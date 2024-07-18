using BusinessLayer.Models;
using BusinessLayer.Services.Login;
using Microsoft.AspNetCore.Mvc;

namespace ConcertStagerBackendAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class OAuthController : ControllerBase
    {
        private readonly IGoogleLoginService _googleLoginService;

        public OAuthController(IGoogleLoginService googleLoginService)
        {
            _googleLoginService = googleLoginService;
        }

        [HttpPost("GoogleSignIn")]
        public async Task<ActionResult> VerifyToken([FromBody] GoogleSignInRequestDto request)
        {
            bool payloadValid = await _googleLoginService
               .Verify(request.IdToken);

            if (payloadValid)
            {
                return Ok(new { GoogleIdTokenValidity = "True" });
            }
            else
            {
                return Ok(new { GoogleIdTokenValidity = "False" });
            }
        }
    }
}
