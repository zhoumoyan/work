$(function(){
	var timestamp = (new Date()).getTime();
	var correctArrayo = [];
	//获取题目
	$.ajax({
		type:"get",
		"cache": "false",
		url:"/ask/answer/get_index?timestamp=" + timestamp,
		data: "openId=" + window.localStorage.getItem("openId"),
		async:true,
		success:function(json){
			res = json.examInfos;
			$("#answerSubject").find("li").eq(0).find(".subjects").empty();
			/*for(var key in res){
				correctArrayo.push(res[key].correct);
				var multipleHtml = (res[key].correct.length > 1? multipleHtml='<span class="multiple">[多选题]</span>':multipleHtml='<span class="multiple">[单选题]</span>');
				var html = '<p class="subjects_one">'+(parseInt(key)+1)+'、'+res[key].title+multipleHtml+'</p>';
				$("#answerSubject").find("li").eq(0).find(".subjects").append(html);
				var optionContent  = res[key].options;
				var tmdan =$('<div class="tmdan"></div>');
				for (var index in optionContent) {
					var optionsHtml = '<p><label class="test-label"><input type="checkbox" class="test-radio" name="'+key+'" value="'+optionContent[index].option+'" />'+
					'<span class="test-radioInput optionAB"></span>'+optionContent[index].option+"."+'</lable>'+optionContent[index].answer+'</p>';
					tmdan.append(optionsHtml);
					$("#answerSubject").find("li").eq(0).find(".subjects").append(tmdan);
				}
			}*/
			for(var key in res){
				correctArrayo.push(res[key].correct);
				var multipleHtml = (res[key].correct.length > 1? multipleHtml='<span class="multiple">[多选题]</span>':multipleHtml='<span class="multiple">[单选题]</span>');
				var html = '<p class="subjects_one">'+(parseInt(key)+1)+'、'+res[key].title+multipleHtml+'</p>';
				$("#answerSubject").find("li").eq(0).find(".subjects").append(html);
				var optionContent  = res[key].options;
				var tmdan =$('<div class="tmdan"></div>');
				for (var index in optionContent) {
					var inputType = (res[key].correct.length > 1?
						inputType='<input type="checkbox" class="test-check" name="'+key+'" value="'+optionContent[index].option+'" /><span class="test-checkInput optionAB"></span>':
						inputType='<input type="radio" class="test-radio" name="'+key+'" value="'+optionContent[index].option+'" /><span class="test-radioInput optionAB"></span>');
					var optionsHtml = '<p><label class="test-label">'+ inputType + optionContent[index].option+"."+'</lable>'+optionContent[index].answer+'</p>';
					tmdan.append(optionsHtml);
					$("#answerSubject").find("li").eq(0).find(".subjects").append(tmdan);
				}
			}
			$("#today_score").html(json.todayScore);
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
	function openaMarkT() {
		$('#answerMarkT').show();
		$("#answerbgMask").show();
		var height = document.body.clientHeight;
		$("#answerbgMask").css({"height":height})
    }
	function closeaMark(){
		$('#answerMark').hide();
		$('#answerMarkT').hide();
		$("#answerbgMask").hide();
	}
	var dataArry = [];
	$("#submissionBtn").on("click",function(){
		dataArry.length = 0;
		$('#answerSubject .tmdan').each(function(index,value){
			if($(this).find("input:checked").length == 0){
				//题目尚未答完
				openaMarkT();
				return false;
			} else {
				//题目已经答完
				var obj = {id:index,correct:'',correctRight:correctArrayo[index]};
				var thiso = $(this);
				thiso.find("input").each(function(){
					var thist = $(this);
					if(thist.prop('checked')){
						obj.correct += thist.val();
					}
				})
				dataArry.push(obj);
				openaMark();
			}
		});
	})
	$("#answerMark").find("button").on("click",function(){
    	if($(this).val() == 0){
    		closeaMark();
    	} else {
    		closeaMark();
    		localStorage.setItem('correctArray',JSON.stringify(dataArry))
			var answerFraction = calculated(dataArry);
			submitScore(answerFraction);  
    	}
    });
    $("#answerMarkT").find("button").on("click",function(){
    		closeaMark();
    });
    $("#goHome").on("click",function(){
		window.location.href = '/ask/portal/index';
	})
})

function calculated(correctArray) {
	var answerFraction = 0;
	for (var key in correctArray) {
		if (correctArray[key].correct === correctArray[key].correctRight) answerFraction += 5;
	}
	return answerFraction;
}


function submitScore(answerFraction) {
	var timestamp = (new Date()).getTime();
	var data = "openId=" + window.localStorage.getItem("openId") + "&answerFraction=" + answerFraction;
	$.ajax({
		"url": "/ask/answer/get_result?timestamp=" + timestamp,
		"data": data,
		"type": "POST",
		"cache": "false",
		"dataType": "json",
		"success": function (result) {
			if (result.success) {
				window.location.replace("/ask/portal/answer_page");
			} else {
				alert(result.message);
			}
		}
	});
}
