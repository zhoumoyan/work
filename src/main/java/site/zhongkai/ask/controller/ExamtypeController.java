package site.zhongkai.ask.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import site.zhongkai.ask.entity.ExamType;
import site.zhongkai.ask.service.ExamtypeService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class ExamtypeController {
    @Autowired
    private ExamtypeService examtypeService;
    //新增题目类别
    @PostMapping("/addexamtype")
    public String addexamtype(ExamType examtype, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        examtype.setCreateTime(new Date());
        System.err.println(examtype);
        if(examtypeService.insert(examtype)){
            return "success";
        }else{
            return "error";
        }
    }
    //修改

    @PostMapping("/updateexamtype")
    public String updateexamtype(ExamType examtype, HttpServletResponse response){

        response.setHeader("Access-Control-Allow-Origin", "*");
        if(examtypeService.insertOrUpdate(examtype)){
            return "success";
        }else{
            return "error";
        }
    }

    //删除题目类别
    @PostMapping("/deleteexamtype")
    public String deleteexamtype(ExamType examtype, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(examtypeService.deleteById(examtype)){
            return "success";
        }else{
            return "error";
        }
    }
    //题目类别查询
    @PostMapping("/selectexamtype")
    public R selectexamtype(@RequestParam Map<String, Object> map, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu=examtypeService.getExamtypeList(map);
        for (Object examType : pu.getList()) {
            ((ExamType) examType).setFormatTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(((ExamType) examType).getCreateTime()));
        }
        return new R(0,"success",pu.getTotalCount(),pu.getList());
    }
    //查询
    @PostMapping("/getExamtypeInfoById")
    public ExamType getExamtypeInfoById(ExamType examtype, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        return examtypeService.selectOne(
                new EntityWrapper<ExamType>().eq("id",examtype.getId()));
    }

    //获取题目所有数据 用于绑定下拉列表
    @PostMapping("/getAllexamtype")
    public List<ExamType> getAllexamtype(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        return examtypeService.selectList(new EntityWrapper<ExamType>());
    }

}
