// JavaScript Document
	var messagetime=1;
	var msg= document.getElementById("msg");
	msg.onfocus=function()
	{
		loader=setInterval(loadChat,1000);
	}
	msg.onblur=function()
	{
				setTimeout(closeConn,10000,loader)
	}
	function closeConn(loader)
	{
		clearInterval(loader);
		
	}


function ajax(type,msg)
{
	try
	{
	xhr= new XMLHttpRequest();

	xhr.open("POST","../php/savechat.php",true);
	xhr.setRequestHeader("content-type","application/x-www-form-urlencoded");
		var str=type+"="+msg;
		var send=xhr.send(str);	
	}
catch(failed)
{
	clearInterval(loader);
	
	

}
xhr.onreadystatechange=function()
	{
		if(xhr.readyState==4)
		{
			if((xhr.status>=200&& xhr.status<300)||(xhr.status==305))
			{
				if(xhr.responseText)
				{
			console.log(xhr.responseText);

					var arr=JSON.parse(xhr.responseText);
					for(var a in arr)
					{
					showMessage(arr[a].msg);	
					messagetime=arr[a].messagetime;
					}
					
				}
			}
					
		}
			
	}
}



function showMessage(response)
{
				var ul=document.getElementById("chatLog");
				
				var li=document.createElement("li");
				li.className="send";
				li.appendChild(document.createTextNode(response));
				ul.appendChild(li);
				form1.elements.item(0).value="";
			ul.scrollTop = ul.scrollHeight - ul.clientHeight;

								
}
function loadChat()
{
	ajax("load_data",messagetime);

}

chatstart=function(event)
{
	event.preventDefault();
	var msg=form1.elements.item(0).value;
		time=new Date().getTime();
		if(msg)
		{

	var json_msg={msg:escape(msg),time:time,user_id:1,friend_id:2};
	json_data=JSON.stringify(json_msg);
	ajax("insert_data",json_data);
		}
		else
		{
		alert("please enter something");	
		}
}

document.forms.item(0).onsubmit=chatstart;
