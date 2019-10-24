package site.zhongkai.ask.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import site.zhongkai.ask.entity.ExamType;
import site.zhongkai.ask.service.IExamTypeService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.ResponseLayui;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/exam_type")
public class ExamTypeController {

    @Resource
    private IExamTypeService examTypeService;

    //新增题目类别
    @PostMapping("/add")
    public String addexamtype(ExamType examtype, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (examTypeService.insert(examtype)) {
            return "success";
        } else {
            return "error";
        }
    }

    //修改
    @PostMapping("/update")
    public String updateexamtype(ExamType examtype, HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin", "*");
        if (examTypeService.insertOrUpdate(examtype)) {
            return "success";
        } else {
            return "error";
        }
    }

    //删除题目类别
    @PostMapping("/delete")
    public String deleteexamtype(ExamType examtype, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (examTypeService.deleteById(examtype)) {
            return "success";
        } else {
            return "error";
        }
    }

    //题目类别查询
    @PostMapping("/query")
    public ResponseLayui queryExamType(@RequestParam Map<String, Object> map, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PageUtils pu = examTypeService.getExamtypeList(map);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object examType : pu.getList()) {
            ((ExamType) examType).setFormatTime((simpleDateFormat).format(((ExamType) examType).getCreateTime()));
        }
        return new ResponseLayui(0, "success", pu.getTotalCount(), pu.getList());
    }

    //查询
    @PostMapping("/get_by_id")
    public ExamType getExamtypeById(ExamType examtype, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return examTypeService.selectOne(
                new EntityWrapper<ExamType>().eq("id", examtype.getId()));
    }

    //获取题目所有数据，用于绑定下拉列表
    @PostMapping("/get_all")
    public List<ExamType> getAllExamtype(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return examTypeService.selectList(new EntityWrapper<>());
    }

}
