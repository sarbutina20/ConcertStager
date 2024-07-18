using BusinessLayer.Models.User;

namespace BusinessLayer.Models.OrganizerDto
{
    public class OrganizerUpdateDto :  UserUpdateDto
    {
        public string ContactNumber { get; set; } = string.Empty;
    }
}
