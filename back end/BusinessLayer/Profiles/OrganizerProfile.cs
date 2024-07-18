using AutoMapper;
using BusinessLayer.Models.OrganizerDto;
using DataLayer.Entities;

namespace BusinessLayer.Profiles
{
    public class OrganizerProfile : Profile
    {
        public OrganizerProfile()
        {
            CreateMap<User, OrganizerDto>();
            CreateMap<OrganizerDto, User>();
            CreateMap<OrganizerDto, OrganizerUpdateDto>();
            CreateMap<OrganizerUpdateDto, OrganizerDto>();
            CreateMap<OrganizerCreateDto, User>();
            CreateMap<User, OrganizerCreateDto>();
            CreateMap<User, OrganizerUpdateDto>();
            CreateMap<OrganizerUpdateDto, User>();
        }
    }
}
