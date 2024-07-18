using BusinessLayer.Models.User;
using BusinessLayer.Services.VisitorService;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace ConcertStagerBackendAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class VisitorController : ControllerBase
    {
        private readonly IVisitorService _visitorService;

        public VisitorController(IVisitorService visitorService)
        {
            _visitorService = visitorService;
        }
        // GET: api/<VisitorController>
        [HttpGet("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetVisitor(int id)
        {
            var response = await _visitorService.GetVisitorAsync(id);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        // PUT api/<VisitorController>/5
        [HttpPatch("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Visitor")]
        public async Task<IActionResult> UpdateVisitor(int id, [FromBody] UserUpdateDto visitorUpdateDto)
        {
            var response = await _visitorService.UpdateVisitorAsync(id, visitorUpdateDto);
            return response.Success ? Ok(response) : BadRequest(response);
        }
    }
}
