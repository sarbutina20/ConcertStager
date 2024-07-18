using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace DataLayer.Entities
{
    public class Venue
    {
        public int Id { get; set; }
        public int? UserId { get; set; }
        public string Decription { get; set; } = string.Empty;
        public string Name { get; set; } = string.Empty;
        public string City { get; set; } = string.Empty;
        public string Address { get; set; } = string.Empty;
        public int Capacity { get; set; }

        public required ICollection<Concert> Concerts { get; set; }
        public ICollection<Review>? Reviews { get; set; }

        public virtual User? User { get; set; }
    }

    public class VenueConfigurationBuilder : IEntityTypeConfiguration<Venue>
    {
        public void Configure(EntityTypeBuilder<Venue> builder)
        {
            builder.ToTable(nameof(Venue));
            builder.HasKey(v => v.Id);
            builder.Property(v => v.Decription).IsRequired();
            builder.Property(v => v.Name).IsRequired();
            builder.Property(v => v.City).IsRequired();
            builder.Property(v => v.Address).IsRequired();
            builder.Property(v => v.Capacity).IsRequired();

            builder.HasOne(v => v.User).WithOne(u => u.Venue).HasForeignKey<Venue>(v => v.UserId).IsRequired(false);
        }
    }
}
