function gotoAnswerPage() {
    $.ajax({
        "url": "/ask/answer/get_result",
        "data": "openId=" + window.localStorage.getItem("openId"),
        "type": "POST",
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