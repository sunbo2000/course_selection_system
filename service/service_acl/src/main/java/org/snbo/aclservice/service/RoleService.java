package org.snbo.aclservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.snbo.aclservice.bean.Role;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户获取角色数据
     *
     * @param userId 用户id
     * @return Map集合
     */
    Map<String, Object> findRoleByUserId(String userId);

    /**
     * 根据用户分配角色
     *
     * @param roleId 角色id
     * @param userId 用户id
     */
    void saveUserRoleRelationShip(String userId, String[] roleId);

    /**
     * 根据用户id获取角色
     *
     * @param id 用户id
     * @return Role类型的集合
     */
    List<Role> selectRoleByUserId(String id);

    /**
     * description: 根据角色名获取角色分页列表,若角色名为空则获取所有角色分页列表
     *
     * @param page  当前页
     * @param limit 分页条数
     * @param role  角色信息,用到的是角色名
     * @return {@link Map<String,Object>} 返回 Map 集合,包含角色集合和角色总数
     * @author sunbo
     * @date 2022/5/29 16:32
     */
    Map<String, Object> getPage(Long page, Long limit, Role role);
}
