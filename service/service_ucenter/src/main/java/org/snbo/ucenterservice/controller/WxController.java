package org.snbo.ucenterservice.controller;

import com.google.gson.Gson;
import org.hamcrest.Condition;
import org.snbo.commonutils.JwtUtils;
import org.snbo.servicebase.exceptionhandler.MoguException;
import org.snbo.ucenterservice.bean.UcenterMember;
import org.snbo.ucenterservice.service.UCenterMemberService;
import org.snbo.ucenterservice.util.ConstantWxUtils;
import org.snbo.ucenterservice.util.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author sunbo
 * @create 2022-04-09-15:09
 */
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxController {

    @Autowired
    private UCenterMemberService memberService;

    @GetMapping("/callback")
    public String callback(String code, String state) {
        return memberService.callback(code, state);
    }

    /**
     * 生成微信扫描二维码
     */
    @GetMapping("/login")
    public String getWxCode() {
        return memberService.getWxCode();
    }
}
