using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace DataLayer.Entities
{
    public class Concert
    {
        public int Id { get; set; }
        public int VenueId { get; set; }
        public int UserId { get; set; }
        public string Description { get; set; } = string.Empty;
        public string Name { get; set; } = string.Empty;
        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }
        public virtual required Venue Venue { get; set; }
        public virtual required User User { get; set; }
        public required ICollection<ConcertEntries> ConcertEntries { get; set; }

        public class ConcertConfigurationBuilder : IEntityTypeConfiguration<Concert>
        {
            public void Configure(EntityTypeBuilder<Concert> builder)
            {
                builder.ToTable(nameof(Concert));
                builder.HasKey(v => v.Id);
                builder.Property(v => v.Name).IsRequired();
                builder.Property(v => v.Description).IsRequired();
                builder.Property(v => v.StartDate).IsRequired();
                builder.Property(v => v.EndDate).IsRequired();
                builder.HasOne(u => u.Venue)
                    .WithMany(u => u.Concerts)
                    .HasForeignKey(u => u.VenueId)
                    .OnDelete(DeleteBehavior.Restrict);
                builder.HasOne(u => u.User)
                    .WithMany(u => u.Concerts)
                    .HasForeignKey(u => u.UserId)
                    .OnDelete(DeleteBehavior.Restrict);
            }
        }
    }
}