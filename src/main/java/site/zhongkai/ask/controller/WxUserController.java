package site.zhongkai.ask.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.zhongkai.ask.entity.WxUser;
import site.zhongkai.ask.service.WxUserService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.R;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Map;

@Log4j2
@RestController
public class WxUserController {

    @Resource
    private WxUserService wxUserService;

    //分页查询
    @PostMapping("/get_wx_user_list")
    public R getStudentList(@RequestParam Map<String, Object> map, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu= wxUserService.getStudentList(map);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object wxUser : pu.getList()) {
            ((WxUser) wxUser).setCreateTimeFormat((simpleDateFormat).format(((WxUser) wxUser).getCreateTime()));
            if (((WxUser) wxUser).getActiveTime() != null) ((WxUser) wxUser).setActiveTimeFormat((simpleDateFormat).format(((WxUser) wxUser).getActiveTime()));
        }
        return new R(0,"success",pu.getTotalCount(),pu.getList());
    }

}
