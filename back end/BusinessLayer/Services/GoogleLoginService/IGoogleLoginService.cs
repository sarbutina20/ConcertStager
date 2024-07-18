namespace BusinessLayer.Services.Login
{
    public interface IGoogleLoginService
    {
        public Task<bool> Verify(object itemToVerify);
    }
}
