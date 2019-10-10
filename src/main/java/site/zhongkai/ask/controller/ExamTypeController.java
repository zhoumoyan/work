package site.zhongkai.ask.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import site.zhongkai.ask.entity.ExamType;
import site.zhongkai.ask.service.ExamTypeService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
public class ExamTypeController {

    @Resource
    private ExamTypeService examTypeService;

    //新增题目类别
    @PostMapping("/add_exam_type")
    public String addexamtype(ExamType examtype, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        examtype.setCreateTime(new Date());
        if(examTypeService.insert(examtype)){
            return "success";
        }else{
            return "error";
        }
    }
    //修改

    @PostMapping("/update_exam_type")
    public String updateexamtype(ExamType examtype, HttpServletResponse response){

        response.setHeader("Access-Control-Allow-Origin", "*");
        if(examTypeService.insertOrUpdate(examtype)){
            return "success";
        }else{
            return "error";
        }
    }

    //删除题目类别
    @PostMapping("/delete_exam_type")
    public String deleteexamtype(ExamType examtype, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(examTypeService.deleteById(examtype)){
            return "success";
        }else{
            return "error";
        }
    }
    //题目类别查询
    @PostMapping("/select_exam_type")
    public R selectexamtype(@RequestParam Map<String, Object> map, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu= examTypeService.getExamtypeList(map);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object examType : pu.getList()) {
            ((ExamType) examType).setFormatTime((simpleDateFormat).format(((ExamType) examType).getCreateTime()));
        }
        return new R(0,"success",pu.getTotalCount(),pu.getList());
    }
    //查询
    @PostMapping("/get_exam_type_info_by_id")
    public ExamType getExamtypeInfoById(ExamType examtype, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        return examTypeService.selectOne(
                new EntityWrapper<ExamType>().eq("id",examtype.getId()));
    }

    //获取题目所有数据 用于绑定下拉列表
    @PostMapping("/get_all_exam_type")
    public List<ExamType> getAllexamtype(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        return examTypeService.selectList(new EntityWrapper<>());
    }

}
