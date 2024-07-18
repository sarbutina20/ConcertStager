using AutoMapper;
using BusinessLayer.Models.OrganizerDto;
using BusinessLayer.Responses;
using Core.Enumerations;
using DataLayer.Entities;
using DataLayer.Generic;
using Microsoft.EntityFrameworkCore;

namespace BusinessLayer.Services.OrganizerService
{
    public class OrganizerService : IOrganizerService
    {
        private readonly IRepository<User> _organizerRepository;
        private readonly IMapper _mapper;

        public OrganizerService(IRepository<User> organizerRepository, IMapper mapper)
        {
            _organizerRepository = organizerRepository;
            _mapper = mapper;
        }

        public async Task<ApiResponse> GetOrganizerAsync(int id)
        {
            var organizer = await _organizerRepository.GetByIdAsync(id);
            if (organizer == null || organizer.RoleId != (int)Roles.Organizer)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Organizer not exists", 400);
                return errorResponse;
            }

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<OrganizerDto>("Organizer found successfully", new OrganizerDto[] { _mapper.Map<OrganizerDto>(organizer) });
            return successResponse;
        }

        public async Task<ApiResponse> GetOrganizersAsync()
        {
            var organizers = await _organizerRepository.GetAll().Where(u => u.RoleId == (int)Roles.Organizer).ToListAsync();

            if(organizers.Count == 0)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Organizers not found", 400);
                return errorResponse;
            }

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<OrganizerDto>("Organizers found successfully", _mapper.Map<OrganizerDto[]>(organizers));
            return successResponse;
        }

        public async Task<ApiResponse> UpdateOrganizerAsync(int id, OrganizerUpdateDto organizerUpdateDto)
        {
            var organizerEntity = await _organizerRepository.GetByIdAsync(id);

            if (organizerEntity == null || organizerEntity.RoleId != (int)Roles.Organizer)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Organizer not exists", 400);
                return errorResponse;
            }

            _mapper.Map(organizerUpdateDto, organizerEntity);
            _organizerRepository.Update(organizerEntity);
            await _organizerRepository.SaveAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<OrganizerDto>("Organizer updated successfully", new OrganizerDto[] { _mapper.Map<OrganizerDto>(organizerEntity) });
            return successResponse;
        }
    }
}
