using AutoMapper;
using BusinessLayer.Models.User;
using BusinessLayer.Models.VenueDto;
using DataLayer.Entities;

namespace BusinessLayer.Profiles
{
    public class VenueProfile : Profile
    {
        public VenueProfile()
        {
            CreateMap<VenueDto, Venue>();
            CreateMap<Venue, VenueDto>();
            CreateMap<VenueDto, UserUpdateDto>();
            CreateMap<UserUpdateDto, VenueDto>();
            CreateMap<Venue, VenueUpdateDto>();
            CreateMap<VenueUpdateDto, Venue>();
            CreateMap<Venue, VenueCreateDto>();
            CreateMap<VenueCreateDto, Venue>();
        }
    }
}
