using Azure;
using BusinessLayer.Models;
using BusinessLayer.Models.User;
using BusinessLayer.Responses;
using BusinessLayer.Services.AuthService;
using BusinessLayer.Services.UserService;
using DataLayer.Entities;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;


namespace ConcertStagerBackendAPI.Controllers;

[Route("api/[controller]")]
[ApiController]
public class UserController : ControllerBase
{
    private readonly IAuthService _authService;
    private readonly IUserService _userService;

    public UserController(IAuthService authService, IUserService userService)
    {
        _authService = authService;
        _userService = userService;
    }

    [HttpPost("register"), AllowAnonymous]
    public async Task<ActionResult<User>> RegisterUser([FromBody] UserRegistrationDto user)
    {
        var response = new JsonResult(await _authService.Registration(user));
        return response.Value is AuthApiResponse authResponse && authResponse.Success ? Ok(response.Value) : BadRequest(response.Value);
    }


    [HttpPost("login"), AllowAnonymous]
    public async Task<ActionResult> Login([FromBody] LoginRequestDto request)
    {
        var response = new JsonResult(await _authService.ProcessLogin(request));
        return response.Value is AuthApiResponse authResponse && authResponse.Success ? Ok(response.Value) : BadRequest(response.Value);
    }

    // Testing route
    [HttpPut]
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer")]
    public async Task<ActionResult<UserDtoBase>> ProtectedRoute([FromBody] UserDtoBase user)
    {
        return Ok("Uspješno pristupanje ruti.");
    }

    [HttpGet, AllowAnonymous]
    public async Task<IActionResult> GetAllUsers()
    {
        var response = await _userService.GetAllUsers();
        return response.Success ? Ok(response) : BadRequest(response);
    }
}