package site.zhongkai.ask.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_answer_log")
public class AnswerLog implements Serializable {
    private static final long serialVersionUID = -6821028435734831406L;
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    private String openId;
    private Integer score;
    @TableField(fill = FieldFill.INSERT)
    private Date operateTime;

    public AnswerLog() {
    }

    public AnswerLog(String openId, Integer score) {
        this.openId = openId;
        this.score = score;
    }

}
