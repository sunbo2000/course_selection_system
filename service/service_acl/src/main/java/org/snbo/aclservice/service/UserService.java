package org.snbo.aclservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.snbo.aclservice.bean.User;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface UserService extends IService<User> {

    /**
     * 从数据库中取出用户信息
     *
     * @param username 用户名
     * @return 返回具体用户
     */
    User selectByUsername(String username);

    /**
     * description: 根据查询条件分页查询用户
     *
     * @param page 当前页
     * @param limit 每页条数
     * @param userQueryVo 用户查询条件对象
     * @return {@link Map<String,Object>}
     * @author sunbo
     * @date 2022/5/29 17:14
     */
    Map<String, Object> getPage(Long page, Long limit, User userQueryVo);
}
