using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace DataLayer.Migrations
{
    /// <inheritdoc />
    public partial class FixedGenreIdProperty : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_User_GenreId",
                table: "User");

            migrationBuilder.CreateIndex(
                name: "IX_User_GenreId",
                table: "User",
                column: "GenreId");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_User_GenreId",
                table: "User");

            migrationBuilder.CreateIndex(
                name: "IX_User_GenreId",
                table: "User",
                column: "GenreId",
                unique: true,
                filter: "[GenreId] IS NOT NULL");
        }
    }
}
