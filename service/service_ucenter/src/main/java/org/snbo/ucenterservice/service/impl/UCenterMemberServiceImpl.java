package org.snbo.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.snbo.commonutils.JwtUtils;
import org.snbo.commonutils.MD5Utils;
import org.snbo.commonutils.TimeUtils;
import org.snbo.servicebase.exceptionhandler.MoguException;
import org.snbo.ucenterservice.bean.UcenterMember;
import org.snbo.ucenterservice.bean.vo.AccountInfo;
import org.snbo.ucenterservice.bean.vo.LoginInfoVo;
import org.snbo.ucenterservice.bean.vo.LoginVo;
import org.snbo.ucenterservice.bean.vo.RegisterVo;
import org.snbo.ucenterservice.mapper.UcenterMemberMapper;
import org.snbo.ucenterservice.service.UCenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.snbo.ucenterservice.util.ConstantWxUtils;
import org.snbo.ucenterservice.util.HttpClientUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author snbo
 * @since 2022-04-06
 */
@Service
public class UCenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UCenterMemberService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public UCenterMemberServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 登录方法
     */
    @Override
    public String login(LoginVo loginVo) {

        //获取登账号和密码
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //手机号和密码不能为空,前后端都判断

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new MoguException(20001, "登录失败");
        }

        //根据手机号码获取出用户再进行后面的判断
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember theMember = baseMapper.selectOne(wrapper);

        if (theMember == null) {
            throw new MoguException(20001, "账号或密码错误");
        }
        //判断密码是否正确
        //md5加密后比较
        if (!MD5Utils.encrypt(password).equals(theMember.getPassword())) {
            throw new MoguException(20001, "账号或密码错误");
        }

        //判断用户状态
        if (theMember.getIsDisabled()) {
            throw new MoguException(20001, "登录失败");
        }
        //验证成功
        //生成token字符串
        return JwtUtils.getJwtToken(theMember.getId(), theMember.getNickname());
    }


    /**
     * 根据id获取是否登录信息
     */
    @Override
    public LoginInfoVo getLoginInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            throw new MoguException(28004, "未登录");
        }
        UcenterMember member = baseMapper.selectById(memberId);
        LoginInfoVo loginInfoVo = new LoginInfoVo();
        BeanUtils.copyProperties(member, loginInfoVo);
        return loginInfoVo;
    }

    @Override
    public UcenterMember getMemberByOpenId(String openId) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openId);
        return baseMapper.selectOne(wrapper);
    }


    @Override
    public AccountInfo getAccountInfo(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(id)) {
            throw new MoguException(20001, "未登录");
        }
        UcenterMember byId = baseMapper.selectById(id);
        AccountInfo accountInfo = new AccountInfo();
        BeanUtils.copyProperties(byId, accountInfo);
        if (!StringUtils.isEmpty(byId.getPassword())) {
            accountInfo.setPsd(true);
        }
        accountInfo.setMobile(byId.getMobile().substring(0, 3) + "****" + byId.getMobile().substring(7));

        return accountInfo;
    }
}
