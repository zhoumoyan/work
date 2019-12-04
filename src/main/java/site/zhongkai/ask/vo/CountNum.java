package site.zhongkai.ask.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CountNum implements Serializable {

    private static final long serialVersionUID = 3834821705186415832L;
    private Integer state;
    private Integer num;

    public CountNum() {
    }

    public CountNum(Integer state, Integer num) {
        this.state = state;
        this.num = num;
    }
}
