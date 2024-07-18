namespace BusinessLayer.Models.User
{
    public class UserRegistrationDto
    {
        public string? GoogleId { get; set; } 
        public string Name { get; set; } = string.Empty;
        public string Email { get; set; } = string.Empty;
        public string Password { get; set; } = string.Empty;
        public int RoleId { get; set; }

        public string? ContactNumber { get; set; } = string.Empty;

        public int? GenreId { get; set; }
    }
}
