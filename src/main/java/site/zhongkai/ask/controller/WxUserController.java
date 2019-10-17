package site.zhongkai.ask.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.zhongkai.ask.entity.WxUser;
import site.zhongkai.ask.service.ISysVoucherService;
import site.zhongkai.ask.service.IWxUserService;

import javax.annotation.Resource;
import java.util.Date;

@Log4j2
@Controller
@RequestMapping("/wx_user")
public class WxUserController {

    @Resource
    private IWxUserService wxUserService;
    @Resource
    private ISysVoucherService sysVoucherService;

    //用户登录
    @GetMapping("/login")
    public ModelAndView handleLogin(@RequestParam("openId") String openId, @RequestParam("nickName") String nickName, ModelAndView modelAndView) {
        WxUser wxUser = new WxUser(openId, nickName);
        modelAndView.addObject("openId", wxUser.getOpenId());
        WxUser user = wxUserService.selectOne(new EntityWrapper<WxUser>().eq("open_id", wxUser.getOpenId()));
        Date operTime = new Date();
        if (user == null) {
            wxUser.setGrade(0).setCreateTime(operTime).setActiveTime(operTime);
            wxUserService.insert(wxUser);
        } else {
            user.setNickName(nickName).setActiveTime(operTime);
            wxUserService.updateById(user);
        }
        modelAndView.setViewName("index");
        return modelAndView;
    }

    // 获取卡券
    @PostMapping("/get_voucher")
    @ResponseBody
    public String getVoucher(@RequestParam("openId") String openId, @RequestParam(required = false, value = "operateType") String operateType) {
        return JSON.toJSONString(sysVoucherService.findVoucher(openId, operateType));
    }

    // 兑换卡券
    @PostMapping("/confirm_exchange")
    @ResponseBody
    public String confirmVoucher(@RequestParam("openId") String openId, @RequestParam("voucherId") String voucherId){
        return JSON.toJSONString(sysVoucherService.exchangeVoucher(openId, voucherId));
    }

}
