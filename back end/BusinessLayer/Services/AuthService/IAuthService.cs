using BusinessLayer.Models;
using BusinessLayer.Models.User;
using BusinessLayer.Responses;
using DataLayer.Entities;

namespace BusinessLayer.Services.AuthService;

public interface IAuthService
{
    //public Task<AuthApiResponse> Registration(UserRegistrationDto user);
    public Task<AuthApiResponse> Registration(UserRegistrationDto user);
    public Task<AuthApiResponse> ProcessLogin(LoginRequestDto request);

    Task<AuthApiResponse> ProcessGoogleLogin(string GoogleId);

    Task<AuthApiResponse> ProcessManualLogin(LoginRequestDto user);

    Task<UserDtoBase> GetUserByID(string Google_ID);

    Task<User> GetUserByEmail(string email);


}