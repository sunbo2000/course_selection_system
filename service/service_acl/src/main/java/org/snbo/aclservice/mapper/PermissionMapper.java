package org.snbo.aclservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.snbo.aclservice.bean.Permission;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 通过用户id获取其具有的权限
     *
     * @param id 用户id
     * @return 字符串类型的权限名组成的集合
     */
    List<String> selectPermissionValueByUserId(String id);

    /**
     * 获取所有权限,当用户是管理员时调用
     *
     * @return 字符串类型的权限名组成的集合
     */
    List<String> selectAllPermissionValue();

    /**
     * 根据用户id获取所有权限
     *
     * @param userId 用户id
     * @return 返回Permission类型的集合
     */
    List<Permission> selectPermissionByUserId(String userId);
}
