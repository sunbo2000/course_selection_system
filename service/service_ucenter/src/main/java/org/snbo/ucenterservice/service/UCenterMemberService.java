package org.snbo.ucenterservice.service;

import org.snbo.ucenterservice.bean.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import org.snbo.ucenterservice.bean.vo.AccountInfo;
import org.snbo.ucenterservice.bean.vo.LoginInfoVo;
import org.snbo.ucenterservice.bean.vo.LoginVo;
import org.snbo.ucenterservice.bean.vo.RegisterVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author snbo
 * @since 2022-04-06
 */
public interface UCenterMemberService extends IService<UcenterMember> {
    /**
     * description: 根据登录信息对用户登录行为做出验证
     *
     * @param loginVo 用户登录表单信息
     * @return {@link String} 登录验证成功返回 token 字符串
     * @author sunbo
     * @date 2022/5/28 18:00
     */
    String login(LoginVo loginVo);

    /**
     * description: 根据注册信息做出注册验证,验证成功则添加用户信息到数据库中
     *
     * @param registerVo 用户注册表单信息
     * @author sunbo
     * @date 2022/5/28 18:00
     */
    void register(RegisterVo registerVo);

    /**
     * description: 从请求头中获取用户 token 信息, 根据 token 信息获取已经登录的用户的信息
     *
     * @param request http 请求
     * @return {@link LoginInfoVo}
     * @author sunbo
     * @date 2022/5/28 18:00
     */
    LoginInfoVo getLoginInfo(HttpServletRequest request);

    /**
     * description: 根据 openid 从数据库获取用户信息
     *
     * @param openId 微信号唯一的 openid
     * @return {@link UcenterMember} 用户信息类
     * @author sunbo
     * @date 2022/5/28 18:00
     */
    UcenterMember getMemberByOpenId(String openId);

    /**
     * description: 根据日期查询日期当天注册人数
     *
     * @param day 日期(yyyy-MM-dd)
     * @return {@link Integer}
     * @author sunbo
     * @date 2022/5/28 18:00
     */
    Integer countRegister(String day);

    /**
     * description: 根据 http 请求获取用户名,根据用户名获取用户的账号信息
     *
     * @param request http 请求
     * @return {@link AccountInfo} 返回用户账号信息
     * @author sunbo
     * @date 2022/5/30 14:41
     */
    AccountInfo getAccountInfo(HttpServletRequest request);

    /**
     * description: 用户扫码后会回调此方法,根据临时票据发送请求得到 accessToken 和 openId,拿到accessToken 和 openId,
     * 再去请求固定地址,获取扫描人信息,完成登录或注册操作
     *
     * @param code  微信授权临时票据
     * @param state 授权状态
     * @return {@link String} 登录或注册完成页面地址
     * @author sunbo
     * @date 2022/5/30 14:49
     */
    String callback(String code, String state);

    /**
     * description: 生成微信登录的二维码
     *
     * @return {@link String} 显示二维码的页面的地址
     * @author sunbo
     * @date 2022/5/30 14:56
     */
    String getWxCode();
}
