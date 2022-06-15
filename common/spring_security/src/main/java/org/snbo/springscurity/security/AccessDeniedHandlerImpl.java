package org.snbo.springscurity.security;


import org.snbo.commonutils.R;
import org.snbo.commonutils.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hanlin
 * @date 2022年03月26日 15:10
 * 授权失败统一异常处理类
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ResponseUtil.out(response, R.error().message("您的权限不足"));
    }
}
