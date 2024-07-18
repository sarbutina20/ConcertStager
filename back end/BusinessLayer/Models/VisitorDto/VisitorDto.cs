using BusinessLayer.Models.User;

namespace BusinessLayer.Models.VisitorDto
{
    public class VisitorDto : UserCreateDto
    {
        public int Id { get; set; }
    }
}
