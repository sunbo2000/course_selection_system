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
        //登录记录放进redis里
        String loginCountName = TimeUtils.getNowLoginCountName();
        String loginCount = redisTemplate.opsForValue().get(loginCountName);
        if (StringUtils.isEmpty(loginCount)) {
            redisTemplate.opsForValue().set(loginCountName, "1", TimeUtils.getInvalidTime(), TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(loginCountName, String.valueOf(Integer.parseInt(loginCount) + 1), 0);
        }

        //获取登录手机号和密码
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

    @Override
    public void register(RegisterVo registerVo) {
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();
        String code = registerVo.getCode();
        //判断非空
        if (StringUtils.isEmpty(nickname) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            throw new MoguException(20001, "注册失败");
        }

        //判断验证码
        //从redis中取出数据
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new MoguException(20001, "验证码错误,请检查后输入");
        }
        //判断是否已经注册过
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new MoguException(20001, "该号码已注册账号");
        }

        //都无误,添加到数据库
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5Utils.encrypt(password));
        //默认头像
        member.setAvatar("https://edu-mogu.oss-cn-chengdu.aliyuncs.com/%E9%BB%98%E8%AE%A4%E5%AE%87%E8%88%AA%E5%91%98%E5%A4%B4%E5%83%8F.png");
        baseMapper.insert(member);
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
    public Integer countRegister(String day) {
        return baseMapper.countRegister(day);
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

    @Override
    public String callback(String code, String state) {
        try {
            //得到授权临时票据code
            System.out.println(code);
            //从redis中将state获取出来，和当前传入的state作比较
            //如果一致则放行，如果不一致则抛出异常：非法访问

            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl =
                    "https://api.weixin.qq.com/sns/oauth2/access_token" +
                            "?appid=%s" +
                            "&secret=%s" +
                            "&code=%s" +
                            "&grant_type=authorization_code";
            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code);
            //发送请求得到 accessToken 和 openId
            //使用 httpclient 发送请求,得到结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //accessTokenInfo 是 key-value 形式的字符串,可以将其装换为map
            Gson gson = new Gson();
            HashMap<String, String> mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String accessToken = mapAccessToken.get("access_token");
            String openid = mapAccessToken.get("openid");

            //判断数据库中是否存在该微信用户,不存在添加,存在直接登录
            UcenterMember member = getMemberByOpenId(openid);
            if (member == null) {
                //拿到accessToken 和 openId,再去请求固定地址,获取扫描人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
                String userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap<String, String> uerInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = uerInfoMap.get("nickname");
                String headimgurl = uerInfoMap.get("headimgurl");
                //给数据库添加信息
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                this.save(member);
            }
            //使用jwt生成token字符串,里面存储id和昵称
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            //最后通过路径传递回首页面
            System.out.println(jwtToken);
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MoguException(20001, "登录失败");
        }

    }

    @Override
    public String getWxCode() {
        //请求微信地址
        //%s 为占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对 redirect_uri 进行编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(baseUrl, ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl, "atmogu");
        return "redirect:" + url;
    }
}
