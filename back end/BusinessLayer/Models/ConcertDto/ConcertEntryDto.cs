namespace BusinessLayer.Models.ConcertDto
{
    public class ConcertEntryDto : ConcertEntryCreateDto
    {
        public int Id { get; set; }
        public DateTime Date { get; set; } 
        public bool? isAccepted { get; set; }
    }
}
