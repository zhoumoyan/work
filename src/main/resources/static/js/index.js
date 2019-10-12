$(function() {
	$("#index_tipsOBtn").on("click",function(){
		window.location.href = '/ask/portal/activity'
	})
	$("#index_tipsT").on("click",function(){
		window.location.href = '/ask/portal/exchange'
	})
})
function gotoAnswerPage(){
	window.location.href = '/ask/portal/subject_page';
	window.localStorage.setItem("openId", $("#open_id").val());
}
