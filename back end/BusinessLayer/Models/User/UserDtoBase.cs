
namespace BusinessLayer.Models.User
{
    public class UserDtoBase : UserCreateDto
    {
        public int Id { get; set; }
        public string JWT { get; set; } = string.Empty;

        public int GenreId { get; set; }

        public string ContactNumber { get; set; } = string.Empty;
    }
}
