function gotoAnswerPage() {
    var timestamp = (new Date()).getTime();
    $.ajax({
        "url": "/ask/answer/get_result?timestamp=" + timestamp,
        "data": "openId=" + window.localStorage.getItem("openId"),
        "type": "POST",
        "cache": "false",
        "dataType": "json",
        "success": function (result) {
            if (result.success) {
                window.location.href = '/ask/portal/subject_page';
            } else {
                alert(result.message);
            }
        }
    });
}