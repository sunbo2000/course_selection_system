package org.snbo.springscurity.filter;


import org.snbo.springscurity.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author hanlin
 * @date 2022年03月26日 22:42
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        //如果token为空
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }
        if (tokenManager.checkToken(token)) {
            //解析token
            String userName = tokenManager.getUserFromToken(token);

            //从redis中根据用户名获取权限列表
            if (redisTemplate.hasKey(userName)) {
                List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(userName);
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                for (String permissionValue : permissionValueList) {
                    if (StringUtils.isEmpty(permissionValue)) {
                        continue;
                    }
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
                    authorities.add(authority);
                }

                //存入SecurityContextHelper
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userName, token, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                //放行
                filterChain.doFilter(request, response);
                return;
            }
        }
        //放行
        filterChain.doFilter(request, response);

    }
}
