using AutoMapper;
using BusinessLayer.Models.PerformerDto;
using BusinessLayer.Responses;
using Core.Enumerations;
using DataLayer.Entities;
using DataLayer.Generic;
using Microsoft.EntityFrameworkCore;

namespace BusinessLayer.Services.PerformerService
{
    public class PerformerService : IPerformerService
    {
        private readonly IRepository<User> _performerRepository;
        private readonly IMapper _mapper;

        public PerformerService(IRepository<User> performerRepository, IMapper mapper)
        {
            _performerRepository = performerRepository;
            _mapper = mapper;
        }

        public async Task<ApiResponse> GetPerformerAsync(int id)
        {
            var performer = await _performerRepository.GetByIdAsync(id);
            if (performer == null || performer.RoleId != (int)Roles.Performer)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Performer not exists", 400);
                return errorResponse;
            }

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<PerformerDto>("Performer found successfully", new PerformerDto[] { _mapper.Map<PerformerDto>(performer) });
            return successResponse;
        }

        public async Task<ApiResponse> GetPerformersAsync()
        {
            var performers = await _performerRepository.GetAll().Where(u => u.RoleId == (int)Roles.Performer).ToListAsync();

            if (performers.Count == 0)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Performers not found", 400);
                return errorResponse;
            }

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<PerformerDto>("Performers found successfully", _mapper.Map<PerformerDto[]>(performers));
            return successResponse;
        }

        public async Task<ApiResponse> UpdatePerformerAsync(int id, PerformerUpdateDto performerUpdateDto)
        {
            var performerEntity = await _performerRepository.GetByIdAsync(id);
            var existingEmail = await _performerRepository.GetAll().FirstOrDefaultAsync(u => u.Email == performerUpdateDto.Email);

            if ((performerEntity == null || performerEntity.RoleId != (int)Roles.Performer) || (existingEmail != null && existingEmail.Id != id))
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Performer not exists", 400);
                return errorResponse;
            }

            _mapper.Map(performerUpdateDto, performerEntity);
            _performerRepository.Update(performerEntity);
            await _performerRepository.SaveAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<PerformerDto>("Performer updated successfully", new PerformerDto[] { _mapper.Map<PerformerDto>(performerEntity) });
            return successResponse;
        }
    }
}
