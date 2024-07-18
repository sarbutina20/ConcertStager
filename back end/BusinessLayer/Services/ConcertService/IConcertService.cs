using BusinessLayer.Models.ConcertDto;
using BusinessLayer.Responses;

namespace BusinessLayer.Services.ConcertService
{
    public interface IConcertService
    {

        public Task<ApiResponse> GetConcertsAsync();

        public Task<ApiResponse> GetConcertAsync(int id);

        public Task<ApiResponse> CreateConcertAsync(ConcertCreateDto concertCreateDto);

        public Task<ApiResponse> GetConcertsByNameAsync(string name);
        public Task<ApiResponse> GetConcertsForOrganizer(int id);
        public Task<ApiResponse> GetVisitorUpcomingConcerts();
        public Task<ApiResponse> GetUpcomingConcerts();
        public Task<ApiResponse> GetFinishedConcerts();
        public Task<ApiResponse> GetAcceptedConcertsForPerformer(int id);
        public Task<ApiResponse> GetPendingConcertsForPerformer(int id);
        public Task<ApiResponse> GetPerformersForConcert(int concertId);
        public Task<ApiResponse> GetAllConcertsForVenue(int id);

    }
}
