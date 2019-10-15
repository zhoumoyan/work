package site.zhongkai.ask.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import site.zhongkai.ask.entity.ExamInfo;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class AnswerIndex implements Serializable {
    private static final long serialVersionUID = -6055116744827397304L;
    private Integer todayScore;
    private List<ExamRandom> examInfos;

    public AnswerIndex() {
    }

    public AnswerIndex(Integer todayScore, List<ExamRandom> examInfos) {
        this.todayScore = todayScore;
        this.examInfos = examInfos;
    }
}
