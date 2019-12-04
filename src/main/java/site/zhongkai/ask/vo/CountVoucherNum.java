package site.zhongkai.ask.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CountVoucherNum implements Serializable {

    private static final long serialVersionUID = 3834821705186415832L;
    private String userId;
    private String nickName;
    private String openId;
    private Integer num;
    private Integer notUsedNum;
    private Integer usedNum;
    private Integer transferNum;
    public CountVoucherNum() {
    }

    public CountVoucherNum(String userId, String nickName, String openId, Integer num, Integer notUsedNum, Integer usedNum, Integer transferNum) {
        this.userId = userId;
        this.nickName = nickName;
        this.openId = openId;
        this.num = num;
        this.notUsedNum = notUsedNum;
        this.usedNum = usedNum;
        this.transferNum = transferNum;
    }
}
