var timeout = "";
var time = 1;

window.onload = function () {
	var contacts = document.getElementById("contacts");
	var contactList = contacts.getElementsByTagName("li");
	for (var i = 0; i < contactList.length; i++) {
		if (contactList.item(i).getAttribute("id") !== "empty_list") {
			contactList.item(i).onclick = controller.startChat;
		}
	}
	document.getElementById("search_box").onkeyup = controller.searchUser;
	document.getElementById("chat_div").onclick = view.removeBox;
	document.getElementById("header").onclick = view.removeBox;
	document.getElementById("message_box").onkeyup = controller.writeMessage;


};

var controller = {
	searchUser: function () {
		var search_result = document.getElementById("search_result");
		search_result.style.visibility = "visible";
		var srl = document.getElementById("showRequestList");
		srl.style.visibility = "hidden";
		var val = this.value;
		var data = "search_term=" + val;
		model.loadData(data, "searchUser", "load_data.php");
	},

	writeMessage: function (event) {
		if (event.keyCode === 13) {
			var str;
			var data;
			var fn = document.getElementById("friend_name");
			var chatId = fn.getAttribute("value");
			var msgbox = document.getElementById("message_box");
			var msg = msgbox.value;
			console.log(fn.innerHTML + chatId);

			msgbox.value = "";
			if (!msg) {
				return;
			}
			if (!Number(chatId)) {
			var friendId=fn.getAttribute("name");

				str = JSON.stringify({
					"chatId": 0,
					friendId:friendId ,
					"message": msg
				});
				data = "saveChat=" + str;
			model.loadData(data,"updateChatList","load_data.php");


			} else {
				
				str = JSON.stringify({
					"chatId": chatId,
					"message": msg
				});
				data = "saveChat=" + str;
				model.loadData(data,"","load_data.php");
			}
			


		}
	},
	startChat: function () {

		clearTimeout(timeout);
		var str;
		var chat = document.getElementById("chat");
		chat.innerHTML = "";
		var fn = document.getElementById("friend_name");
		fn.innerHTML = this.innerHTML;
		
		var chatId = this.getAttribute("id");

		if(!Number(chatId))
			{
		var friendId = this.getAttribute("name");
			fn.setAttribute("name",friendId);
			fn.setAttribute("value",0);

			str = JSON.stringify({
				"chatId": 0,
				friendId:friendId 
			});
			}
			else
			{
			fn.setAttribute("value",chatId);
				str = JSON.stringify({
				"chatId": chatId
			});
		
			}
		
		var data = "loadChat=" + str;
		console.log(data);
		model.loadData(data, "loadChat", "load_data.php");
	}



};