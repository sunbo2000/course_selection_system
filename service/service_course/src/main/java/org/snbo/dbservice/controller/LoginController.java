package org.snbo.dbservice.controller;

import org.snbo.commonutils.R;
import org.snbo.dbservice.bean.vo.LoginVo;
import org.snbo.dbservice.bean.vo.StudentInfo;
import org.snbo.dbservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sunbo
 * @date 2022-06-17-19:22
 */
@RestController
@RequestMapping("/db/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public R studentLogin(@RequestBody LoginVo loginVo) {
        String token = loginService.login(loginVo);
        return R.ok().data("token", token);
    }

    @GetMapping
    public R getStudentLoginInfo(HttpServletRequest request) {
        StudentInfo studentInfo = loginService.getLoginInfo(request);
        return R.ok().data("studentInfo", studentInfo);
    }
}
