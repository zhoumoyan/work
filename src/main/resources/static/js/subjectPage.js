$(function(){
	//获取题目
	$.ajax({
		type:"get",
		url:"static.json",
		async:true,
		success:function(res){
			$("#answerSubject").find("li").eq(0).find(".subjects").empty();
			for(var key in res){
				var html = '<p class="subjects_one">'+(parseInt(key)+1)+'、'+res[key].title+'</p>';
				$("#answerSubject").find("li").eq(0).find(".subjects").append(html);
				var optionContent  = res[key].options;
				for (var index in optionContent) {
					var optionsHtml = '<p><label class="test-label"><input type="checkbox" class="test-radio" name="'+key+'" value="'+optionContent[index].option+'" />'+
					'<span class="test-radioInput optionAB"></span>'+optionContent[index].option+"."+'</lable>'+optionContent[index].answer+'</p>'
					$("#answerSubject").find("li").eq(0).find(".subjects").append(optionsHtml);
				}
			}
		},
		error:function(res){			
		}
	});
	function openaMark() {
		$('#answerMark').show();
		$("#answerbgMask").show();
		var height = document.body.clientHeight;
		$("#answerbgMask").css({"height":height})
    }
	function closeaMark(){
		$('#answerMark').hide();
		$("#answerbgMask").hide();
	}
	$("#submissionBtn").on("click",function(){
		openaMark()
	});
	$("#answerMark").find("button").on("click",function(){
    	if($(this).val() == 0){
    		closeaMark();
    	}else{
    		closeaMark();
    		window.location.href = '../portal/answerPage.html'
    	}
    });
    $("#goHome").on("click",function(){
		window.location.href = '../portal.html'
	})
});
