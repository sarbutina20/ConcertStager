using BusinessLayer.Models.User;

namespace BusinessLayer.Models.PerformerDto
{
    public class PerformerCreateDto : UserCreateDto
    {
        public int GenreId { get; set; }
    }
}
