using AutoMapper;
using BusinessLayer.Models.ReviewsDto;
using BusinessLayer.Responses;
using Core.Entities.Reviews;
using Core.Enumerations;
using Core.Helpers;
using DataLayer.Entities;
using DataLayer.Generic;
using Microsoft.EntityFrameworkCore;

namespace BusinessLayer.Services.ReviewService
{
    public class VenueReviewService : IVenueReviewService
    {
        private readonly IRepository<Review> _reviewRepository;
        private readonly IRepository<User> _userRepository;
        private readonly IRepository<Venue> _venueRepository;
        private readonly IMapper _mapper;

        public VenueReviewService(IRepository<Review> reviewRepository, IRepository<User> userRepository, IRepository<Venue> venueRepository, IMapper mapper)
        {
            _reviewRepository = reviewRepository;
            _userRepository = userRepository;
            _venueRepository = venueRepository;
            _mapper = mapper;
        }

        public async Task<ApiResponse> GetAllReviewsForVenue(int id)
        {
            var venueId = await _venueRepository.GetByIdAsync(id);
            if (venueId == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Venue not found.", 404);
                return errorResponse;
            }

            var allVenueReviews = await _reviewRepository.GetAll().Where(r => r.VenueId == id).ToListAsync();

            var response = ApiResponseCreator.CreateSuccessfulResponse<VenueReviewDto>("Successfully retrieved all reviews for this venue.", _mapper.Map<VenueReviewDto[]>(allVenueReviews));
            return response;
        }

        public async Task<ApiResponse> CreateReviewForVenue(VenueCreateReviewDto venueCreateReviewDto)
        {
            var venueId = await _venueRepository.GetByIdAsync(venueCreateReviewDto.VenueId);

            if (venueId == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Venue not found.", 404);
                return errorResponse;
            }

            var userReview = await _userRepository.GetByIdAsync(venueCreateReviewDto.UserReviewId);
            if ((userReview == null) || (userReview.RoleId != (int)Roles.Performer && userReview.RoleId != (int)Roles.Visitor))
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("User not found.", 404);
                return errorResponse;
            }
            if (!GradeValidator.isGradeValid(venueCreateReviewDto.Grade))
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Grade must be between 1 and 5.", 403);
                return errorResponse;
            }

            var existingReview = await _reviewRepository.GetAll().FirstOrDefaultAsync(r => r.UserReviewId == venueCreateReviewDto.UserReviewId && r.VenueId == venueCreateReviewDto.VenueId);
            if (existingReview != null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("User already reviewed this venue.", 403);
                return errorResponse;
            }

            var review = _mapper.Map<Review>(venueCreateReviewDto);
            _reviewRepository.Add(review);
            await _reviewRepository.SaveAsync();

            var response = ApiResponseCreator.CreateSuccessfulResponse<VenueReviewDto>("Successfully created review for this venue.", new VenueReviewDto[] { _mapper.Map<VenueReviewDto>(review) });
            return response;
        }

        public async Task<ApiResponse> UpdateReviewForVenue(int id, ReviewUpdateDto reviewUpdateDto)
        {
            var review = await _reviewRepository.GetByIdAsync(id);
            if (review == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Review not found.", 404);
                return errorResponse;
            }

            if (!GradeValidator.isGradeValid(reviewUpdateDto.Grade))
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Grade must be between 1 and 5.", 403);
                return errorResponse;
            }

            _mapper.Map(reviewUpdateDto, review);
            _reviewRepository.Update(review);
            await _reviewRepository.SaveAsync();

            var response = ApiResponseCreator.CreateSuccessfulResponse<VenueReviewDto>("Successfully updated review for this venue.", new VenueReviewDto[] { _mapper.Map<VenueReviewDto>(review) });
            return response;
        }

        public async Task<ApiResponse> DeleteReviewForVenue(int id, int userId)
        {
            var review = await _reviewRepository.GetByIdAsync(id);
            if (review == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Review not found.", 404);
                return errorResponse;
            }

            var userReview = await _userRepository.GetByIdAsync(userId);
            if ((userReview == null) || (userReview.RoleId != (int)Roles.Visitor && userReview.RoleId != (int)Roles.Performer) || (review.UserReviewId != userId))
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("User not found.", 404);
                return errorResponse;
            }

            _reviewRepository.Delete(review);
            await _reviewRepository.SaveAsync();

            var response = ApiResponseCreator.CreateSuccessfulResponse<VenueReviewDto>("Successfully deleted review for this venue.", new VenueReviewDto[] { _mapper.Map<VenueReviewDto>(review) });
            return response;
        }
    }
}
