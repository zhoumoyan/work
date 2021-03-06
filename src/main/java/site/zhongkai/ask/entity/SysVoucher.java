package site.zhongkai.ask.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_sys_voucher")
public class SysVoucher implements Serializable {
    private static final long serialVersionUID = 5416764545565521439L;
    @TableId(type= IdType.ID_WORKER_STR)
    private String id;                  // 业务主键
    private BigDecimal money;           // 卡券金额
    @TableField(exist = false)
    private String moneyFormat;         // 格式化金额
    private String voucherExplain;      // 卡券说明
    private String consumeExplain;      // 消耗说明
    private Integer consumeScore;       // 消耗分数
    private Date validTime;             // 有效时间
    @TableField(exist = false)
    private String validTimeFormat;     // 格式化时间
    private Integer state;              // 卡券状态：0-正常，1-停用
    private String operateUserId;       // 操作人员ID
    private Date createTime;            // 创建时间
    @TableField(exist = false)
    private String createTimeFormat;    // 格式化时间
}
