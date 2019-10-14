package site.zhongkai.ask.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import site.zhongkai.ask.entity.ExamInfo;

import java.util.List;
import java.util.Map;

public interface IExamInfoMapper extends BaseMapper<ExamInfo> {
    //多表联查方式
    List<ExamInfo> getExamInfoList(Page<ExamInfo> page, Map<String, Object> params);
}
