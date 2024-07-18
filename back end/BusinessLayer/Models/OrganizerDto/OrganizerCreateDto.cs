using BusinessLayer.Models.User;

namespace BusinessLayer.Models.OrganizerDto
{
    public class OrganizerCreateDto : UserCreateDto
    {
        public string ContactNumber { get; set; } = string.Empty;
    }
}
