package org.snbo.aclservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.snbo.aclservice.bean.UserRole;
import org.snbo.aclservice.mapper.UserRoleMapper;
import org.snbo.aclservice.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
