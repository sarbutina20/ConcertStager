﻿namespace BusinessLayer.Responses;

public class AuthApiResponse
{
    public bool Success { get; set; }
    public string Message { get; set; } = string.Empty;
    public object? Data { get; set; }
}