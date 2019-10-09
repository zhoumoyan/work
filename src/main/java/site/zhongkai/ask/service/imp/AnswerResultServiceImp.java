package site.zhongkai.ask.service.imp;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import site.zhongkai.ask.dao.AnswerResultDao;
import site.zhongkai.ask.entity.Answerresult;
import site.zhongkai.ask.service.AnswerResultService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("answerResultService")
public class AnswerResultServiceImp extends ServiceImpl<AnswerResultDao, Answerresult> implements AnswerResultService {
    @Override
    public List<Answerresult> getAnswerresultList(Answerresult answerresult) {
        return this.baseMapper.getAnswerresultList(answerresult);
    }
}
