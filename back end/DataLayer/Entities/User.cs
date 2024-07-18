using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace DataLayer.Entities;

public class User
{
    public int Id { get; set; }
    public int RoleId { get; set; }
    public int? GenreId { get; set; }
    public string? GoogleId { get; set; } = string.Empty;
    public string Name { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
    public string? ContactNumber { get; set; }

    public ICollection<Concert>? Concerts { get; set; }

    public required ICollection<ConcertEntries> ConcertEntries { get; set; }
    public ICollection<Review>? Reviews { get; set; }
    public virtual Role? Role { get; set; }
    public virtual Genre? Genre { get; set; }

    public virtual Venue? Venue { get; set; }
}

public class UserConfigurationBuilder : IEntityTypeConfiguration<User>
{
    public void Configure(EntityTypeBuilder<User> builder)
    {
        builder.ToTable(nameof(User));
        builder.HasKey(v => v.Id);
        builder.Property(v => v.Email).IsRequired();
        builder.HasIndex(v => v.Email).IsUnique();
        builder.Property(v => v.RoleId).IsRequired();
        builder.Property(v => v.ContactNumber).IsRequired(false);
        builder.Property(v => v.GenreId).IsRequired(false);

        builder.HasOne(p => p.Genre).WithOne(g => g.User).HasForeignKey<User>(p => p.GenreId).IsRequired(false);
        builder.HasOne(p => p.Role).WithMany(g => g.Users).HasForeignKey(p => p.RoleId).OnDelete(DeleteBehavior.Cascade);

        builder.HasIndex(v => v.RoleId).IsUnique(false);
        builder.HasIndex(v => v.GenreId).IsUnique(false);
    }
}