using BusinessLayer.Models.User;
using BusinessLayer.Responses;
using DataLayer.Entities;

namespace BusinessLayer.Services.UserService
{
    public interface IUserService
    {
        public Task<User> CreateUserAsync(UserRegistrationDto user);

        public Task<UserDtoBase> VerifyUserAsync(string Google_ID);

        public Task<User> GetUserByUsername(string username);
        public Task<String> GetUserRoleAsync(int userId);
        public Task<ApiResponse> GetAllUsers();
    }
}
