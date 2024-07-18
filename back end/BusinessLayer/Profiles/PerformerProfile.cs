using AutoMapper;
using BusinessLayer.Models.PerformerDto;
using BusinessLayer.Models.User;
using DataLayer.Entities;

namespace BusinessLayer.Profiles
{
    public class PerformerProfile : Profile
    {
        public PerformerProfile()
        {
            CreateMap<PerformerDto, User>();
            CreateMap<User, PerformerDto>();
            CreateMap<PerformerDto, PerformerUpdateDto>();
            CreateMap<PerformerUpdateDto, PerformerDto>();
            CreateMap<PerformerCreateDto, User>();
            CreateMap<User, PerformerCreateDto>();
            CreateMap<User, PerformerUpdateDto>();
            CreateMap<PerformerUpdateDto, User>();
            CreateMap<UserRegistrationDto, User>();
            CreateMap<User, UserRegistrationDto>();
        }

    }
}
