package site.zhongkai.ask.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.zhongkai.ask.config.Constant;
import site.zhongkai.ask.entity.WxUser;
import site.zhongkai.ask.mapper.IWxUserMapper;
import site.zhongkai.ask.service.IWxUserService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.R;
import site.zhongkai.ask.utils.ResponseResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/wx_user")
public class WxUserController {

    @Resource
    private IWxUserService wxUserService;
    @Resource
    private IWxUserMapper wxUserMapper;

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

    //分页查询
    @PostMapping("/get_all")
    @ResponseBody
    public R getAllWxUser(@RequestParam Map<String, Object> map, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu = wxUserService.getWxUserList(map);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object wxUser : pu.getList()) {
            System.err.println(wxUser);
            ((WxUser) wxUser).setCreateTimeFormat((simpleDateFormat).format(((WxUser) wxUser).getCreateTime()));
            if (((WxUser) wxUser).getActiveTime() != null)
                ((WxUser) wxUser).setActiveTimeFormat((simpleDateFormat).format(((WxUser) wxUser).getActiveTime()));
        }
        return new R(0, "success", pu.getTotalCount(), pu.getList());
    }

}
