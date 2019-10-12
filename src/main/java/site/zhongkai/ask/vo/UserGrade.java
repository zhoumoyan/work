package site.zhongkai.ask.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = false)
public class UserGrade implements Serializable {
    private static final long serialVersionUID = 4523578262842343512L;
    private String openId;
    private Integer todayScore;
    private Integer validScore;
    private Integer historyScore;
}
