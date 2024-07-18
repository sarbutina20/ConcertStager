using BusinessLayer.Models.ReviewsDto;
using BusinessLayer.Responses;
using Core.Entities.Reviews;

namespace BusinessLayer.Services.ReviewService
{
    public interface IVenueReviewService
    {
        Task<ApiResponse> GetAllReviewsForVenue(int id);
        Task<ApiResponse> CreateReviewForVenue(VenueCreateReviewDto venueCreateReviewDto);
        Task<ApiResponse> UpdateReviewForVenue(int id, ReviewUpdateDto reviewUpdateDto);
        Task<ApiResponse> DeleteReviewForVenue(int id, int userId);
    }
}
