using AutoMapper;
using BusinessLayer.Models.User;
using BusinessLayer.Responses;
using DataLayer.Entities;
using DataLayer.Generic;
using Microsoft.EntityFrameworkCore;
using System.Data;


namespace BusinessLayer.Services.UserService
{
    public class UserService : IUserService
    {
        private readonly IRepository<User> _userRepository;
        private readonly IMapper _mapper;

        public UserService(IRepository<User> userRepository, IMapper mapper)
        {
            _userRepository = userRepository;
            _mapper = mapper;
        }

        public async Task<User> CreateUserAsync(UserRegistrationDto user)
        {
            var passwordHash = BCrypt.Net.BCrypt.HashPassword(user.Password);
            user.Password = passwordHash;

            var storedUser = await GetUserByUsername(user.Name);
            if (storedUser != null) throw new DuplicateNameException("User already exists!");

            try
            {
                var mappedUser = _mapper.Map<User>(user);
                _userRepository.Add(mappedUser);
                await _userRepository.SaveAsync();
                return mappedUser;
            }
            catch (Exception)
            {
                return null;
            }
        }

        public async Task<UserDtoBase> VerifyUserAsync(string Google_ID)
        {
            var storedUser = await GetUserByUsername(Google_ID);
            if (storedUser == null) return null;

            var mappedUser = _mapper.Map<UserDtoBase>(storedUser);
            return mappedUser;
        }

        public async Task<User> GetUserByUsername(string Google_ID)
        {
            var users = _userRepository.GetAll();
            var storedUser = await users.FirstOrDefaultAsync(u => u.GoogleId == Google_ID);
            return storedUser;
        }

        public async Task<string> GetUserRoleAsync(int userId)
        {
            var role = await _userRepository.GetAll().Where(u => u.Id == userId).Select(u => u.Role.Name).FirstOrDefaultAsync();
            if (role != null)
            {
                return role;
            }
            return "Role not found";
        }

        public async Task<ApiResponse> GetAllUsers()
        {
            var users = await _userRepository.GetAll().ToListAsync();
            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<UserDtoBase>("Successfully retrieved all users.", _mapper.Map<UserDtoBase[]>(users));
            return successResponse;
        }
    }
}