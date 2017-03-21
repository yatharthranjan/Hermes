// JavaScript Document

function shownotificationBox()
{
	var id=this.id;
	var search_result=document.getElementById("search_result");
	search_result.style.visibility="hidden";
	var data;
	if(id==="friend_request_div")
	{
		var c1=document.getElementById("count_request_div")
				var v1=c1.innerHTML;

		if(!Number(v1))
			{
			return false;	
			}
			c1.innerHTML="0";
			 data="sendRequestData="+true;

		loadData(data,"createRequestBox","check_data.php");
	}
	else if(id==="notification_div")
	{
		
		var c2=document.getElementById("count_notification_div")
					var v2=c2.innerHTML;
		
			if(!Number(v2))
			{
			return false;	
			}
		c2.innerHTML="0";		
		data="sendNotificationData="+true;

			loadData(data,"createNotificationBox","check_data.php");
	
		
	}
}

function createRequestBox(arr)
{
	
	console.log(arr);
	
	var ul=document.getElementById("showRequestList");
		ul.innerHTML="";
			ul.style.visibility="visible";
            for(var i=0;i<arr.length;i++)
						{
							var userId=Number(arr[i].user_id);
							var username=arr[i].username;
							var name=arr[i].name;
							var pic=arr[i].profile_pic;
							var li= document.createElement("li");
							var a1= document.createElement("a");
							var a2= document.createElement("a");
							var img= document.createElement("img");
							var a3= document.createElement("a");

							var info=document.createTextNode(username);
							var info2=document.createTextNode(" ("+name+")");

							var txt1=document.createTextNode("Accept");
							var txt2=document.createTextNode("Reject");
							
							li.setAttribute("id",userId);
							img.setAttribute("src",pic);
							a2.setAttribute("class","accept");
							a3.setAttribute("class","reject");
	
								a1.appendChild(info);
								a1.appendChild(info2);
								a3.appendChild(txt2);
								a2.appendChild(txt1);
								
										li.appendChild(img);
										li.appendChild(a1);
										li.appendChild(a3);
										li.appendChild(a2);
										ul.appendChild(li);

						a1.onclick=showUserProfile;						
						a2.onclick=response;
						a3.onclick=response;

						}
}
function response()
{
	var response=this.className;
	this.innerHTML="Done";
	var p=this.parentNode;
	p.disabled=true;
	var friend_id=p.id;
	setTimeout(function()
			   {
	p.style.display="none";
	},500);
	var data="updateRequestData="+JSON.stringify({"response":response,"friend_id":friend_id});
	
	loadData(data,"","check_data.php");

	
	
}

function createNotificationBox(arr)
{
var ul=document.getElementById("showRequestList");
		ul.innerHTML="";
ul.style.visibility="visible";
	 for(var i=0;i<arr.length;i++)
						{
							var userId=Number(arr[i].user_id);
							var username=arr[i].username;
							var name=arr[i].name;
						var pic=arr[i].profile_pic;

							var li= document.createElement("li");
							var a1= document.createElement("a");
							var img= document.createElement("img");
							var info=document.createTextNode(username);
							var info2=document.createTextNode(" ("+name+")");
							var info3=document.createTextNode(" has accepted your friend request");

							li.setAttribute("id",userId);
							img.setAttribute("src",pic);
							
								a1.appendChild(info);
								a1.appendChild(info2);
								a1.appendChild(info3);

							li.appendChild(img);
							li.appendChild(a1);
							ul.appendChild(li);

							
						}
	
	
}