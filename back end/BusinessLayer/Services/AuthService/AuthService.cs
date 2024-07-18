using AutoMapper;
using BusinessLayer.Models;
using BusinessLayer.Models.User;
using BusinessLayer.Responses;
using BusinessLayer.Services.UserService;
using DataLayer.Entities;
using DataLayer.Generic;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;

namespace BusinessLayer.Services.AuthService;

public class AuthService : IAuthService
{
    private readonly IRepository<User> _userRepository;
    private readonly IMapper _mapper;
    private readonly IJwtTokenService _jwtTokenService;
    private readonly IConfiguration _configuration;
    private readonly IUserService _userService;

    public AuthService(IRepository<User> userRepository, IMapper mapper, IJwtTokenService jwtTokenService, IConfiguration configuration, IUserService userService)
    {
        _userRepository = userRepository;
        _mapper = mapper;
        _jwtTokenService = jwtTokenService;
        _configuration = configuration;
        _userService = userService;
    }

    public async Task<AuthApiResponse> Registration(UserRegistrationDto user)
    {
        var storedUser = await GetUserByEmail(user.Email);
        if (storedUser != null)
        {
            var failedResponse = new AuthApiErrorResponse();
            failedResponse.Success = false;
            failedResponse.Message = "User already exists";
            failedResponse.error_code = 400;
            failedResponse.error_message = "User already exists";
            return failedResponse;
        }

        user.Password = BCrypt.Net.BCrypt.HashPassword(user.Password);

        var mappedUser = _mapper.Map<User>(user);
        _userRepository.Add(mappedUser);
        await _userRepository.SaveAsync();

        var roleName = await _userService.GetUserRoleAsync(mappedUser.Id);

        var secretKey = _configuration["Jwt:Key"];
        var token = _jwtTokenService.GenerateToken(user.Email, roleName, secretKey);

        var createdUser = _mapper.Map<UserDtoBase>(mappedUser);
        createdUser.JWT = token;

        var response = new AuthApiResponse();
        response.Data = new UserDtoBase[] { createdUser };
        response.Success = true;
        response.Message = "User was successfully registered.";

        return response;
    }

    public async Task<AuthApiResponse> ProcessLogin(LoginRequestDto request)
    {
        if (request.LoginType == LoginType.Google)
        {
            return await ProcessGoogleLogin(request.IdToken);
        }
        else if (request.LoginType == LoginType.Manual)
        {
            return await ProcessManualLogin(request);
        }
        else
        {
            var failedResponse = new AuthApiErrorResponse();
            failedResponse.Success = false;
            failedResponse.Message = "Invalid login type";
            failedResponse.error_code = 400;
            failedResponse.error_message = "Invalid login type";
            return failedResponse;
        }
    }

    public async Task<AuthApiResponse> ProcessGoogleLogin(string Google_ID)
    {
        var storedUser = await GetUserByID(Google_ID);
        if (storedUser == null)
        {
            var failedResponse = new AuthApiErrorResponse();
            failedResponse.Success = false;
            failedResponse.Message = "User doesn't exist";
            failedResponse.error_code = 404;
            failedResponse.error_message = "User doesn't exist";
            return failedResponse;
        }

        var roleName = await _userService.GetUserRoleAsync(storedUser.Id);

        var secretKey = _configuration["Jwt:Key"];
        var token = _jwtTokenService.GenerateToken(storedUser.GoogleId, roleName, secretKey);

        //var mappedUser = _mapper.Map<UserDtoBase>(storedUser);
        storedUser.JWT = token;

        var response = new AuthApiResponse();
        response.Data = new UserDtoBase[] { storedUser };
        response.Success = true;
        response.Message = "User was successfully authentificated.";

        return response;
    }

    public async Task<AuthApiResponse> ProcessManualLogin(LoginRequestDto user)
    {
        var storedUser = await GetUserByEmail(user.Email);
        if (storedUser == null)
        {
            var failedResponse = new AuthApiErrorResponse();
            failedResponse.Success = false;
            failedResponse.Message = "User doesn't exist";
            failedResponse.error_code = 404;
            failedResponse.error_message = "User doesn't exist";
            return failedResponse;
        }

        
        var validPassword = BCrypt.Net.BCrypt.Verify(user.Password, storedUser.Password);

        if (!validPassword)
        {
            var failedResponse = new AuthApiErrorResponse();
            failedResponse.Success = false;
            failedResponse.Message = "Incorrect password!";
            failedResponse.error_code = 403;
            failedResponse.error_message = "Incorrect password!";
            return failedResponse;
        }

        var roleName = await _userService.GetUserRoleAsync(storedUser.Id);

        var secretKey = _configuration["Jwt:Key"];
        var token = _jwtTokenService.GenerateToken(storedUser.Email, roleName, secretKey);
        var mappedUser = _mapper.Map<UserDtoBase>(storedUser);
        mappedUser.JWT = token;

        var response = new AuthApiResponse();
        response.Data = new UserDtoBase[] { mappedUser };
        response.Success = true;
        response.Message = "User was successfully authentificated.";

        return response;

    }

    public async Task<UserDtoBase> GetUserByID(string Google_ID)
    {
        var storedUser = await _userRepository.GetAll().FirstOrDefaultAsync(u => u.GoogleId == Google_ID);
        var mappedUser = _mapper.Map<UserDtoBase>(storedUser);
        return mappedUser;
    }

    public async Task<User> GetUserByEmail(string email)
    {
        var storedUser = await _userRepository.GetAll().FirstOrDefaultAsync(u => u.Email == email);
        //var mappedUser = _mapper.Map<UserDtoBase>(storedUser);
        return storedUser;
    }
}