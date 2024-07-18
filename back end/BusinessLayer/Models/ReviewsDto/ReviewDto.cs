namespace BusinessLayer.Models.ReviewsDto
{
    public class ReviewDto
    {
        public float Grade { get; set; }
        public string? Description { get; set; } = string.Empty;
        public int UserReviewId { get; set; }
    }
}
