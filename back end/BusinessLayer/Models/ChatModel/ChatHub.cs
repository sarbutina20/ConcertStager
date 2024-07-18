using Microsoft.AspNetCore.SignalR;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static Microsoft.EntityFrameworkCore.DbLoggerCategory.Database;

namespace BusinessLayer.Models.ChatModel
{
    public class ChatHub : Hub
    {
        
        private static Dictionary<string, string> userConnectionMap = new Dictionary<string, string>();

        public override Task OnConnectedAsync()
        {
            string userId = Context.GetHttpContext().Request.Query["userId"];
            string connectionId = Context.ConnectionId;

            userConnectionMap[userId] = connectionId;

            Groups.AddToGroupAsync(connectionId, userId);

            return base.OnConnectedAsync();
        }

        public async Task SendMessage(string userId, string message)
        {
            if (userConnectionMap.TryGetValue(userId, out var connId))
            {
                await Clients.Group(userId).SendAsync("ReceiveMessage", userId, message);
            }
            else
            {

            }
        }
    }
}
