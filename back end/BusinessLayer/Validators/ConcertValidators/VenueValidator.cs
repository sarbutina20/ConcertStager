using BusinessLayer.Models.VenueDto;
using FluentValidation;

namespace BusinessLayer.Validators.ConcertValidators
{
    public class VenueValidator : AbstractValidator<VenueDto>
    {
        public VenueValidator()
        {
            RuleFor(v => v.Id).NotNull();

            RuleFor(v => v.UserId).NotNull();

            RuleFor(v => v.Name).NotNull().WithMessage("Morate napisati ime mjesta održavanja koncerta!");
            RuleFor(v => v.Name).Matches("^[A-Za-z]+$").WithMessage("Ime prostora mora sadržavati samo slova!");

            RuleFor(v => v.Address).NotNull().WithMessage("Morate napisati adresu mjesta održavanja koncerta!");
            RuleFor(v => v.Address).Matches("^(?=.*[A-Za-z])(?=.*\\d).+$").WithMessage("Adresa prostora mora sadržavati samo slova i brojeve!");

            RuleFor(v => v.City).NotNull().WithMessage("Morate napisati grad mjesta održavanja koncerta!");
            RuleFor(v => v.City).Matches("^[A-Za-z]+$").WithMessage("Ime grada mora sadržavati samo slova!");

            RuleFor(v => v.Capacity).NotNull().WithMessage("Morate napisati kapacitet mjesta održavanja!");
        }
    }
}
