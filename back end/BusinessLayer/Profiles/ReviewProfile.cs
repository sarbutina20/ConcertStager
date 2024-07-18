using AutoMapper;
using BusinessLayer.Models.ReviewsDto;
using Core.Entities.Reviews;
using DataLayer.Entities;

namespace BusinessLayer.Profiles
{
    public class ReviewProfile : Profile
    {
        public ReviewProfile()
        {
            CreateMap<Review, PerformerReviewDto>();
            CreateMap<PerformerReviewDto, Review>();
            CreateMap<Review, PerformerReviewCreateDto>();
            CreateMap<PerformerReviewCreateDto, Review>();
            CreateMap<Review, VenueReviewDto>();
            CreateMap<VenueReviewDto, Review>();
            CreateMap<Review, VenueCreateReviewDto>();
            CreateMap<VenueCreateReviewDto, Review>();
            CreateMap<Review, ReviewUpdateDto>();
            CreateMap<ReviewUpdateDto, Review>();
            CreateMap<Review, UserReviewDto>();
            CreateMap<UserReviewDto, Review>();
        }
    }
}
