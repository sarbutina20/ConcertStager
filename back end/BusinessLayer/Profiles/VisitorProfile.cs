using AutoMapper;
using BusinessLayer.Models.User;
using BusinessLayer.Models.VisitorDto;
using DataLayer.Entities;

namespace BusinessLayer.Profiles
{
    public class VisitorProfile : Profile
    {
        public VisitorProfile()
        {
            CreateMap<VisitorDto, User>();
            CreateMap<User, VisitorDto>();
            CreateMap<UserUpdateDto, User>();
            CreateMap<User, UserUpdateDto>();
            CreateMap<VisitorDto, UserUpdateDto>();
            CreateMap<UserUpdateDto, VisitorDto>();
            CreateMap<User, UserCreateDto>();
            CreateMap<UserCreateDto, User>();
            CreateMap<VisitorDto, UserCreateDto>();
            CreateMap<UserCreateDto, VisitorDto>();
        }
    }
}
