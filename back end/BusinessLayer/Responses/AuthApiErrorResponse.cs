using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace BusinessLayer.Responses
{
    public class AuthApiErrorResponse : AuthApiResponse
    {
        public new bool Success { get; set; }
        public new string Message { get; set; } = string.Empty;
        public int error_code { get; set; }
        public string error_message { get; set; } = string.Empty;

        [JsonIgnore(Condition = JsonIgnoreCondition.WhenWritingNull)]
        public new object Data
        {
            get => Success ? base.Data : null;
            set => base.Data = value;
        }
        /*
        [JsonIgnore(Condition = JsonIgnoreCondition.WhenWritingNull)]
        public new string JWT
        {
            get => Success ? base.JWT : null;
            set => base.JWT = value;
        } */
    }
}
