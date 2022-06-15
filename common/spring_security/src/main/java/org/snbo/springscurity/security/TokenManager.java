package org.snbo.springscurity.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * <p>
 * token管理
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Component
public class TokenManager {

    /**
     * token过期时间
     */
    private long tokenExpiration = 24*60*60*1000;

    private String tokenSignKey = "ukc8BDbRigUfhenGokugpiufbaDaYkyukdhasg6pFfWgbfyuovbdhfbalshfdus2jZWLPO";

    /**
     * 使用jwt根据用户名生成token
     */
    public String createToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    /**
     * 从token中获取用户名
     */
    public String getUserFromToken(String token) {
        String user = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return user;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }

}
