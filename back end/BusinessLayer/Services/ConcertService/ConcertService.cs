using AutoMapper;
using BusinessLayer.Models.ConcertDto;
using BusinessLayer.Models.PerformerDto;
using BusinessLayer.Responses;
using Core.Entities.Concert;
using Core.Enumerations;
using DataLayer.Entities;
using DataLayer.Generic;
using Microsoft.EntityFrameworkCore;

namespace BusinessLayer.Services.ConcertService
{
    public class ConcertService : IConcertService
    {

        private readonly IRepository<Concert> _concertRepository;
        private readonly IRepository<User> _userRepository;
        private readonly IRepository<Venue> _venueRepository;
        private readonly IMapper _mapper;

        public ConcertService(IRepository<Venue> venueRepository, IRepository<User> userRepository, IRepository<Concert> concertRepository, IMapper mapper)
        {
            _concertRepository = concertRepository;
            _userRepository = userRepository;
            _venueRepository = venueRepository;
            _mapper = mapper;
        }

        public async Task<ApiResponse> CreateConcertAsync(ConcertCreateDto concertCreateDto)
        {
            var allConcerts = await _concertRepository.GetAll().ToListAsync();
            var existedConcert = allConcerts.FirstOrDefault(x => x.Name == concertCreateDto.Name);
            if (existedConcert != null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Concert with this name already exists", 400);
                return errorResponse;
            }

            var organizer = await _userRepository.GetByIdAsync(concertCreateDto.UserId);
            if (organizer == null || organizer.RoleId != (int)Roles.Organizer)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("User with this id doesn't exist or is not an organizer", 400);
                return errorResponse;
            }

            var concert = _mapper.Map<Concert>(concertCreateDto);
            _concertRepository.Add(concert);
            await _concertRepository.SaveAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertDto>("Concert created successfully", new ConcertDto[] { _mapper.Map<ConcertDto>(concert) });
            return successResponse;
        }

        public async Task<ApiResponse> GetConcertAsync(int id)
        {
            var concert = await _concertRepository.GetByIdAsync(id);
            if (concert == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Concert with this id doesn't exist", 400);
                return errorResponse;
            }

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertDto>("Concert retrieved successfully", new ConcertDto[] { _mapper.Map<ConcertDto>(concert) });
            return successResponse;
        }

        public async Task<ApiResponse> GetConcertsAsync()
        {
            var concerts = await _concertRepository.GetAll().ToListAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertDto>("Concerts retrieved successfully", _mapper.Map<ConcertDto[]>(concerts));
            return successResponse;
        }

        public async Task<ApiResponse> GetConcertsByNameAsync(string name)
        {
            var concerts = await _concertRepository.GetAll().Where(x => x.Name.Contains(name)).ToListAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertDto>("Concerts retrieved successfully", _mapper.Map<ConcertDto[]>(concerts));
            return successResponse;
        }

        public async Task<ApiResponse> GetConcertsForOrganizer(int id)
        {
            var organizer = await _userRepository.GetByIdAsync(id);
            if(organizer == null || organizer.RoleId != (int)Roles.Organizer)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("User with this id doesn't exist or is not an organizer", 400);
                return errorResponse;
            }

            var organizerConcerts = await _concertRepository.GetAll().Where(x => x.UserId == id).ToListAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertDto>("Concerts retrieved successfully", _mapper.Map<ConcertDto[]>(organizerConcerts));
            return successResponse;
        }

        public async Task<ApiResponse> GetVisitorUpcomingConcerts()
        {
            var currentDate = DateTime.Now;

            var upcomingConcerts = await _concertRepository.GetAll().Where(x => x.ConcertEntries.Any(ce => ce.isAccepted == true) && x.StartDate > currentDate).ToListAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertDto>("Upcoming concerts retrieved successfully", _mapper.Map<ConcertDto[]>(upcomingConcerts));
            return successResponse;
        }

        public async Task<ApiResponse> GetUpcomingConcerts()
        {
            var currentDate = DateTime.Now;

            var upcomingConcerts = await _concertRepository.GetAll().Where(x => x.StartDate > currentDate).ToListAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertDto>("Upcoming concerts retrieved successfully", _mapper.Map<ConcertDto[]>(upcomingConcerts));
            return successResponse;
        }

        public async Task<ApiResponse> GetFinishedConcerts()
        {
            var currentDate = DateTime.Now;

            var finishedConcerts = await _concertRepository
                .GetAll()
                .Where(x => x.EndDate < currentDate)
                .ToListAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertDto>("Finished concerts retrieved successfully", _mapper.Map<ConcertDto[]>(finishedConcerts));
            return successResponse;
        }

        public async Task<ApiResponse> GetAcceptedConcertsForPerformer(int id)
        {
            var performer = await _userRepository.GetByIdAsync(id);
            if(performer == null || performer.RoleId != (int)Roles.Performer)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("User with this id doesn't exist or is not a performer", 400);
                return errorResponse;
            }

            var performerConcerts = await _concertRepository.GetAll().Where(c => c.ConcertEntries.Any(ce => ce.UserId == id && ce.isAccepted == true)).ToListAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertDto>("Performer concerts", _mapper.Map<ConcertDto[]>(performerConcerts));
            return successResponse;
        }

        public async Task<ApiResponse> GetPendingConcertsForPerformer(int id)
        {
            var performer = await _userRepository.GetByIdAsync(id);
            if (performer == null || performer.RoleId != (int)Roles.Performer)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("User with this id doesn't exist or is not a performer", 400);
                return errorResponse;
            }

            var currentDate = DateTime.Now;

            var performerConcerts = await _concertRepository.GetAll().Where(c => c.ConcertEntries.Any(ce => ce.UserId == id && ce.isAccepted == null) && c.StartDate > currentDate).ToListAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertDto>("Performer pending concerts", _mapper.Map<ConcertDto[]>(performerConcerts));
            return successResponse;
        }

        public async Task<ApiResponse> GetPerformersForConcert(int concertId)
        {
            var concert = await _concertRepository.GetByIdAsync(concertId);
            if(concert == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Concert with this id doesn't exist", 400);
                return errorResponse;
            }

            var performers = await _userRepository.GetAll().Where(u => u.ConcertEntries.Any(ce => ce.ConcertId == concertId && ce.isAccepted == true)).ToListAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<PerformerDto>("Performers for concert", _mapper.Map<PerformerDto[]>(performers));
            return successResponse;
        }

        public async Task<ApiResponse> GetAllConcertsForVenue(int id)
        {
            var venue = await _venueRepository.GetByIdAsync(id);
            if(venue == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Venue with this id doesn't exist", 400);
                return errorResponse;
            }

            var venueConcerts = await _concertRepository.GetAll().Where(c => c.VenueId == id).ToListAsync();
            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<ConcertDto>("Venue concerts", _mapper.Map<ConcertDto[]>(venueConcerts));  
            return successResponse;
        }
    }
}
