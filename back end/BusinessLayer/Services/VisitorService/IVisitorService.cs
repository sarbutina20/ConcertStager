using BusinessLayer.Models.User;
using BusinessLayer.Responses;

namespace BusinessLayer.Services.VisitorService
{
    public interface IVisitorService
    {
        public Task<ApiResponse> GetVisitorsAsync();

        public Task<ApiResponse> GetVisitorAsync(int id);
        public Task<ApiResponse> UpdateVisitorAsync(int id, UserUpdateDto visitorUpdateDto);
    }
}
