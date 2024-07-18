using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace DataLayer.Entities
{
    public class ConcertEntries
    {
        public int Id { get; set; }
        public int UserId { get; set; }
        public int ConcertId { get; set; }
        public DateTime Date { get; set; } = DateTime.Now;
        public bool? isAccepted { get; set; } = null;

        public virtual required Concert Concert { get; set; }
        public virtual required User User { get; set; }

        public class ConcertEntriesConfigurationBuilder : IEntityTypeConfiguration<ConcertEntries>
        {
            public void Configure(EntityTypeBuilder<ConcertEntries> builder)
            {
                builder.ToTable(nameof(ConcertEntries));
                builder.HasKey(v => v.Id);
                builder.Property(v => v.Date).IsRequired();
                builder.Property(v => v.isAccepted).IsRequired(false);


                builder.HasOne(u => u.Concert)
                    .WithMany(u => u.ConcertEntries)
                    .HasForeignKey(u => u.ConcertId)
                    .OnDelete(DeleteBehavior.Cascade);

                builder.HasOne(u => u.User)
                    .WithMany(u => u.ConcertEntries)
                    .HasForeignKey(u => u.UserId)
                    .OnDelete(DeleteBehavior.Cascade);

            }
        }

    }
}
