package site.zhongkai.ask.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = false)
public class GetQueryNum implements Serializable {
    private static final long serialVersionUID = 4523558262342343511L;
    private String userId;
    private String nickName;
    private List<CountAnswerNum> countAnswerNums;


    public GetQueryNum() {
    }

    public GetQueryNum(String userId, String nickName, List<CountAnswerNum> countAnswerNums) {
        this.userId = userId;
        this.nickName = nickName;
        this.countAnswerNums = countAnswerNums;
    }
}
