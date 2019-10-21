$(function () {
    $("#answerTickets").on("click", function () {
        window.location.href = '/ask/portal/tickets'
    })
    /*$(document).on('click', '.ticketList', function () {
        $("#answerBox").hide();
        $("#answerBox2").show();
    })
    $(".ticketList").on("click", function () {
        $("#answerBox").hide();
        $("#answerBox2").show();
    })
    $("#exhomePage").on("click", function () {
        $("#answerBox").show();
        $("#answerBox2").hide();
    })
    function openaMark() {
        $('#answerMark').show();
        $("#answerbgMask").show();
    }*/

    function closeaMark() {
        $('#answerMark').hide();
        $("#answerbgMask").hide();
    }

    /*$("#confirmBtn").on("click", function () {
        //兑换成功
//		$("#exresult").text('兑换成功');
//		$("#exresult").removeClass("exSureFalse");

        //兑换失败
        $("#exresult").text('可用积分不足，兑换失败');
        $("#exresult").addClass("exSureFalse");
        openaMark();

    })
    */
    $("#determineBtn").on("click", function () {
        closeaMark();
        /*setInterval(function () {
            window.location.href = '/ask/portal/tickets'
        }, 100);*/
        if ($("#determineBtn").val() === 0) {
            window.location.href = '/ask/portal/tickets';
        }
    })

    $("#goHome").on("click", function () {
        /*window.history.back(-1)*/
        window.location.href = '/ask/portal/index';
    })

})

$(document).ready(function () {
    showVoucherList();
});
function showVoucherList() {
    $("#voucher_list").empty();
    $.ajax({
        "url": "/ask/wx_user/get_voucher",
        "type": "POST",
        "data": "openId=" + window.localStorage.getItem("openId"),
        "dataType": "json",
        "success": function (result) {
            var list = result.data.sysVouchers;
            for (var i = 0; i < list.length; i++) {
                var html =
                    '<ul class="ticketList" onclick="showSelectVoucher(' + i + ')"> ' +
                    '<li>' +
                    '<p>￥<span class="answerMoney">#{money}</span></p>' +
                    '<p class="integralOne">#{consumeExplain}<span><input id="consume_score' + i + '" type="hidden" value="#{consumeScore}"></span></p> ' +
                    '</li>' +
                    '<li>' +
                    '<p class="ticketTilte1">编号：<span id="voucher_id' + i + '">#{id}</span></p>' +
                    '<p class="ticketTilte1">#{voucherExplain}</p>' +
                    '</li>' +
                    '<li class="effectiveDate">' +
                    '<p>有效时间：<span>#{validTimeFormat}</span></p>' +
                    '<p class="notUsed">#{state}</p>' +
                    '</li>' +
                    '</ul>';
                var state = (list[i].consumeScore <= result.data.userGrade ? "可以兑换" : "积分不足");
                /*html = html.replace("#{money}", list[i].money.toFixed(2));*/
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

function showSelectVoucher(i) {
    var elements = document.getElementById("voucher_list").getElementsByTagName("ul")[i];
    $("#answerBox").hide();
    $("#answerBox2").show();
    var html1 = '<p class="answerTitle">确定使用' + $("#consume_score" + i).val() + '积分兑换</p>';
    $("#select_voucher").show().html(html1).append(elements);
    document.getElementById("select_voucher").getElementsByTagName("ul")[0].removeAttribute("onclick");
    var html2 = '<button type="button" class="exchange_Ticket" id="confirmBtn" onclick="confirmExchange(' + i + ')">确定</button>';
    $("#confirm_div").html(html2);
}

function confirmExchange(i) {
    if (parseInt($("#valid_score2").html()) < parseInt($("#consume_score" + i).val())) {
        $("#exresult").text('可用积分不足，兑换失败').addClass("exSureFalse");
        openaMark();
        return;
    }
    var data = "openId=" + window.localStorage.getItem("openId") + "&voucherId=" + $("#voucher_id" + i).html();
    $.ajax({
        "url": "/ask/wx_user/confirm_exchange",
        "type": "POST",
        "data": data,
        "dataType": "json",
        "success": function (result) {
            if (result.success) {
                $("#exresult").text('兑换成功').removeClass("exSureFalse");
                $("#valid_score1").html($("#valid_score1").html() - $("#consume_score" + i).val());
                $("#valid_score2").html($("#valid_score2").html() - $("#consume_score" + i).val());
            } else {
                $("#exresult").text(result.message).addClass("exSureFalse");
                $("#determineBtn").val(1);
            }
            openaMark();
        }
    })
}

function openaMark() {
    $('#answerMark').show();
    $("#answerbgMask").show();
}