using BusinessLayer.Models.VisitorDto;
//using Core.Entities.Users;
using FluentValidation;

namespace Core.Helpers.UserValidators
{
    public class VisitorValidator : AbstractValidator<VisitorDto>
    {
        public VisitorValidator()
        {
            RuleFor(u => u.Id).NotNull();

            RuleFor(u => u.Name).NotNull().WithMessage("Morate upisati svoje ime i prezime!");
            RuleFor(u => u.Name).Matches("^[A-Za-z]+ [A-Za-z]+$").WithMessage("Vaše ime mora sadržavati samo slova");

            RuleFor(u => u.Email).NotNull().WithMessage("Morate upisati svoju email adresu");
            RuleFor(u => u.Email).EmailAddress().WithMessage("Morate napisati ispravnu email adresu!");

            RuleFor(u => u.RoleId).NotNull();
        }
    }
}
