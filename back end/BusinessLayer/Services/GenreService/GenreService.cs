using AutoMapper;
using BusinessLayer.Models.GenreDto;
using BusinessLayer.Responses;
using DataLayer.Entities;
using DataLayer.Generic;
using Microsoft.EntityFrameworkCore;

namespace BusinessLayer.Services.GenreService
{
    public class GenreService : IGenreService
    {
        private readonly IRepository<Genre> _genreRepository;
        private readonly IMapper _mapper;

        public GenreService(IRepository<Genre> genreRepository, IMapper mapper)
        {
            _genreRepository = genreRepository;
            _mapper = mapper;
        }

        public async Task<ApiResponse> GetGenreById(int id)
        {
            var genre = await _genreRepository.GetByIdAsync(id);

            if (genre == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Genre not found", 404);
                return errorResponse;
            }

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<GenreBaseDto>("Genre found", new GenreBaseDto[] { _mapper.Map<GenreBaseDto>(genre) });
            return successResponse;

        }

        public async Task<ApiResponse> GetAllGenres()
        {
            var genres = await _genreRepository.GetAll().ToListAsync();

            if (genres == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("No genres found!", 400);
                return errorResponse;
            }

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<GenreBaseDto>("Genres retrieved successfully!", _mapper.Map<GenreBaseDto[]>(genres));
            return successResponse;

        }
    }
}
