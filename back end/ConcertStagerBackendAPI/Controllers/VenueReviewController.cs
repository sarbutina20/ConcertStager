using BusinessLayer.Models.ReviewsDto;
using BusinessLayer.Services.ReviewService;
using Core.Entities.Reviews;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace ConcertStagerBackendAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class VenueReviewController : ControllerBase
    {
        private readonly IVenueReviewService _venueReviewService;

        public VenueReviewController(IVenueReviewService venueReviewService)
        {
            _venueReviewService = venueReviewService;
        }

        // GET api/<VenueReviewController>/5
        [HttpGet("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetReviewsForVenue(int id)
        {
            var response = await _venueReviewService.GetAllReviewsForVenue(id);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        // POST api/<ReviewController>
        [HttpPost]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Performer, Visitor")]
        public async Task<IActionResult> CreateReviewForVenue([FromBody] VenueCreateReviewDto venueCreateReviewDto)
        {
            var response = await _venueReviewService.CreateReviewForVenue(venueCreateReviewDto);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpPatch("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Performer, Visitor")]
        public async Task<IActionResult> UpdateVenueReview(int id, [FromBody] ReviewUpdateDto reviewUpdate)
        {
            var response = await _venueReviewService.UpdateReviewForVenue(id, reviewUpdate);
            return response.Success ? Ok(response) : BadRequest(response);
        }

        [HttpDelete("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Performer, Visitor")]
        public async Task<IActionResult> DeleteVenueReview(int id, [FromQuery] int userReviewId)
        {
            var response = await _venueReviewService.DeleteReviewForVenue(id, userReviewId);
            return response.Success ? Ok(response) : BadRequest(response);
        }
    }
}
