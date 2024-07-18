using BusinessLayer.Models.VenueDto;
using BusinessLayer.Services.VenueService;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace ConcertStagerBackendAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class VenueController : ControllerBase
    {

        private readonly IVenueService _venueService;

        public VenueController(IVenueService venueService)
        {
            _venueService = venueService;
        }
        // GET: api/<VenueController>
        [HttpGet]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetAllVenues()
        {
            var response = await _venueService.GetVenuesAsync();
            return response.Success ? Ok(response) : BadRequest(response);
        }

        // GET api/<VenueController>/5
        [HttpGet("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetVenueById(int id)
        {
            var response = await _venueService.GetVenueAsync(id);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpGet("organizer/{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer")]
        public async Task<IActionResult> GetOrganizerVenues(int id)
        {
            var response = await _venueService.GetOrganizerVenuesAsync(id);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        // POST api/<VenueController>
        [HttpPost]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer")]
        public async Task<IActionResult> CreateVenue([FromBody] VenueCreateDto venueCreateDto)
        {
            var response = await _venueService.CreateVenueAsync(venueCreateDto);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        // PUT api/<VenueController>/5
        [HttpPatch("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer")]
        public async Task<IActionResult> UpdateVenue(int id, [FromBody] VenueUpdateDto venueUpdateDto)
        {
            var response = await _venueService.UpdateVenueAsync(id, venueUpdateDto);
            return response.Success ? Ok(response) : BadRequest(response);
        }
    }
}
