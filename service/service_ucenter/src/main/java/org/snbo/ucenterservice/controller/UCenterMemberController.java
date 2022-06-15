package org.snbo.ucenterservice.controller;


import io.swagger.annotations.ApiOperation;
import org.snbo.commonutils.JwtUtils;
import org.snbo.commonutils.R;
import org.snbo.commonutils.vo.UCenterMember;
import org.snbo.servicebase.exceptionhandler.MoguException;
import org.snbo.ucenterservice.bean.UcenterMember;
import org.snbo.ucenterservice.bean.vo.AccountInfo;
import org.snbo.ucenterservice.bean.vo.LoginInfoVo;
import org.snbo.ucenterservice.bean.vo.LoginVo;
import org.snbo.ucenterservice.bean.vo.RegisterVo;
import org.snbo.ucenterservice.service.UCenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author snbo
 * @since 2022-04-06
 */
@RestController
@RequestMapping("/eduCenter/member")
public class UCenterMemberController {

    private final UCenterMemberService memberService;

    @Autowired
    public UCenterMemberController(UCenterMemberService memberService) {
        this.memberService = memberService;
    }


    @PostMapping("/login")
    @ApiOperation(value = "登录验证")
    public R login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return R.ok().data("token", token);
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册验证")
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }


    @ApiOperation(value = "根据 token 获取用户信息")
    @GetMapping("/getInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //从请求头获取到id,根据id查询到用户信息,将用户信息返回
        LoginInfoVo loginInfo = memberService.getLoginInfo(request);
        return R.ok().data("loginInfo", loginInfo);
    }

    @GetMapping("/getAccount")
    public R getAccountInfo(HttpServletRequest request) {
        AccountInfo accountInfo = memberService.getAccountInfo(request);
        return R.ok().data("accountInfo", accountInfo);
    }

    @PutMapping()
    public R updateInfo(@RequestBody UcenterMember member) {
        boolean b = memberService.updateById(member);
        if (!b) {
            throw new MoguException(20001, "更改信息失败");
        }
        return R.ok();
    }

    /**
     * 根据id获取用户信息
     * feign调用
     */
    @ApiOperation(value = "根据id获取用户信息,用于其他服务远程 feign 调用")
    @GetMapping("/{id}")
    public UCenterMember getMemberInfoById(@PathVariable("id") String id) {
        UCenterMember uCenterMember = new UCenterMember();
        BeanUtils.copyProperties(memberService.getById(id), uCenterMember);
        return uCenterMember;
    }

    /**
     * 查询某一天注册人数
     */
    @GetMapping("/countRegister/{day}")
    public Integer countRegister(@PathVariable String day) {
        return memberService.countRegister(day);
    }
}

