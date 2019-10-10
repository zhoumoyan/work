$(function(){
	$("#ticket").on("click",function(){
		window.location.href = '../portal/exchange.html'
	});
	$("#exchange").on("click",function(){
		window.location.href = '../portal/exchange.html'
	});
	$("#goHome").on("click",function(){
		window.history.back(-1)
	})
});