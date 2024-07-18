using BusinessLayer.Models.ConcertDto;
using FluentValidation;

namespace BusinessLayer.Validators.ConcertValidators
{
    public class ConcertEntryValidator : AbstractValidator<ConcertEntryDto>
    {
        public ConcertEntryValidator()
        {
            RuleFor(e => e.Id).NotNull();
            RuleFor(e => e.Date).NotNull();
            RuleFor(e => e.isAccepted).NotNull();
            RuleFor(e => e.UserId).NotNull();
            RuleFor(e => e.ConcertId).NotNull();
        }
    }
}
