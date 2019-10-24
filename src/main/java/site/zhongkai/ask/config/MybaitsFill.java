package site.zhongkai.ask.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MybaitsFill extends MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter("operateTime") && metaObject.getValue("operateTime") == null) setFieldValByName("operateTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    }

}
