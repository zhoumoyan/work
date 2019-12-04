package site.zhongkai.ask.utils;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ResponseLayui {
    private Integer code;
    private String msg;
    private Long count;
    private List data;

    public ResponseLayui() {

    }

    public ResponseLayui(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseLayui(Integer code, String msg, Long count, List data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

}
