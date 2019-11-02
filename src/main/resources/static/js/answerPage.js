$(function () {
    $("#ticket").on("click", function () {
        window.location.href = '/ask/portal/tickets'
    })
    $("#exchange").on("click", function () {
        window.location.href = '/ask/portal/exchange'
    })
    $("#goHome").on("click", function () {
        window.history.back(-1)
    });
    //获取答案
    var answerFraction = 0;
    var correctArray = JSON.parse(localStorage.getItem('correctArray'));
    for (var key in correctArray) {
        var ischecked = '';
        if (key < 9) {
            var $input = '';
            (correctArray[key].correct == correctArray[key].correctRight ?
                ($input = '<input class="test-check" disabled="disabled" checked="checked" type="checkbox" value="' + correctArray[key].correct + '">', answerFraction += 5) :
                $input = '<input class="test-check" disabled="disabled" type="checkbox" value="' + correctArray[key].correct + '">');
            var html = '<li><label class="test-label textIndent3">' + $input +
                '<span class="test-checkInput"></span>' + (parseInt(key) + 1) + '</lable></li>';
        } else {
            var $input = '';
            (correctArray[key].correct == correctArray[key].correctRight ?
                ($input = '<input class="test-check" disabled="disabled" checked="checked" type="checkbox" value="' + correctArray[key].correct + '">', answerFraction += 5) :
                $input = '<input class="test-check" disabled="disabled" type="checkbox" value="' + correctArray[key].correct + '">');
            var html = '<li><label class="test-label">' + $input +
                '<span class="test-checkInput"></span>' + (parseInt(key) + 1) + '</lable></li>';
        }
        $("#answerCheckbox ul").append(html);
        $("#answerFraction").text(answerFraction + ' 分');
    }
    submitResult(answerFraction);
});

function submitResult(answerFraction) {
    var data = "answerFraction=-1&openId=" + window.localStorage.getItem("openId");
    $.ajax({
        "url": "/ask/answer/get_result",
        "data": data,
        "type": "POST",
        "dataType": "json",
        "success": function (result) {
            if (result.success) {
                var grade = result.data;
                $("#today_score").html(grade.todayScore);
                $("#valid_score").html(grade.validScore);
                $("#history_score").html(grade.historyScore);
            } else {
                alert(result.message);
            }
        }
    });
}
