using Core.Entities.Reviews;
using FluentValidation;

namespace Core.Helpers.ReviewValidators
{
    public class VenueReviewValidator : AbstractValidator<VenueReviewDto>
    {
        public VenueReviewValidator()
        {
            RuleFor(r => r.Id).NotNull();

            RuleFor(r => r.Description).NotNull().WithMessage("Morate napisati opis recenzije!");

            RuleFor(r => r.Grade).NotNull().WithMessage("Morate dati ocjenu!");
            RuleFor(r => r.Grade).InclusiveBetween(1, 5).WithMessage("Ocjena mora biti između 1 i 5!");

            RuleFor(r => r.UserReviewId).NotNull();
            RuleFor(r => r.VenueId).NotNull();
        }
    }
}
