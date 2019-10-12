$(function(){
	$("#answerTickets").on("click",function(){
		window.location.href = '/ask/portal/tickets'
	})
	$(".ticketList").on("click",function(){
		$("#answerBox").hide();
		$("#answerBox2").show();
	})
	$("#exhomePage").on("click",function(){
		$("#answerBox").show();
		$("#answerBox2").hide();
	})
	function openaMark() {
		$('#answerMark').show();
		$("#answerbgMask").show();
    }
	function closeaMark(){
		$('#answerMark').hide();
		$("#answerbgMask").hide();
	}
	$("#confirmBtn").on("click",function(){
		//兑换成功
//		$("#exresult").text('兑换成功');
//		$("#exresult").removeClass("exSureFalse");
    	
    	//兑换失败
    	$("#exresult").text('可用积分不足，兑换失败');
    	$("#exresult").addClass("exSureFalse");
    	openaMark();
    	
    })
	$("#determineBtn").on("click",function(){
		closeaMark();
		setInterval(function(){
    		window.location.href = '/ask/portal/tickets'
    	},100);
	})
	$("#goHome").on("click",function(){
		window.history.back(-1)
	})
})