package site.zhongkai.ask.service.imp;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import site.zhongkai.ask.config.Response;
import site.zhongkai.ask.entity.UserVoucher;
import site.zhongkai.ask.mapper.IManagerMapper;
import site.zhongkai.ask.entity.Manager;
import site.zhongkai.ask.mapper.IUserVoucherMapper;
import site.zhongkai.ask.mapper.IWxUserMapper;
import site.zhongkai.ask.service.IManagerService;
import site.zhongkai.ask.utils.PageUtils;
import site.zhongkai.ask.utils.Query;
import org.springframework.stereotype.Service;
import site.zhongkai.ask.utils.ResponseResult;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ManagerServiceImp extends ServiceImpl<IManagerMapper, Manager> implements IManagerService {

    @Resource
    private IUserVoucherMapper userVoucherMapper;
    @Resource
    private IManagerMapper managerMapper;
    @Override
    public PageUtils getManagers(Map<String, Object> params) {
        Page<Manager> page = new Query<Manager>(params).getPage();
        List<Manager> list = baseMapper.getManagers(page, params);
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public ResponseResult setVoucherState(String voucherId, Date useTime) {
        UserVoucher voucher = userVoucherMapper.selectById(voucherId);
        if (voucher == null) return Response.getErrorResult(40009);
        if (voucher.getUseTime() == null && voucher.getState() != 1) {
            userVoucherMapper.updateById(new UserVoucher().setId(voucherId).setUseTime(useTime).setState(1));
        } else {
            return Response.getErrorResult(40010);
        }
        return Response.getSuccessResult(20001);
    }

    @Override
    public boolean changeAvatar(String uid, String avatar) {
        // 根据参数uid查询用户数据
        Manager manager = managerMapper.selectById(uid);
        // 判断查询结果是否为null -> result == null
        if (manager == null) {

            return false;
        }
        manager.setAvatar(avatar);
        // 执行更新头像，并获取返回的受影响的行数
        Integer rows = managerMapper.updateById(manager);
        // 判断受影响的行数是否不为1，如果是，则抛出异常
        if (rows != 1) {
            return false;
        }
        return true;
    }
}
