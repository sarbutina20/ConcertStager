using Google.Apis.Auth;

namespace BusinessLayer.Services.Login
{
    public class GoogleLoginService : IGoogleLoginService
    {
        private readonly string _clientId;

        public GoogleLoginService()
        {
            _clientId = "759654001215-35engom30hdklp8vd9apfua4pndtquhg.apps.googleusercontent.com";
        }

        // in this case we use idToken from Google
        public async Task<bool> Verify(object itemToVerify)
        {
            try
            {
                var settings = new GoogleJsonWebSignature.ValidationSettings
                {
                    Audience = new[] { _clientId }
                };

                var payload = await GoogleJsonWebSignature.ValidateAsync((string)itemToVerify, settings);

                return (payload != null) ? true : false;
            }
            catch (InvalidJwtException ex)
            {
                Console.WriteLine("Invalid ID token: " + ex.Message);
                return false;
            }
            catch (Exception ex)
            {
                Console.WriteLine("An unexpected error occurred: " + ex.Message);
                return false;
            }
        }
    }
}
