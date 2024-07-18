using BusinessLayer.Models.ReviewsDto;
using BusinessLayer.Services.ReviewService;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace ConcertStagerBackendAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PerformerReviewController : ControllerBase
    {

        private readonly IPerformerReviewService _performerReviewService;

        public PerformerReviewController(IPerformerReviewService performerReviewService)
        {
            _performerReviewService = performerReviewService;
        }
        // GET: api/<ReviewController>
        [HttpGet("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetReviewsForPerformer(int id)
        {
            var response = await _performerReviewService.GetAllReviewsForPerformer(id);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpPatch("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Visitor")]
        public async Task<IActionResult> UpdatePerformerReview(int id, [FromBody] ReviewUpdateDto reviewUpdate)
        {
            var response = await _performerReviewService.UpdateReviewForPerformer(id, reviewUpdate);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpDelete("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Visitor")]
        public async Task<IActionResult> DeletePerformerReview(int id, [FromQuery] int userReviewId)
        {
            var response = await _performerReviewService.DeleteReviewForPerformer(id, userReviewId);
            return response.Success ? Ok(response) : BadRequest(response);
        }
        [HttpPost]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Visitor")]
        public async Task<IActionResult> CreateReviewForPerformer([FromBody] PerformerReviewCreateDto performerReviewCreateDto)
        {
            var response = await _performerReviewService.CreateReviewForPerformer(performerReviewCreateDto);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpGet("user/{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetReviewsByUser(int id)
        {
            var response = await _performerReviewService.GetAllReviewsByUser(id);
            return response.Success ? Ok(response) : BadRequest(response);
        }
    }
}
