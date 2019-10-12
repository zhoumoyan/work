package site.zhongkai.ask.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ExamOption implements Serializable {
    private static final long serialVersionUID = 7056074911250764735L;
    private String option;
    private String answer;

    public ExamOption() {
    }

    public ExamOption(String option, String answer) {
        this.option = option;
        this.answer = answer;
    }
}
