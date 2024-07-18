namespace BusinessLayer.Responses
{
    public class ErrorApiResponse : ApiResponse
    {
        public int errorCode { get; set; }
        public string errorMessage { get; set; } = string.Empty;
    }
}
