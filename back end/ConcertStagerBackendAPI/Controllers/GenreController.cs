using BusinessLayer.Services.GenreService;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace ConcertStagerBackendAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GenreController : ControllerBase
    {
        private readonly IGenreService _genreService;

        public GenreController(IGenreService genreService)
        {
            _genreService = genreService;
        }
        // GET: api/<GenreController>
        [HttpGet("{id}")]
        [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme, Roles = "Organizer, Performer, Visitor")]
        public async Task<IActionResult> GetGenre(int id)
        {
            var response = await _genreService.GetGenreById(id);
            return response.Success ? Ok(response) : NotFound(response);
        }

        // GET: api/<GenreController>
        [HttpGet, AllowAnonymous]
        public async Task<IActionResult> GetGenres()
        {
            var response = await _genreService.GetAllGenres();
            return response.Success ? Ok(response) : NotFound(response);
        }
    }
}
