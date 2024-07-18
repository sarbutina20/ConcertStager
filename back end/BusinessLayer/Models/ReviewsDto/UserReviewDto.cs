namespace BusinessLayer.Models.ReviewsDto
{
    public class UserReviewDto
    {
        public int Id { get; set; }
        public float Grade { get; set; }
        public string Description { get; set; } = string.Empty;
        public int UserReviewId { get; set; }
        public int UserId { get; set; }
        public int VenueId { get; set; }
    }
}
