package org.snbo.aclservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.snbo.aclservice.bean.User;
import org.snbo.aclservice.mapper.UserMapper;
import org.snbo.aclservice.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public Map<String, Object> getPage(Long page, Long limit, User userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username", userQueryVo.getUsername());
        }
        baseMapper.selectPage(pageParam, wrapper);

        Map<String, Object> map = new HashMap<>(2);
        map.put("items", pageParam.getRecords());
        map.put("total", pageParam.getTotal());

        return map;
    }
}
