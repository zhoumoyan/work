package site.zhongkai.ask.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import site.zhongkai.ask.entity.Manager;
import site.zhongkai.ask.entity.Sysuser;
import site.zhongkai.ask.service.SysuserService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@RestController
public class SysuserController {
    @Autowired
    private SysuserService sysuserService;
    //用户登录
    @PostMapping("/sysLogin")
    public Manager sysLogin(Manager manager, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        Manager loginUser = sysuserService.selectOne(new EntityWrapper<Manager>()
                .eq("loginname", manager.getLoginname())
                .eq("password", manager.getPassword()));
        loginUser.setPassword(null).setSalt(null).setToken(null);
        return loginUser;
    }

    //分页查询
    @PostMapping("/getSysuserList")
    public R getSysuserList(@RequestParam Map<String, Object> map, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu=sysuserService.getSysuserList(map);
        return new R(0,"success",pu.getTotalCount(),pu.getList());
    }

    //添加
    @PostMapping("/addSysuser")
    public String addSysuser(Manager sysuser, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(sysuserService.insert(sysuser)){
            return "success";
        }else{
            return "error";
        }
    }

    //删除
    @PostMapping("/delSysuser")
    public String delSysuser(Sysuser sysuser, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(sysuserService.deleteById(sysuser)){
            return "success";
        }else{
            return "error";
        }
    }

    //修改
    @PostMapping("/updateSysuser")
    public String updateSysuser(Manager sysuser, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(sysuserService.updateById(sysuser)){
            return "success";
        }else{
            return "error";
        }
    }
    //根据id查询信息
    @PostMapping("/getSysuserById")
    public Manager getSysuserById(Manager sysuser, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        return sysuserService.selectOne(
                new EntityWrapper<Manager>().eq("id",sysuser.getId()));
    }

}
