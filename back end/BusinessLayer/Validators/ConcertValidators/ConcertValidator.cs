using BusinessLayer.Models.ConcertDto;
using FluentValidation;

namespace BusinessLayer.Validators.ConcertValidators
{
    public class ConcertValidator : AbstractValidator<ConcertCreateDto>
    {
        public ConcertValidator()
        {

            RuleFor(c => c.Name).NotNull().NotEmpty().WithMessage("Morate napisati ime koncerta!");


            RuleFor(c => c.StartDate).NotNull().NotEmpty().WithMessage("Morate napisati početak koncerta!");

            RuleFor(c => c.EndDate).NotNull().NotEmpty().WithMessage("Morate napisati kraj koncerta!");

            RuleFor(c => c.VenueId).NotNull().NotEmpty().WithMessage("Morate napisati ID mjesta održavanja koncerta!");

            RuleFor(c => c.UserId).NotNull().NotEmpty().WithMessage("Morate napisati ID organizatora koncerta!");
        }
    }
}
