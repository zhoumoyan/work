package site.zhongkai.ask.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class User {
    private Integer id;         // 自增长ID
    private String type_name;   // 类别名称
    private Date create_time;   // 创建时间
}
