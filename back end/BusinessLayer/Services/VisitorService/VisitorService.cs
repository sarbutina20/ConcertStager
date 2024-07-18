using AutoMapper;
using BusinessLayer.Models.User;
using BusinessLayer.Models.VisitorDto;
using BusinessLayer.Responses;
using Core.Enumerations;
using DataLayer.Entities;
using DataLayer.Generic;
using Microsoft.EntityFrameworkCore;

namespace BusinessLayer.Services.VisitorService
{
    public class VisitorService : IVisitorService
    {
        private readonly IRepository<User> _visitorRepository;
        private readonly IMapper _mapper;

        public VisitorService(IRepository<User> visitorRepository, IMapper mapper)
        {
            _visitorRepository = visitorRepository;
            _mapper = mapper;

        }

        public async Task<ApiResponse> GetVisitorAsync(int id)
        {
            var visitor = await _visitorRepository.GetByIdAsync(id);
            if (visitor == null || visitor.RoleId != (int)Roles.Visitor)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Visitor not found", 400);
                return errorResponse;
            }

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<VisitorDto>("Visitor found successfully", new VisitorDto[] { _mapper.Map<VisitorDto>(visitor) });
            return successResponse;
        }

        public async Task<ApiResponse> GetVisitorsAsync()
        {
            var visitors = await _visitorRepository.GetAll().Where(u => u.RoleId == (int)Roles.Visitor).ToListAsync();

            if(visitors.Count == 0)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Visitors not found", 400);
                return errorResponse;
            }

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<VisitorDto>("Visitors found successfully", _mapper.Map<VisitorDto[]>(visitors));
            return successResponse;
        }

        public async Task<ApiResponse> UpdateVisitorAsync(int id, UserUpdateDto visitorDto)
        {
            var visitorEntity = await _visitorRepository.GetByIdAsync(id);
            var existedEmail = await _visitorRepository.GetAll().FirstOrDefaultAsync(v => v.Email == visitorDto.Email);

            if (visitorEntity == null || visitorEntity.RoleId != (int)Roles.Visitor || (existedEmail != null && existedEmail.Id != id))
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Visitor not found", 400);
                return errorResponse;
            }

            _mapper.Map(visitorDto, visitorEntity);
            _visitorRepository.Update(visitorEntity);

            await _visitorRepository.SaveAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<VisitorDto>("Visitor updated successfully", new VisitorDto[] { _mapper.Map<VisitorDto>(visitorEntity) });
            return successResponse;
        }
    }
}
