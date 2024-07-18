using BusinessLayer.Models.ConcertDto;
using BusinessLayer.Responses;

namespace BusinessLayer.Services.ConcertEntryService
{
    public interface IConcertEntryService
    {
        public Task<ApiResponse> CreateConcertEntryAsync(ConcertEntryCreateDto concertEntryCreateDto);
        public Task<ApiResponse> GetConcertEntriesAsync(int concertId);

        public Task<ApiResponse> DeleteConcertEntry(int id);
        public Task<ApiResponse> AcceptDenyConcertEntry(int id, AcceptDenyConcertEntryDto concertEntry);

        public Task<ApiResponse> GetUnresolvedConcertEntries(int concertId);
    }
}
