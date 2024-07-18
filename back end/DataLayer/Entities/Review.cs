using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace DataLayer.Entities
{
    public class Review
    {
        public int Id { get; set; }
        public int? UserId { get; set; }
        public int? VenueId { get; set; }
        public int UserReviewId { get; set; }
        public float Grade { get; set; }
        public string? Description { get; set; } = string.Empty;

        public virtual User? User { get; set; }
        public virtual Venue? Venue { get; set; }
    }

    public class ReviewConfigurationBuilder : IEntityTypeConfiguration<Review>
    {
        public void Configure(EntityTypeBuilder<Review> builder)
        {
            builder.ToTable(nameof(Review));
            builder.HasKey(v => v.Id);
            builder.Property(v => v.Description).IsRequired(false);
            builder.Property(v => v.Grade).IsRequired();
            builder.HasOne(u => u.Venue)
                .WithMany(u => u.Reviews)
                .HasForeignKey(u => u.VenueId)
                .OnDelete(DeleteBehavior.Restrict);
            builder.HasOne(u => u.User)
                .WithMany(u => u.Reviews)
                .HasForeignKey(u => u.UserId)
                .OnDelete(DeleteBehavior.Restrict);
        }
    }
}
