$(function () {
    $("#answerExchanges").on("click", function () {
        window.location.href = '/ask/portal/exchange'
    })
    $("#goHome").on("click", function () {
        window.history.back(-1)
    })
})

$(document).ready(function () {
    getUserVoucher();
});

function getUserVoucher() {
    $("#user_voucher").empty();
    $.ajax({
        "url": "/ask/wx_user/get_voucher",
        "type": "POST",
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
                var state = (list[i].state === 0 ? "未使用" : "已使用");
                /*html = html.replace("#{money}", list[i].money.toFixed(2));*/
                html = html.replace("#{money}", list[i].money);
                html = html.replace("#{consumeExplain}", list[i].consumeExplain);
                html = html.replace("#{consumeScore}", list[i].consumeScore);
                html = html.replace("#{id}", list[i].id);
                html = html.replace("#{voucherExplain}", list[i].voucherExplain);
                html = html.replace("#{validTimeFormat}", list[i].validTimeFormat);
                html = html.replace("#{state}", state);
                $("#user_voucher").append(html);
            }
        }
    })
}
