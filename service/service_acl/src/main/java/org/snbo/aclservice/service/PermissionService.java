package org.snbo.aclservice.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.snbo.aclservice.bean.Permission;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 获取全部菜单
     *
     * @return Permission组成的集合
     */
    List<Permission> queryAllMenu();

    /**
     * 根据角色获取菜单
     *
     * @param roleId 角色id
     * @return Permission组成的集合
     */
    List<Permission> selectAllMenu(String roleId);

    /**
     * 给角色分配权限
     *
     * @param roleId       角色id
     * @param permissionId 权限id
     */
    void saveRolePermissionRelationShip(String roleId, String[] permissionId);

    /**
     * 递归删除菜单
     *
     * @param id 菜单id
     */
    void removeChildById(String id);

    /**
     * 根据用户id获取用户菜单
     *
     * @param id 用户id
     * @return 菜单名组成的集合
     */
    List<String> selectPermissionValueByUserId(String id);

    /**
     * 根据用户id获取用户菜单权限
     *
     * @param id 用户id
     * @return Json类型的Permission集合
     */
    List<JSONObject> selectPermissionByUserId(String id);


    /**
     * 递归删除菜单
     *
     * @param id 菜单id
     */
    void removeChildByIdMogu(String id);

    /**
     * 给角色分配权限
     *
     * @param roleId 角色id
     * @param permissionId 权限id
     */
    void saveRolePermissionRelationShipMogu(String roleId, String[] permissionId);

    /**
     * 获取所有菜单
     *
     * @return Permission类型的集合
     */
    List<Permission> getAllMenuMogu();
}
