namespace BusinessLayer.Responses
{
    public class SuccessfulApiResponse<T> : ApiResponse
    {
        public T[]? Data { get; set; }
    }
}
