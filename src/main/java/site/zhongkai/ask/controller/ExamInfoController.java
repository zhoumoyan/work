package site.zhongkai.ask.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.zhongkai.ask.entity.ExamInfo;
import site.zhongkai.ask.service.IExamInfoService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.R;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/exam_info")
public class ExamInfoController {

    @Resource
    private IExamInfoService examInfoService;

    //分页查询
    @PostMapping("/get_all")
    public R getAllExamInfo(@RequestParam Map<String, Object> map, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu = examInfoService.getExamInfoList(map);
        return new R(0, "success", pu.getTotalCount(), pu.getList());
    }

    //添加
    @PostMapping("/add")
    public String addExamInfo(ExamInfo examinfo, HttpServletResponse response) {
        examinfo.setCreateTime(new Date());
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (examInfoService.insert(examinfo)) {
            return "success";
        } else {
            return "error";
        }
    }

    //删除
    @PostMapping("/delete")
    public String delExamInfo(ExamInfo examinfo, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (examInfoService.deleteById(examinfo)) {
            return "success";
        } else {
            return "error";
        }
    }

    //修改
    @PostMapping("/update")
    public String updateExamInfo(ExamInfo examinfo, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (examInfoService.updateById(examinfo)) {
            return "success";
        } else {
            return "error";
        }
    }

    //根据id查询题目信息
    @PostMapping("/get_by_id")
    public ExamInfo getExamInfoById(ExamInfo examinfo, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return examInfoService.selectOne(new EntityWrapper<ExamInfo>().eq("id", examinfo.getId()));
    }

}
