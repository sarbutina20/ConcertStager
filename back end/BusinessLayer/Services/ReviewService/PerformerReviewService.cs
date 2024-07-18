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
    public class PerformerReviewService : IPerformerReviewService
    {
        private readonly IRepository<Review> _reviewRepository;
        private readonly IRepository<User> _userRepository;
        private readonly IMapper _mapper;

        public PerformerReviewService(IRepository<Review> reviewRepository, IRepository<User> userRepository, IMapper mapper)
        {
            _userRepository = userRepository;
            _reviewRepository = reviewRepository;
            _mapper = mapper;
        }

        public async Task<ApiResponse> GetAllReviewsForPerformer(int id)
        {
            var performer = await _userRepository.GetAll().Where(p => p.Id == id && p.RoleId == (int)Roles.Performer).ToListAsync();
            if (performer.Count == 0)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Performer not found.", 404);
                return errorResponse;
            }
            var allPerformerReviews = await _reviewRepository.GetAll().Where(r => r.UserId == id).ToListAsync();

            var response = ApiResponseCreator.CreateSuccessfulResponse<PerformerReviewDto>("Successfully retrieved reviews for this performer.", _mapper.Map<PerformerReviewDto[]>(allPerformerReviews));
            return response;
        }

        public async Task<ApiResponse> UpdateReviewForPerformer(int id, ReviewUpdateDto reviewUpdateDto)
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

            var response = ApiResponseCreator.CreateSuccessfulResponse<PerformerReviewDto>("Successfully updated review for this performer.", new PerformerReviewDto[] { _mapper.Map<PerformerReviewDto>(review) });
            return response;
        }

        public async Task<ApiResponse> DeleteReviewForPerformer(int id, int userId)
        {
            var review = await _reviewRepository.GetByIdAsync(id);
            if (review == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Review not found.", 404);
                return errorResponse;
            }

            var userReview = await _userRepository.GetByIdAsync(userId);
            if ((userReview == null) || (userReview.RoleId != (int)Roles.Visitor && userReview.RoleId != (int)Roles.Organizer) || (review.UserReviewId != userId))
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("UserReview not found or has role Performer.", 404);
                return errorResponse;
            }

            _reviewRepository.Delete(review);
            await _reviewRepository.SaveAsync();

            var response = ApiResponseCreator.CreateSuccessfulResponse<PerformerReviewDto>("Successfully deleted review for this performer.", new PerformerReviewDto[] { _mapper.Map<PerformerReviewDto>(review) });
            return response;
        }

        public async Task<ApiResponse> CreateReviewForPerformer(PerformerReviewCreateDto performerReviewCreateDto)
        {

            var performer = await _userRepository.GetAll().Where(p => p.Id == performerReviewCreateDto.UserId && p.RoleId == (int)Roles.Performer).ToListAsync();
            if (performer.Count == 0)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Performer not found.", 404);
                return errorResponse;
            }

            if (!GradeValidator.isGradeValid(performerReviewCreateDto.Grade))
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Grade must be between 1 and 5.", 403);
                return errorResponse;
            }

            var userReview = await _userRepository.GetByIdAsync(performerReviewCreateDto.UserReviewId);
            if ((userReview == null) || (userReview.RoleId != (int)Roles.Organizer && userReview.RoleId != (int)Roles.Visitor))
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("UserReview not found or has role Performer.", 404);
                return errorResponse;
            }

            var existingReview = await _reviewRepository.GetAll().FirstOrDefaultAsync(r => r.UserId == performerReviewCreateDto.UserId && r.UserReviewId == performerReviewCreateDto.UserReviewId);
            if (existingReview != null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("Review already exists.", 403);
                return errorResponse;
            }

            var review = _mapper.Map<Review>(performerReviewCreateDto);
            _reviewRepository.Add(review);
            await _reviewRepository.SaveAsync();

            var response = ApiResponseCreator.CreateSuccessfulResponse<PerformerReviewDto>("Successfully created review for this performer.", new PerformerReviewDto[] { _mapper.Map<PerformerReviewDto>(review) });
            return response;
        }

        public async Task<ApiResponse> GetAllReviewsByUser(int id)
        {
            var user = await _userRepository.GetByIdAsync(id);
            if(user == null)
            {
                var errorResponse = ApiResponseCreator.CreateErrorResponse("User not found.", 404);
                return errorResponse;
            }

            var allUserReviews = await _reviewRepository.GetAll().Where(r => r.UserReviewId == id).ToListAsync();

            var response = ApiResponseCreator.CreateSuccessfulResponse<UserReviewDto>("Successfully retrieved reviews for this user.", _mapper.Map<UserReviewDto[]>(allUserReviews));
            return response;
        }
    }

}
