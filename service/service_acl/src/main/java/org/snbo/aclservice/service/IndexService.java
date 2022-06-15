package org.snbo.aclservice.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author sunbo.2000
 */
public interface IndexService {

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username 用户名
     * @return Map集合
     */
    Map<String, Object> getUserInfo(String username);

    /**
     * 根据用户名获取动态菜单
     *
     * @param username 用户名
     * @return List集合
     */
    List<JSONObject> getMenu(String username);

}
