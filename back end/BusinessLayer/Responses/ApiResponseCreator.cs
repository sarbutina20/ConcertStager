namespace BusinessLayer.Responses
{
    public static class ApiResponseCreator
    {
        public static ErrorApiResponse CreateErrorResponse(string message, int errorCode)
        {
            var errorResponse = new ErrorApiResponse
            {
                Success = false,
                Message = message,
                errorCode = errorCode,
                errorMessage = message
            };

            return errorResponse;
        }

        public static SuccessfulApiResponse<T> CreateSuccessfulResponse<T>(string message, T[] data)
        {
            var response = new SuccessfulApiResponse<T>
            {
                Success = true,
                Message = message,
                Data = data
            };

            return response;
        }
    }
}
