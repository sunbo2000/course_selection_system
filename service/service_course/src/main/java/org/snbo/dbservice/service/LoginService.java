package org.snbo.dbservice.service;

import org.snbo.dbservice.bean.vo.LoginVo;
import org.snbo.dbservice.bean.vo.StudentInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sunbo
 * @date 2022-06-17-19:24
 */

public interface LoginService {

    /**
     * description: 登录验证
     *
     * @param loginVo 登录信息
     * @return {@link String}
     * @author sunbo
     * @date 2022/6/17 19:25
     */
    String login(LoginVo loginVo);

    /**
     * description: 根据请求头获取学生信息
     *
     * @param request 请求头
     * @return {@link StudentInfo}
     * @author sunbo
     * @date 2022/6/17 19:48
     */
    StudentInfo getLoginInfo(HttpServletRequest request);
}
