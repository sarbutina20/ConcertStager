﻿namespace BusinessLayer.Models.VenueDto
{
    public class VenueCreateDto
    {
        public int UserId { get; set; }
        public string Name { get; set; } = string.Empty;
        public string Decription { get; set; } = string.Empty;
        public string City { get; set; } = string.Empty;
        public string Address { get; set; } = string.Empty;
        public int Capacity { get; set; }
    }
}
