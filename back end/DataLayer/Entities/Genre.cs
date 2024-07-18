using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace DataLayer.Entities
{
    public class Genre
    {
        public int Id { get; set; }
        public string Name { get; set; } = string.Empty;
        public virtual User? User { get; set; }
    }

    public class GenreConfigurationBuilder : IEntityTypeConfiguration<Genre>
    {
        public void Configure(EntityTypeBuilder<Genre> builder)
        {
            builder.ToTable(nameof(Genre));
            builder.HasKey(v => v.Id);
            builder.Property(v => v.Name).IsRequired();
        }
    }
}
