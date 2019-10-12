$(function(){
	$("#answerExchanges").on("click",function(){
		window.location.href = '/ask/portal/exchange'
	})
	$("#goHome").on("click",function(){
		window.history.back(-1)
	})
})
