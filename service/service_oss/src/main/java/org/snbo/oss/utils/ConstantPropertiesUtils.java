package org.snbo.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author sunbo
 * @create 2022-03-22-22:15
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {
    /**
     * 读取配置文件内容
     */
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;
    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    /**
     * 配置文件注入不能注入静态属性里
     * 定义公开常量
     */
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    /**
     * 实现接口后,当配置文件加载完就会执行下面的方法
     */
    @Override
    public void afterPropertiesSet(){
        END_POINT = endpoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
