$(function () {
    $("#answerTickets").on("click", function () {
        window.location.href = '/ask/portal/tickets'
    })
    /*$(document).on('click', '.ticketList', function () {
        $("#answerBox").hide();
        $("#answerBox2").show();
    })*/
    /*$(".ticketList").on("click", function () {
        $("#answerBox").hide();
        $("#answerBox2").show();
    })*/
    $("#exhomePage").on("click", function () {
        $("#answerBox").show();
        $("#answerBox2").hide();
    })

    function openaMark() {
        $('#answerMark').show();
        $("#answerbgMask").show();
    }

    function closeaMark() {
        $('#answerMark').hide();
        $("#answerbgMask").hide();
    }

    $("#confirmBtn").on("click", function () {
        //兑换成功
//		$("#exresult").text('兑换成功');
//		$("#exresult").removeClass("exSureFalse");

        //兑换失败
        $("#exresult").text('可用积分不足，兑换失败');
        $("#exresult").addClass("exSureFalse");
        openaMark();

    })
    $("#determineBtn").on("click", function () {
        closeaMark();
        setInterval(function () {
            window.location.href = '/ask/portal/tickets'
        }, 100);
    })
    $("#goHome").on("click", function () {
        window.history.back(-1)
    })

})

$(document).ready(function () {
    showVoucherList();
});

function showVoucherList() {
    $("#voucher_list").empty();
    $.ajax({
        "url": "/ask/voucher/get_all",
        "type": "POST",
        "data": "openId=" + window.localStorage.getItem("openId"),
        "dataType": "json",
        "success": function (result) {
            var list = result.data.vouchers;
            for (var i = 0; i < list.length; i++) {
                var html =
                    '<ul class="ticketList" onclick="showVoucher(' + i + ')"> ' +
                    '<li>' +
                    '<p>￥<span class="answerMoney">#{money}</span></p>' +
                    '<p class="integralOne">#{consumeExplain}<span><input id="consume_score' + i + '" type="hidden" value="#{consumeScore}"></span></p> ' +
                    '</li>' +
                    '<li>' +
                    '<p class="ticketTilte1">编号：<span>#{id}</span></p>' +
                    '<p class="ticketTilte1">#{voucherExplain}</p>' +
                    '</li>' +
                    '<li class="effectiveDate">' +
                    '<p>有效时间：<span>#{validTimeFormat}</span></p>' +
                    '<p class="notUsed">#{state}</p>' +
                    '</li>' +
                    '</ul>';
                var state = (list[i].state === 0 ? "未使用" : "已使用");
                html = html.replace("#{money}", list[i].money);
                html = html.replace("#{consumeExplain}", list[i].consumeExplain);
                html = html.replace("#{consumeScore}", list[i].consumeScore);
                html = html.replace("#{id}", list[i].id);
                html = html.replace("#{voucherExplain}", list[i].voucherExplain);
                html = html.replace("#{validTimeFormat}", list[i].validTimeFormat);
                html = html.replace("#{state}", state);
                $("#voucher_list").append(html);
            }
            $("#valid_score1").html(result.data.userGrade);
            $("#valid_score2").html(result.data.userGrade);
        }
    })
}

function showVoucher(i) {
    var elementsByTagNameElement = document.getElementById("voucher_list").getElementsByTagName("ul")[i];
    $("#answerBox").hide();
    $("#answerBox2").show();
    var html = '<p class="answerTitle">确定使用'+$("#consume_score"+i).val()+'积分兑换</p>';
    $("#select_voucher").show().html(html).append(elementsByTagNameElement);

}