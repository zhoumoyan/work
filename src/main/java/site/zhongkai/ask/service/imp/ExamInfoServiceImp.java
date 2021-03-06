package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import site.zhongkai.ask.mapper.IExamInfoMapper;
import site.zhongkai.ask.entity.ExamInfo;
import site.zhongkai.ask.service.IExamInfoService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExamInfoServiceImp extends ServiceImpl<IExamInfoMapper, ExamInfo> implements IExamInfoService {
    @Override
    public PageUtils getExamInfoList(Map<String, Object> params) {
        Page<ExamInfo> page=new Query<ExamInfo>(params).getPage();
        List<ExamInfo> list=baseMapper.getExamInfoList(page,params);
        page.setRecords(list);
        return new PageUtils(page);
    }
}
