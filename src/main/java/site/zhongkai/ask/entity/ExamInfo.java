package site.zhongkai.ask.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_exam_info")
public class ExamInfo implements Serializable {
	private static final long serialVersionUID = -5846314257914938890L;
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;				//业务主键
	private String examTypeId;		//类别主键
	@TableField(exist = false)
	private String examTypeName;	//类别名称
	private String examName;		//题目名称
	private String optionA;			//选项A
	private String optionB;			//选项B
	private String optionC;			//选项C
	private String optionD;			//选项D
	private String optionE;			//选项E
	private String correctAnswer;	//答案
	private String examExplain;		//解释说明
	private Date createTime;		//创建时间
}
