package com.github.shanbei.shanbeiuser.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    // 有效期为60*60*1000 一小时
    public static final Long JWT_TTl = 60 * 60 * 1000L;

    // 设置密钥明文
    public static final String JWT_KEY = "shanbei";

    // We need a signing key, so we'll create one just for this example.
    // Usually the key would be read from your application configuration instead.
    public static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 获得不带“-”的UUID
     *
     * @return UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成jwt
     *
     * @param subject token中要存放的数据（Json格式）
     * @return
     */
    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
        return builder.compact();
    }

    /**
     * 创建token
     *
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        // 设置过期时间
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
        return builder.compact();
    }

    /**
     *
     * @param subject
     * @param ttlMillis JWT的存活时间（time to live Millisecond）
     * @param uuid
     * @return
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        // 设置签名算法
        // Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // System.out.println(key);
        // 设置过期时间
        // 第一步：获取当前系统时间，以毫秒为单位。
        // 第二步：如果参数没给JWT存活时间，则使用默认时间。
        // 第三步：获取过期时的系统时间。
        long nowMillis = System.currentTimeMillis();
        Date nowDate = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTl;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        // 构建jwt
        return Jwts.builder()
                // .setId(uuid)
                .setSubject(subject)
                // .setIssuer("shanbei")
                // .setIssuedAt(nowDate)
                .signWith(key,SignatureAlgorithm.HS256)
                .setExpiration(expDate);
    }

    public static void main(String[] args) throws Exception {
        // byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        // System.out.println(Arrays.toString(encodedKey));
        String a = createJWT("123");
        System.out.println(a);
        System.out.println(parseJWT(a));
    }

    /**
     * 生成加密后的密钥
     */
    public static SecretKey generalKey() {
        // 使用Base64对字符串进行解码。
        byte[] encodedKey = Base64.getDecoder().decode(JWT_KEY);

        // 第一个参数表示用于创建密钥的原始二进制数据。
        // 第二个参数表示从 encodedKey 数组的起始位置开始使用数据。
        // 第三个表示使用 encodedKey 数组的长度
        // 第四个参数表示使用的加密算法。
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

        // return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    /**
     * 解析JWT
     * https://github.com/jwtk/jjwt#reading-a-jws
     *
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws JwtException {
        // Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // System.out.println(key);
        System.out.println(jwt);
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJwt(jwt)
                .getBody();
    }


    /**
     * 获取请求token
     *
     * @param request 请求
     * @return token
     */
    private String getToken(HttpServletRequest request)
    {
        // 从请求头中获取token
        String token = request.getHeader(header);

        // 如果Token存在且以"Bearer"为前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith("Bearer"))
        {
            token = token.replace("Bearer", "");
        }
        return token;
    }

}
