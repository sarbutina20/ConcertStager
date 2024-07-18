

namespace BusinessLayer.Models
{
    public class LoginRequestDto
    {
        public LoginType LoginType { get; set; }
        public string? IdToken { get; set; } = string.Empty;
        public string Email { get; set; } = string.Empty;   
        public string Password { get; set; } = string.Empty;
    }
    
    public enum LoginType
    {
        Google,
        Manual
    }
}
