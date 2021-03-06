$(function () {
    $("#answerExchanges").on("click", function () {
        /*window.location.href = ''*/
        confirmCharge();
    })
    $("#goHome").on("click", function () {
        window.history.back(-1)
    })
})

$(document).ready(function () {
    getUserVoucher();
});

function confirmCharge() {
    var timestamp = (new Date()).getTime();
    $.ajax({
        "url": "/ask/wx_user/confirm_charge?timestamp=" + timestamp,
        "type": "POST",
        "cache": "false",
        "data": "openId=" + window.localStorage.getItem("openId"),
        "dataType": "json",
        "success": function (result) {
            window.location.href = 'http://kyunai.com/charging/mobilepage/index?code=1';
            /*if (result.success){
                window.location.href = 'http://kyunai.com/charging/mobilepage/index?code=1';
            } else if (result.state === 40006){
                alert(result.message);
                window.location.href = 'http://kyunai.com/charging/mobilepage/index?code=1';
            } else {
                alert(result.message);
                window.location.reload();
            }*/
        }
    })
}

function getUserVoucher() {
    $("#user_voucher").empty();
    var timestamp = (new Date()).getTime();
    $.ajax({
        "url": "/ask/wx_user/get_voucher?timestamp=" + timestamp,
        "type": "POST",
        "cache": "false",
        "data": "operateType=iVoucher&openId=" + window.localStorage.getItem("openId"),
        "dataType": "json",
        "success": function (result) {
            var list = result.data;
            if (list.length === 0){
                $("#voucher_title").html("<div class='textCenter'>您还没有兑换过卡券，请先答题后用积分兑换。</div>");
                var btn = '<div class="textCenter"><input class="toanswerBtn" type="button" name="" onclick="gotoAnswerPage()" value="去答题"/></div>';
                $("#tickets_btn").html(btn);
                return;
            }
            for (var i = 0; i < list.length; i++) {
                var html =
                    '<ul class="ticketList">' +
                    '<li>' +
                    '<p>￥<span class="answerMoney">#{money}</span></p>' +
                    '<p class="integralOne">#{consumeExplain}</p>' +
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
                var state = (list[i].state === 1 ? "已充电" : "未充电");
                /*html = html.replace("#{money}", list[i].money.toFixed(2));*/
                html = html.replace("#{money}", list[i].money);
                html = html.replace("#{consumeExplain}", list[i].consumeExplain);
                html = html.replace("#{id}", list[i].id);
                html = html.replace("#{voucherExplain}", list[i].voucherExplain);
                html = html.replace("#{validTimeFormat}", list[i].validTimeFormat);
                html = html.replace("#{state}", state);
                $("#user_voucher").append(html);
            }
        }
    })
}
