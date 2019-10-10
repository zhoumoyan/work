package site.zhongkai.ask.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.zhongkai.ask.entity.Manager;
import site.zhongkai.ask.service.ManagerService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.R;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Log4j2
@RestController
public class ManagerController {

    @Resource
    private ManagerService managerService;

    //用户登录
    @PostMapping("/handle_login")
    public Manager handleLogin(Manager manager, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        Manager loginUser = managerService.selectOne(new EntityWrapper<Manager>()
                .eq("loginname", manager.getLoginname())
                .eq("password", manager.getPassword()));
        loginUser.setPassword(null).setSalt(null).setToken(null);
        return loginUser;
    }

    //分页查询
    @PostMapping("/get_manager_list")
    public R getManagerList(@RequestParam Map<String, Object> map, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu= managerService.getManagers(map);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object manager : pu.getList()) {
            ((Manager) manager).setFormatTime((simpleDateFormat).format(((Manager) manager).getCreateTime()));
        }
        return new R(0,"success",pu.getTotalCount(),pu.getList());
    }

    //添加
    @PostMapping("/add_manager")
    public String addManager(Manager manager, HttpServletResponse response){
        manager.setState("0").setCreateTime(new Date());
        System.err.println(manager);
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(managerService.insert(manager)){
            return "success";
        }else{
            return "error";
        }
    }

    //删除
    @PostMapping("/del_manager")
    public String delManager(Manager manager, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(managerService.deleteById(manager)){
            return "success";
        }else{
            return "error";
        }
    }

    //修改
    @PostMapping("/update_manager")
    public String updateManager(Manager manager, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(managerService.updateById(manager)){
            return "success";
        }else{
            return "error";
        }
    }
    //根据id查询信息
    @PostMapping("/get_manager_by_id")
    public Manager getManagerById(Manager manager, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        return managerService.selectOne(
                new EntityWrapper<Manager>().eq("id",manager.getId()));
    }

}
