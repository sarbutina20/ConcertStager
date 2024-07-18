using AutoMapper;
using BusinessLayer.Models.VenueDto;
using BusinessLayer.Responses;
using Core.Enumerations;
using DataLayer.Entities;
using DataLayer.Generic;
using Microsoft.EntityFrameworkCore;

namespace BusinessLayer.Services.VenueService
{
    public class VenueService : IVenueService
    {
        private readonly IRepository<Venue> _venueRepository;
        private readonly IRepository<User> _userRepository;
        private readonly IMapper _mapper;

        public VenueService(IRepository<Venue> venueRepository, IRepository<User> userRepository, IMapper mapper)
        {
            _venueRepository = venueRepository;
            _userRepository = userRepository;
            _mapper = mapper;
        }


        public async Task<ApiResponse> CreateVenueAsync(VenueCreateDto venueCreateDto)
        {
            var allVenues = await _venueRepository.GetAll().ToListAsync();
            var existingVenue = allVenues.FirstOrDefault(v => v.Name == venueCreateDto.Name);
            if (existingVenue != null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Venue already exists", 400);
                return errorResponse;
            }
            var organizer = await _userRepository.GetByIdAsync(venueCreateDto.UserId);
            if ((organizer == null) || (organizer.RoleId != (int)Roles.Organizer))
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Organizer not exists", 400);
                return errorResponse;
            }

            var organizerVenue = await _venueRepository.GetAll().FirstOrDefaultAsync(v => v.UserId == venueCreateDto.UserId);
            if (organizerVenue != null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Organizer already has a venue", 400);   
                return errorResponse;
            }

            var venue = _mapper.Map<Venue>(venueCreateDto);
            _venueRepository.Add(venue);
            await _venueRepository.SaveAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<VenueDto>("Venue created successfully", new VenueDto[] { _mapper.Map<VenueDto>(venue) });
            return successResponse;
        }

        public async Task<ApiResponse> GetOrganizerVenuesAsync(int organizerId)
        {
            var venues = await _venueRepository.GetAll().Where(v => v.UserId == organizerId).ToListAsync();
            if (venues.Count == 0)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("No venues found", 400);
                return errorResponse;
            }

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<VenueDto>("Venues found successfully", _mapper.Map<VenueDto[]>(venues));
            return successResponse;
        }

        public async Task<ApiResponse> GetVenueAsync(int id)
        {
            var venue = await _venueRepository.GetByIdAsync(id);
            if (venue == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Venue not found", 404);
                return errorResponse;
            }

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<VenueDto>("Venue found successfully", new VenueDto[] { _mapper.Map<VenueDto>(venue) });
            return successResponse;
        }

        public async Task<ApiResponse> GetVenuesAsync()
        {
            var venues = await _venueRepository.GetAll().ToListAsync();
            if (venues.Count == 0)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("No venues found", 404);
                return errorResponse;
            }

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<VenueDto>("Venues found successfully", _mapper.Map<VenueDto[]>(venues));
            return successResponse;
        }

        public async Task<ApiResponse> UpdateVenueAsync(int id, VenueUpdateDto venueUpdateDto)
        {
            var venueEntity = await _venueRepository.GetByIdAsync(id);

            if (venueEntity == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Venue not found", 404);
                return errorResponse;
            }

            _mapper.Map(venueUpdateDto, venueEntity);
            _venueRepository.Update(venueEntity);
            await _venueRepository.SaveAsync();

            var successResponse = ApiResponseCreator.CreateSuccessfulResponse<VenueDto>("Venue updated successfully", new VenueDto[] { _mapper.Map<VenueDto>(venueEntity) });
            return successResponse;
        }
    }
}
