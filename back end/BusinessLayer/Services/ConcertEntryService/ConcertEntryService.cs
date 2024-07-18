using AutoMapper;
using BusinessLayer.Models.ConcertDto;
using BusinessLayer.Responses;
using Core.Enumerations;
using DataLayer.Entities;
using DataLayer.Generic;
using Microsoft.EntityFrameworkCore;

namespace BusinessLayer.Services.ConcertEntryService
{
    public class ConcertEntryService : IConcertEntryService
    {
        private readonly IRepository<Concert> _concertRepository;
        private readonly IRepository<ConcertEntries> _concertEntriesRepository;
        private readonly IRepository<User> _userRepository;
        private readonly IMapper _mapper;

        public ConcertEntryService(IRepository<User> userRepository, IRepository<Concert> concertRepository, IRepository<ConcertEntries> concertEntriesRepository, IMapper mapper)
        {
            _concertRepository = concertRepository;
            _concertEntriesRepository = concertEntriesRepository;
            _userRepository = userRepository;
            _mapper = mapper;
        }

        public async Task<ApiResponse> CreateConcertEntryAsync(ConcertEntryCreateDto concertEntryCreateDto)
        {
            var allConcertEntries = await _concertEntriesRepository.GetAll().ToListAsync();
            var existedConcertEntry = allConcertEntries.FirstOrDefault(x => x.ConcertId == concertEntryCreateDto.ConcertId && x.UserId == concertEntryCreateDto.UserId);
            if (existedConcertEntry != null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Concert entry with this user and concert already exists", 400);
                return errorResponse;
            }

            var performer = await _userRepository.GetByIdAsync(concertEntryCreateDto.UserId);
            if (performer == null || performer.RoleId != (int)Roles.Performer)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("User with this id doesn't exist or is not a performer", 400);
                return errorResponse;
            }

            var concert = await _concertRepository.GetByIdAsync(concertEntryCreateDto.ConcertId);
            if (concert == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Concert with this id doesn't exist", 400);
                return errorResponse;
            }

            var concertEntry = _mapper.Map<ConcertEntries>(concertEntryCreateDto);
            _concertEntriesRepository.Add(concertEntry);
            await _concertEntriesRepository.SaveAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertEntryDto>("Concert entry created successfully", new ConcertEntryDto[] { _mapper.Map<ConcertEntryDto>(concertEntry) });
            return successResponse;
        }

        public async Task<ApiResponse> DeleteConcertEntry(int id)
        {
            var concertEntries = await _concertEntriesRepository.GetAll().ToListAsync();
            var concertEntry = concertEntries.FirstOrDefault(x => x.Id == id);
            if (concertEntry == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Concert entry with this id doesn't exist", 400);
                return errorResponse;
            }

            _concertEntriesRepository.Delete(concertEntry);
            await _concertEntriesRepository.SaveAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertEntryDto>("Concert entry deleted successfully", new ConcertEntryDto[] { _mapper.Map<ConcertEntryDto>(concertEntry) });
            return successResponse;
        }

        public async Task<ApiResponse> GetConcertEntriesAsync(int concertId)
        {
            var concerts = await _concertEntriesRepository.GetAll().Where(x => x.ConcertId == concertId).ToListAsync();
            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertEntryDto>("Concert entries retrieved successfully", _mapper.Map<ConcertEntryDto[]>(concerts));
            return successResponse;
        }

        public async Task<ApiResponse> GetUnresolvedConcertEntries(int concertId)
        {
            var concert = await _concertRepository.GetByIdAsync(concertId);
            if(concert == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Concert with this id doesn't exist", 400);
                return errorResponse;
            }
            var concertEntries = await _concertEntriesRepository.GetAll().Where(x => x.ConcertId == concertId && x.isAccepted == null).ToListAsync();
            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertEntryDto>("Concert entries retrieved successfully", _mapper.Map<ConcertEntryDto[]>(concertEntries));
            return successResponse;
        }

        public async Task<ApiResponse> AcceptDenyConcertEntry(int id, AcceptDenyConcertEntryDto concertEntry)
        {
            var existingConcertEntry = await _concertEntriesRepository.GetByIdAsync(id);

            if (existingConcertEntry == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Concert entry with this id doesn't exist", 400);
                return errorResponse;
            }

            _mapper.Map(concertEntry, existingConcertEntry);
            _concertEntriesRepository.Update(existingConcertEntry);

            await _concertEntriesRepository.SaveAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertEntryDto>("Concert entry updated successfully", new ConcertEntryDto[] { _mapper.Map<ConcertEntryDto>(existingConcertEntry) });
            return successResponse;

        }
    }
}
