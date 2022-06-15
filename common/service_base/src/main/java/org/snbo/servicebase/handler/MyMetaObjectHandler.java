package org.snbo.servicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * MybatisPlus 自动填充插件
 *
 * @author sunbo
 * @create 2022-03-18-11:42
 */

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //填bean类的属性名
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }


    /**
     * 更新时填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
