using BusinessLayer.Models.ReviewsDto;

namespace Core.Entities.Reviews
{
    public class VenueCreateReviewDto : ReviewDto
    {
        public int VenueId { get; set; }
    }
}
