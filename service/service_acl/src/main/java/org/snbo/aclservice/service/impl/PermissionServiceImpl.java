package org.snbo.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.snbo.aclservice.bean.Permission;
import org.snbo.aclservice.bean.RolePermission;
import org.snbo.aclservice.bean.User;
import org.snbo.aclservice.helper.MemuHelper;
import org.snbo.aclservice.helper.PermissionHelper;
import org.snbo.aclservice.mapper.PermissionMapper;
import org.snbo.aclservice.service.PermissionService;
import org.snbo.aclservice.service.RolePermissionService;
import org.snbo.aclservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author snbo
 * @since 2020-01-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserService userService;

    /**
     * 获取全部菜单
     */
    @Override
    public List<Permission> queryAllMenu() {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);

        return bulid(permissionList);
    }

    /**
     * 根据角色获取菜单
     */
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id", roleId));
        //转换给角色id与角色权限对应Map对象
        for (Permission permission : allPermissionList) {
            for (RolePermission rolePermission : rolePermissionList) {
                if (rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }


        return bulid(allPermissionList);
    }

    /**
     * 给角色分配权限
     */
    @Override
    public void saveRolePermissionRelationShip(String roleId, String[] permissionIds) {

        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", roleId));


        List<RolePermission> rolePermissionList = new ArrayList<>();
        for (String permissionId : permissionIds) {
            if (StringUtils.isEmpty(permissionId)) {
                continue;
            }

            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissionList);
    }

    @Override
    public void removeChildById(String id) {
        List<String> idList = new ArrayList<>();
        this.selectChildListById(id, idList);

        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList;
        if (this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList;
        if (this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }
        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
        return MemuHelper.bulid(permissionList);
    }

    /**
     * 判断用户是否系统管理员
     *
     * @param userId 用户id
     * @return 是管理员返回则true
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);
        return null != user && "admin".equals(user.getUsername());
    }

    /**
     * 递归获取子节点
     */
    private void selectChildListById(String id, List<String> idList) {
        List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }

    /**
     * 使用递归方法建菜单
     */
    private static List<Permission> bulid(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     */
    private static Permission findChildren(Permission treeNode, List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<>());

        for (Permission it : treeNodes) {
            if (treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

    @Override
    public List<Permission> getAllMenuMogu() {
        //先查询出所有菜单数据
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);
        //进行封装
        return buildPermission(permissionList);
    }

    /**
     * 把所有菜单数据进行封装
     */
    public static List<Permission> buildPermission(List<Permission> permissionList) {
        List<Permission> resultList = new ArrayList<>();
        //遍历所有集合,得到顶层菜单 pid = 0,设置 level = 1,把顶层菜单加入结果集
        for (Permission permission : permissionList) {
            if ("0".equals(permission.getPid())) {
                permission.setLevel(1);
                //加入结果集之前根据顶层菜单递归查询其子菜单
                Permission thePermission = handleChildren(permission, permissionList);
                resultList.add(thePermission);
            }
        }
        return resultList;
    }

    /**
     * 递归查询子菜单
     */
    public static Permission handleChildren(Permission permission, List<Permission> permissionList) {
        //从所有菜单里面寻找子菜单
        for (Permission permissionNode : permissionList) {
            //如果是它的子菜单
            if (permissionNode.getPid().equals(permission.getId())) {
                //子菜单的 level 比它大一位
                permissionNode.setLevel(permission.getLevel() + 1);
                //处理子菜单自己的子菜单后加进当前菜单
                if (permission.getChildren() == null) {
                    permission.setChildren(new ArrayList<>());
                }
                permission.getChildren().add(handleChildren(permissionNode, permissionList));
            }
        }
        return permission;
    }


    @Override
    public void removeChildByIdMogu(String id) {
        //1 创建list集合，用于封装所有删除菜单id值
        List<String> idList = new ArrayList<>();
        //2 向idList集合设置删除菜单id
        this.selectPermissionChildById(id, idList);
        //把当前id封装到list里面
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    /**
     * 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
     */
    private void selectPermissionChildById(String id, List<String> idList) {
        //查询菜单里面子菜单id
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", id);
        wrapper.select("id");
        List<Permission> childIdList = baseMapper.selectList(wrapper);
        //把childIdList里面菜单id值获取出来，封装idList里面，做递归查询
        for (Permission permission : childIdList) {
            //封装idList里面
            idList.add(permission.getId());
            //递归查询
            selectPermissionChildById(permission.getId(), idList);
        }
    }

    @Override
    public void saveRolePermissionRelationShipMogu(String roleId, String[] permissionIds) {
        //roleId角色id
        //permissionId菜单id 数组形式
        //1 创建list集合，用于封装添加数据
        List<RolePermission> rolePermissionList = new ArrayList<>();
        //遍历所有菜单数组
        for (String perId : permissionIds) {
            //RolePermission对象
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(perId);
            //封装到list集合
            rolePermissionList.add(rolePermission);
        }
        //添加到角色菜单关系表
        rolePermissionService.saveBatch(rolePermissionList);
    }
}
