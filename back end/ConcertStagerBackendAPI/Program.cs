using BusinessLayer.Models.ChatModel;
using BusinessLayer.Models.User;
using BusinessLayer.Services;
using BusinessLayer.Services.AuthService;
using BusinessLayer.Services.ConcertEntryService;
using BusinessLayer.Services.ConcertService;
using BusinessLayer.Services.GenreService;
using BusinessLayer.Services.Login;
using BusinessLayer.Services.OrganizerService;
using BusinessLayer.Services.PerformerService;
using BusinessLayer.Services.ReviewService;
using BusinessLayer.Services.UserService;
using BusinessLayer.Services.VenueService;
using BusinessLayer.Services.VisitorService;
using Core.Enumerations;
using DataLayer;
using DataLayer.Entities;
using DataLayer.Generic;
using FluentValidation;
using FluentValidation.AspNetCore;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using Microsoft.OpenApi.Models;
using Swashbuckle.AspNetCore.Filters;
using System.Security.Claims;
using System.Text;
using Microsoft.AspNetCore.SignalR;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

#region Cors
builder.Services.AddCors(options =>
{
    options.AddPolicy("CorsPolicy",
        policy =>
        {
            policy.AllowAnyOrigin().AllowAnyHeader().AllowAnyMethod();
        });
});
#endregion


builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();

#region Swagger

builder.Services.AddSwaggerGen(options =>
{
    options.AddSecurityDefinition("oauth2", new OpenApiSecurityScheme()
    {
        Description = "Standard Authorization header using the Bearer scheme (\"bearer {token}\"",
        In = ParameterLocation.Header,
        Name = "Authorization",
        Type = SecuritySchemeType.ApiKey
    });
    options.AddSecurityRequirement(new OpenApiSecurityRequirement
    {
        {
            new OpenApiSecurityScheme
            {
                Reference = new OpenApiReference
                {
                    Type = ReferenceType.SecurityScheme,
                    Id = "oauth2"
                }
            },
            new string[] { }
        }
    });
    options.OperationFilter<SecurityRequirementsOperationFilter>();
});

#endregion

#region Authentication

builder.Services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
    .AddJwtBearer(options =>
    {
        options.TokenValidationParameters = new TokenValidationParameters
        {
            ValidateIssuerSigningKey = true,
            IssuerSigningKey =
                new SymmetricSecurityKey(Encoding.UTF8.GetBytes(builder.Configuration.GetSection("Jwt:Key").Value)),
            ValidateIssuer = false,
            ValidateAudience = false,
            RoleClaimType = ClaimTypes.Role
        };
    });

#endregion

#region Validations

builder.Services.AddValidatorsFromAssemblyContaining<IValidatorsMarker>();
builder.Services.AddFluentValidationAutoValidation();

#endregion

#region AutoMapper

builder.Services.AddAutoMapper(AppDomain.CurrentDomain.GetAssemblies());

builder.Services.AddAutoMapper(config =>
{
    config.CreateMap<UserDtoBase, User>();
    config.CreateMap<User, UserDtoBase>();
    config.CreateMap<User, UserCreateDto>();
    config.CreateMap<UserCreateDto, User>();
});

#endregion

#region Chat
builder.Services.AddSignalR();
#endregion

#region Services
builder.Services.AddScoped<IAuthService, AuthService>();
builder.Services.AddScoped<IUserService, UserService>();
builder.Services.AddScoped<IVisitorService, VisitorService>();
builder.Services.AddScoped<IVenueService, VenueService>();
builder.Services.AddScoped<IPerformerService, PerformerService>();
builder.Services.AddScoped<IOrganizerService, OrganizerService>();
builder.Services.AddScoped<IGoogleLoginService, GoogleLoginService>();
builder.Services.AddScoped<IJwtTokenService, JwtTokenService>();
builder.Services.AddScoped<IConcertService, ConcertService>();
builder.Services.AddScoped<IGenreService, GenreService>();
builder.Services.AddScoped<IPerformerReviewService, PerformerReviewService>();
builder.Services.AddScoped<IVenueReviewService, VenueReviewService>();
builder.Services.AddScoped<IConcertEntryService, ConcertEntryService>();

#endregion

#region Repositories

builder.Services.AddScoped<IRepository<User>, Repository<User>>();
builder.Services.AddScoped<IRepository<Concert>, Repository<Concert>>();
builder.Services.AddScoped<IRepository<ConcertEntries>, Repository<ConcertEntries>>();
builder.Services.AddScoped<IRepository<Venue>, Repository<Venue>>();
builder.Services.AddScoped<IRepository<Role>, Repository<Role>>();
builder.Services.AddScoped<IRepository<Genre>, Repository<Genre>>();
builder.Services.AddScoped<IRepository<Review>, Repository<Review>>();




builder.Services.AddScoped<IReadOnlyRepository<Venue>, ReadOnlyRepository<Venue>>();
builder.Services.AddScoped<IReadOnlyRepository<User>, ReadOnlyRepository<User>>();
builder.Services.AddScoped<IReadOnlyRepository<Role>, ReadOnlyRepository<Role>>();
builder.Services.AddScoped<IReadOnlyRepository<Genre>, ReadOnlyRepository<Genre>>();
builder.Services.AddScoped<IReadOnlyRepository<Concert>, ReadOnlyRepository<Concert>>();
builder.Services.AddScoped<IReadOnlyRepository<ConcertEntries>, ReadOnlyRepository<ConcertEntries>>();
builder.Services.AddScoped<IReadOnlyRepository<Review>, ReadOnlyRepository<Review>>();
#endregion

#region Database

builder.Services.AddDbContext<DataContext>(options =>
{
    options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection"));
});

#endregion

var app = builder.Build();

app.UseRouting();
app.UseAuthorization();
app.UseEndpoints(endpoints =>
{
    endpoints.MapHub<ChatHub>("/chathub");
});
// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}



app.UseHttpsRedirection();

app.UseAuthentication();

app.MapControllers();

app.Run();