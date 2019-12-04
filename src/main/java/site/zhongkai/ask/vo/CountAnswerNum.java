package site.zhongkai.ask.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CountAnswerNum  implements Serializable {

    private static final long serialVersionUID = 3834821705186415832L;
    private String userId;
    private String nickName;
    private String openId;
    private Integer num;
    private Integer state;

    public CountAnswerNum() {
    }

    public CountAnswerNum(String userId, String nickName, String openId, Integer num) {
        this.userId = userId;
        this.nickName = nickName;
        this.openId = openId;
        this.num = num;
    }

}
