function weibo_login(){
	$.get("/rush/account/goto_weibo",
		function(url){                   
		window.location.href=url;  
	});
}


function mail_check(){
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	if(reg.test(mailForm.mail.value)==false){
		alert("请输入正确格式的邮箱！");
		return false;
	}
}

//购买
function buyTicket(){
	$.get("/rush/buy/buyTicket",
	    function(state){
		    if(state==1){
		    	alert("购买成功！电影票信息稍后会发到您的邮箱");
		    }
		    else {
		    	alert("购买失败，抢购已结束");
		    }
		    var button = document.getElementById("buy-button");
			button.remove();
		})
}

/** 使用setInterval方法，每隔5秒钟调用一次此函数 **/
function getTicketsLeft(){
	var stop=self.setInterval(function(){
		$.get("/rush/buy/getTicketsLeft",
				function(ticketsleft){
				var element = document.getElementById("ticketsleft");
				element.textContent = ticketsleft;
				if(ticketsleft==0){
					window.clearInterval(stop);
					var button = document.getElementById("buy-button");
					button.remove();
				}
			});
		},1000)
}
