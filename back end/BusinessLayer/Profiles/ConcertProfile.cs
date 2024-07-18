using AutoMapper;
using BusinessLayer.Models.ConcertDto;
using Core.Entities.Concert;
using DataLayer.Entities;

namespace BusinessLayer.Profiles
{
    public class ConcertProfile : Profile
    {
        public ConcertProfile()
        {
            CreateMap<Concert, ConcertDto>();
            CreateMap<ConcertDto, Concert>();
            CreateMap<ConcertCreateDto, Concert>();
            CreateMap<Concert, ConcertCreateDto>();
            CreateMap<ConcertEntryCreateDto, ConcertEntries>();
            CreateMap<ConcertEntries, ConcertEntryCreateDto>();
            CreateMap<ConcertEntryDto, ConcertEntries>();
            CreateMap<ConcertEntries, ConcertEntryDto>();
            CreateMap<ConcertEntries, AcceptDenyConcertEntryDto>();
            CreateMap<AcceptDenyConcertEntryDto, ConcertEntries>();
        }
    }
}
