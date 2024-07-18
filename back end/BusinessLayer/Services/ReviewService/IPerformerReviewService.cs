using BusinessLayer.Models.ReviewsDto;
using BusinessLayer.Responses;

namespace BusinessLayer.Services.ReviewService
{
    public interface IPerformerReviewService
    {
        Task<ApiResponse> GetAllReviewsForPerformer(int id);
        Task<ApiResponse> CreateReviewForPerformer(PerformerReviewCreateDto performerReviewCreateDto);
        Task<ApiResponse> UpdateReviewForPerformer(int id, ReviewUpdateDto reviewUpdateDto);
        Task<ApiResponse> DeleteReviewForPerformer(int id, int userId);
        public Task<ApiResponse> GetAllReviewsByUser(int id);
    }
}
