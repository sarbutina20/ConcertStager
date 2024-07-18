using BusinessLayer.Models.User;

namespace BusinessLayer.Models.PerformerDto
{
    public class PerformerUpdateDto : UserUpdateDto
    {
        public int GenreId { get; set; }
    }
}
