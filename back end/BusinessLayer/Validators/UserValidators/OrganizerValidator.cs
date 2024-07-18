using BusinessLayer.Models.OrganizerDto;
//using Core.Entities.Users;
using FluentValidation;

namespace Core.Helpers.UserValidators
{
    public class OrganizerValidator : AbstractValidator<OrganizerDto>
    {
        public OrganizerValidator()
        {
            RuleFor(u => u.Id).NotNull();

            RuleFor(u => u.GoogleId).NotNull();

            RuleFor(u => u.Name).NotNull().WithMessage("Morate upisati svoje ime i prezime!");
            RuleFor(u => u.Name).Matches("^[A-Za-z]+ [A-Za-z]+$").WithMessage("Vaše ime mora sadržavati samo slova");

            RuleFor(o => o.ContactNumber).NotNull().WithMessage("Morate napisati svoj kontakt broj!");
            RuleFor(o => o.ContactNumber).Matches("^[0-9]+$").WithMessage("Kontakt broj mora sadržavati samo brojeve!");

            RuleFor(u => u.Email).NotNull().WithMessage("Morate upisati svoju email adresu");
            RuleFor(u => u.Email).EmailAddress().WithMessage("Morate napisati ispravnu email adresu!");

            RuleFor(u => u.RoleId).NotNull();
        }
    }
}
