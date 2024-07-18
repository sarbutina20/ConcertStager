using BusinessLayer.Responses;

namespace BusinessLayer.Services.GenreService
{
    public interface IGenreService
    {
        public Task<ApiResponse> GetGenreById(int id);
        public Task<ApiResponse> GetAllGenres();
    }
}
