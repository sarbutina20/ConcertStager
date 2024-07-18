using DataLayer.Entities;
using Microsoft.EntityFrameworkCore;
using System.Reflection;

namespace DataLayer
{
    public class DataContext : DbContext
    {
        public DataContext(DbContextOptions<DataContext> options) : base(options)
        {

        }
        public DbSet<Venue> Venues { get; set; }

        public DbSet<User> Users { get; set; }
        public DbSet<Concert> Concerts { get; set; }

        public DbSet<ConcertEntries> ConcertEntries { get; set; }
        public DbSet<Genre> Genres { get; set; }
        public DbSet<Role> Roles { get; set; }
        public DbSet<Review> Reviews { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            modelBuilder.ApplyConfigurationsFromAssembly(Assembly.GetExecutingAssembly());
        }
    }
}
