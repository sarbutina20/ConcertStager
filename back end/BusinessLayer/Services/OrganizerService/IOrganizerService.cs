using BusinessLayer.Models.OrganizerDto;
using BusinessLayer.Responses;

namespace BusinessLayer.Services
{
    public interface IOrganizerService
    {
        public Task<ApiResponse> GetOrganizersAsync();

        public Task<ApiResponse> GetOrganizerAsync(int id);
        public Task<ApiResponse> UpdateOrganizerAsync(int id, OrganizerUpdateDto organizerUpdateDto);
    }
}
