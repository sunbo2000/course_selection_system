package org.snbo.springscurity.filter;


import org.snbo.springscurity.security.TokenManager;
import org.snbo.commonutils.R;
import org.snbo.commonutils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 访问过滤器
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;
    private final static String ADMIN = "admin";

    public TokenAuthenticationFilter(AuthenticationManager authManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        super(authManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        logger.info("=================" + req.getRequestURI());
        //  判断是不是admin，超级管理员用户，如是，直接通过。
        if (req.getRequestURI().indexOf(ADMIN) == -1) {
            chain.doFilter(req, res);
            return;
        }

        // 不是admin，超级管理员用户
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            // 从redis里面取出用户权限
            authentication = getAuthentication(req);
        } catch (Exception e) {
            ResponseUtil.out(res, R.error());
        }

        // 权限不为空
        if (authentication != null) {
            // 设置权限
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            ResponseUtil.out(res, R.error());
        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里
        String token = request.getHeader("token");
        if (token != null && !"".equals(token.trim())) {
            // 根据token获取用户信息
            String userName = tokenManager.getUserFromToken(token);

            // 从redis中获取用户信息
            List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(userName);

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            // 权限封装
            for (String permissionValue : permissionValueList) {
                if (StringUtils.isEmpty(permissionValue)) {
                    continue;
                }
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
                authorities.add(authority);
            }

            // 用户存在权限，给用户添加权限并返回。
            if (!StringUtils.isEmpty(userName)) {
                return new UsernamePasswordAuthenticationToken(userName, token, authorities);
            }
            return null;
        }
        return null;
    }
}