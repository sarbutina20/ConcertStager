using BusinessLayer.Models.PerformerDto;
using BusinessLayer.Responses;

namespace BusinessLayer.Services.PerformerService
{
    public interface IPerformerService
    {
        public Task<ApiResponse> GetPerformersAsync();

        public Task<ApiResponse> GetPerformerAsync(int id);
        public Task<ApiResponse> UpdatePerformerAsync(int id, PerformerUpdateDto performerUpdateDto);
    }
}
