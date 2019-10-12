package site.zhongkai.ask.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class ExamRandom implements Serializable {
    private static final long serialVersionUID = 6163910076824771484L;
    private String id;
    private String title;
    private String correct;
    private List<ExamOption> options;

    public ExamRandom() {
    }

    public ExamRandom(String id, String title, String correct, List<ExamOption> options) {
        this.id = id;
        this.title = title;
        this.correct = correct;
        this.options = options;
    }
}
