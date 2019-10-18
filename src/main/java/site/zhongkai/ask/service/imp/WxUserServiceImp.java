package site.zhongkai.ask.service.imp;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import site.zhongkai.ask.config.Constant;
import site.zhongkai.ask.config.Response;
import site.zhongkai.ask.entity.InterfaceLog;
import site.zhongkai.ask.entity.UserVoucher;
import site.zhongkai.ask.mapper.IInterfaceLogMapper;
import site.zhongkai.ask.mapper.IUserVoucherMapper;
import site.zhongkai.ask.mapper.IWxUserMapper;
import site.zhongkai.ask.entity.WxUser;
import site.zhongkai.ask.service.IWxUserService;
import site.zhongkai.ask.utils.*;
import org.springframework.stereotype.Service;
import site.zhongkai.ask.vo.ChargingPile;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class WxUserServiceImp extends ServiceImpl<IWxUserMapper, WxUser> implements IWxUserService {

    private static final String CHARGING_PILE = PropertiesUtils.getValue(Constant.SYSTEM_PROPERTIES, "chargingPileAddr");
    private static final String VAR2_3DES = "439a362ffbbf460a60dc26543549a8ec";
    private static final String VAR3_3DES = "20191006";
    @Resource
    private IUserVoucherMapper userVoucherMapper;
    @Resource
    private IInterfaceLogMapper interfaceLogMapper;

    @Override
    public PageUtils getWxUserList(Map<String, Object> params) {
        Page<WxUser> page = new Query<WxUser>(params).getPage();
        List<WxUser> list = baseMapper.getWxUsers(page, params);
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public ResponseResult exchangeCharge(String openId) {
        List<UserVoucher> userVouchers = userVoucherMapper.selectList(new EntityWrapper<UserVoucher>().eq("open_id", openId).eq("state", 0));
        if (userVouchers.isEmpty()) return Response.getErrorResult(40007);
        int i = userVouchers.size();
        for (UserVoucher voucher : userVouchers) {
            ResponseResult result = execExchange(openId, voucher.getId());
            if (result.isSuccess()) --i;
        }
        if (i == 0) return Response.getSuccessResult(20001);
        if (i == userVouchers.size()) return Response.getErrorResult(40005);
        return Response.getErrorResult(40006);
    }

    private ResponseResult execExchange(String openId, String voucherId) {
        UserVoucher voucher = userVoucherMapper.selectById(voucherId);
        if (voucher == null) return Response.getErrorResult(50000);
        Date interfaceTime = new Date();
        String parameterPlaintext = JSON.toJSONString(new ChargingPile(voucherId, openId, voucher.getMoney(), 0, interfaceTime.getTime(), voucher.getValidTime().getTime(), "充电卷"));
        String parameterCiphertext = "volumeInfo=" + encrypt3DES(parameterPlaintext);
        ResponseResult<String> httpResult = HttpUtils.httpRequest(CHARGING_PILE, "POST", parameterCiphertext, StandardCharsets.UTF_8, StandardCharsets.UTF_8);
        InterfaceLog interfaceLog = new InterfaceLog(openId, voucher.getConsumeScore(), voucherId, voucher.getMoney(), voucher.getValidTime(), Constant.INTERFACE_CHARGING_PILE, parameterPlaintext, interfaceTime);
        if (httpResult.isSuccess()) {
            if ("ok".equals(httpResult.getData())) {
                interfaceLog.setOperateResult(0).setResultMessage(httpResult.getData());
                interfaceLogMapper.insert(interfaceLog);
                userVoucherMapper.updateById(new UserVoucher(voucherId,1, interfaceTime));
                return new ResponseResult(true, Constant.STATE_SUCCESS, Constant.OPERATE_SUCCESS);
            } else {
                interfaceLog.setOperateResult(2).setResultMessage(httpResult.getData());
            }
        } else {
            interfaceLog.setOperateResult(1).setResultMessage(httpResult.getData());
        }
        interfaceLogMapper.insert(interfaceLog);
        return Response.getErrorResult(40005);
    }

    // 3DES加密
    private static String encrypt3DES(String plainText) {
        String result;
        try {
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(VAR2_3DES.getBytes());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("desede");
            Key desKey = secretKeyFactory.generateSecret(deSedeKeySpec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(VAR3_3DES.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, desKey, ips);
            byte[] encryptData = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            result = Base64.encodeBase64URLSafeString(encryptData);
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
        return result;
    }

}
