using Core.Entities.Reviews;
using FluentValidation;

namespace Core.Helpers.ReviewValidators
{
    public class PerformerReviewValidator : AbstractValidator<PerformerReviewDto>
    {
        public PerformerReviewValidator()
        {
            RuleFor(r => r.Id).NotNull();

            RuleFor(r => r.Description).NotNull().WithMessage("Morate napisati opis recenzije!");

            RuleFor(r => r.Grade).NotNull().WithMessage("Morate dati ocjenu!");
            RuleFor(r => r.Grade).InclusiveBetween(1, 5).WithMessage("Ocjena mora biti između 1 i 5!");

            RuleFor(r => r.UserId).NotNull();
            RuleFor(r => r.UserReviewId).NotNull();
        }
    }
}
