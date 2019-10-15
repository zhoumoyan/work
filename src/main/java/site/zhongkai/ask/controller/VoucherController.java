package site.zhongkai.ask.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.zhongkai.ask.service.IVoucherService;

import javax.annotation.Resource;

@Log4j2
@Controller
@RequestMapping("/voucher")
public class VoucherController {

    @Resource
    private IVoucherService voucherService;

    // 获取代金卷
    @PostMapping("/get")
    @ResponseBody
    public String getVoucher(@RequestParam("openId") String openId, @RequestParam(required = false, value = "operateType") String operateType) {
        return JSON.toJSONString(voucherService.findVoucher(openId, operateType));
    }

    // 兑换代金卷
    @PostMapping("/confirm_exchange")
    @ResponseBody
    public String confirmVoucher(@RequestParam("openId") String openId, @RequestParam("voucherId") String voucherId){
        return JSON.toJSONString(voucherService.exchangeVoucher(openId, voucherId));
    }

}
