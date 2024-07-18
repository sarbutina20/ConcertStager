using BusinessLayer.Models.PerformerDto;
using BusinessLayer.Services.PerformerService;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace ConcertStagerBackendAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PerformerController : ControllerBase
    {
        private readonly IPerformerService _performerService;

        public PerformerController(IPerformerService performerService)
        {
            _performerService = performerService;
        }

        // GET api/<PerformerController>/5
        [HttpGet]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetPerformers()
        {
            var response = await _performerService.GetPerformersAsync();
            return response.Success ? Ok(response) : BadRequest(response);
        }

        // GET api/<PerformerController>/5
        [HttpGet("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetPerformer(int id)
        {
            var response = await _performerService.GetPerformerAsync(id);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        // PUT api/<PerformerController>/5
        [HttpPatch("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Performer")]
        public async Task<IActionResult> UpdatePerformer(int id, [FromBody] PerformerUpdateDto performerUpdateDto)
        {
            var response = await _performerService.UpdatePerformerAsync(id, performerUpdateDto);
            return response.Success ? Ok(response) : BadRequest(response);
        }
    }
}
