using BusinessLayer.Models.VenueDto;
using BusinessLayer.Responses;

namespace BusinessLayer.Services.VenueService
{
    public interface IVenueService
    {
        public Task<ApiResponse> GetVenuesAsync();

        public Task<ApiResponse> GetVenueAsync(int id);

        public Task<ApiResponse> CreateVenueAsync(VenueCreateDto venueCreateDto);

        public Task<ApiResponse> GetOrganizerVenuesAsync(int organizerId);

        public Task<ApiResponse> UpdateVenueAsync(int id, VenueUpdateDto venueUpdateDto);
    }
}
