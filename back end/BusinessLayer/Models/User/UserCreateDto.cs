namespace BusinessLayer.Models.User
{
    public class UserCreateDto
    {
        public string? GoogleId { get; set; } = string.Empty;
        public string Name { get; set; } = string.Empty;
        public string Email { get; set; } = string.Empty;
        //public string Password { get; set; } = string.Empty;
        public int RoleId { get; set; }
    }
}
