namespace BusinessLayer.Models.ConcertDto
{
    public class ConcertCreateDto
    {
        public int VenueId { get; set; }
        public int UserId { get; set; }
        public string Description { get; set; } = string.Empty;
        public string Name { get; set; } = string.Empty;
        public DateTime StartDate { get; set; } 
        public DateTime EndDate { get; set; } 
    }
}
