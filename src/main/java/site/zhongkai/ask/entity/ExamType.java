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
@TableName("t_exam_type")
public class ExamType implements Serializable {
	private static final long serialVersionUID = -4244681839801440754L;
	@TableId(type= IdType.ID_WORKER_STR)
	private String id;			// 自增长ID
	private String typeName;	// 类别名称
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;	// 创建时间
	@TableField(exist = false)
	private String formatTime;	// 时间格式
}
