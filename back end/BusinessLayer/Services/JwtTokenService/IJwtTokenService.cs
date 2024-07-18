namespace BusinessLayer.Services
{
    public interface IJwtTokenService
    {
        string GenerateToken(string email, string role, string secretKey);
        bool ValidateToken(string token, string secretKey);
    }
}
