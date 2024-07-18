using BusinessLayer.Models.ConcertDto;
using BusinessLayer.Services.ConcertEntryService;
using BusinessLayer.Services.ConcertService;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace ConcertStagerBackendAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ConcertController : ControllerBase
    {
        private readonly IConcertService _concertService;
        private readonly IConcertEntryService _concertEntryService;

        public ConcertController(IConcertService concertService, IConcertEntryService concertEntryService)
        {
            _concertService = concertService;
            _concertEntryService = concertEntryService;
        }

        // GET api/<ConcertController>/5
        [HttpGet("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetConcert(int id)
        {
            var response = await _concertService.GetConcertAsync(id);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        // GET api/<ConcertController>
        [HttpGet]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetAllConcerts()
        {
            var response = await _concertService.GetConcertsAsync();
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpGet("search")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetConcertsByName([FromQuery] string search)
        {
            var response = await _concertService.GetConcertsByNameAsync(search);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        // POST api/<ConcertController>
        [HttpPost]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer")]

        public async Task<IActionResult> CreateConcert([FromBody] ConcertCreateDto concertCreateDto)
        {
            var response = await _concertService.CreateConcertAsync(concertCreateDto);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpPost("entry")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Performer")]
        public async Task<IActionResult> CreateConcertEntry([FromBody] ConcertEntryCreateDto concertEntryCreateDto)
        {
            var response = await _concertEntryService.CreateConcertEntryAsync(concertEntryCreateDto);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpPatch("entry/{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer")]
        public async Task<IActionResult> AcceptDenyConcertEntry(int id, [FromBody] AcceptDenyConcertEntryDto concertEntry)
        {
            var response = await _concertEntryService.AcceptDenyConcertEntry(id, concertEntry);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpGet("{concertId}/entry")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetConcertEntriesAsync(int concertId)
        {
            var response = await _concertEntryService.GetConcertEntriesAsync(concertId);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpGet("{concertId}/Unresolvedentry")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer")]
        public async Task<IActionResult> GetUnresolvedConcertEntriesAsync(int concertId)
        {
            var response = await _concertEntryService.GetUnresolvedConcertEntries(concertId);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpDelete("entry/{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Performer")]
        public async Task<IActionResult> DeleteConcertEntry(int id)
        {
            var response = await _concertEntryService.DeleteConcertEntry(id);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpGet("organizer/{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer")]
        public async Task<IActionResult> GetConcertsForOrganizer(int id)
        {
            var response = await _concertService.GetConcertsForOrganizer(id);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpGet("visitorUpcoming")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Visitor")]
        public async Task<IActionResult> GetVisitorUpcomingConcerts()
        {
            var response = await _concertService.GetVisitorUpcomingConcerts();
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpGet("Upcoming")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetUpcomingConcerts()
        {
            var response = await _concertService.GetUpcomingConcerts();
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpGet("finished")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetFinishedConcerts()
        {
            var response = await _concertService.GetFinishedConcerts();
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpGet("performer/{id}/accepted")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Performer")]
        public async Task<IActionResult> GetAcceptedConcertsForPerformer(int id)
        {
            var response = await _concertService.GetAcceptedConcertsForPerformer(id);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpGet("performer/{id}/pending")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Performer")]
        public async Task<IActionResult> GetPendingConcertsForPerformer(int id)
        {
            var response = await _concertService.GetPendingConcertsForPerformer(id);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpGet("{concertId}/performer")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetPerformersForConcert(int concertId)
        {
            var response = await _concertService.GetPerformersForConcert(concertId);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpGet("venue/{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetConcertsForVenue(int id)
        {
            var response = await _concertService.GetAllConcertsForVenue(id);
            return response.Success ? Ok(response) : BadRequest(response);
        }
    }
}
