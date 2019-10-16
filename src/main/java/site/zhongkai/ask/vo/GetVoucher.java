package site.zhongkai.ask.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import site.zhongkai.ask.entity.SysVoucher;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class GetVoucher implements Serializable {
    private static final long serialVersionUID = -3926729164448230324L;
    private Integer userGrade;              // 用户积分
    private List<SysVoucher> sysVouchers;   // 卡券列表

    public GetVoucher() {
    }

    public GetVoucher(Integer userGrade, List<SysVoucher> sysVouchers) {
        this.userGrade = userGrade;
        this.sysVouchers = sysVouchers;
    }
}
