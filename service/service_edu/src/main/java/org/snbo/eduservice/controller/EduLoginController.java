package org.snbo.eduservice.controller;

import io.swagger.annotations.Api;
import org.snbo.commonutils.R;
import org.springframework.web.bind.annotation.*;


/**
 * @author sunbo
 * @create 2022-03-20-17:40
 */
@Api("后台登录管理")
@RestController
@RequestMapping("/eduService/user")
public class EduLoginController {
    @Deprecated
    @PostMapping("/login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @Deprecated
    @GetMapping("/info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://edu-mogu.oss-cn-chengdu.aliyuncs.com/12126.jpg");
    }
}
