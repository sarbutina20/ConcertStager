using AutoMapper;
using BusinessLayer.Models.GenreDto;
using DataLayer.Entities;

namespace BusinessLayer.Profiles
{
    public class GenreProfile : Profile
    {
        public GenreProfile()
        {
            CreateMap<GenreBaseDto, Genre>();
            CreateMap<Genre, GenreBaseDto>();
        }
    }
}
