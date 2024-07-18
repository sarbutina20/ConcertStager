using BusinessLayer.Models.OrganizerDto;
using BusinessLayer.Services;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace ConcertStagerBackendAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class OrganizerController : ControllerBase
    {
        private readonly IOrganizerService _organizerService;

        public OrganizerController(IOrganizerService organizerService)
        {
            _organizerService = organizerService;
        }
        // GET: api/<OrganizerController>
        [HttpGet("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetOrganizer(int id)
        {
            var response = await _organizerService.GetOrganizerAsync(id);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        // GET api/<OrganizerController>/5
        [HttpPatch("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer")]
        public async Task<IActionResult> UpdateOrganizer(int id, [FromBody] OrganizerUpdateDto organizerUpdateDto)
        {
            var response = await _organizerService.UpdateOrganizerAsync(id, organizerUpdateDto);
            return response.Success ? Ok(response) : BadRequest(response);
        }
    }
}
