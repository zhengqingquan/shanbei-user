package com.github.shanbei.shanbeiuser.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;

/**
 * JWT模块
 * 参考：
 * https://github.com/jwtk/jjwt#jws-read
 * https://www.bilibili.com/video/BV1bL4y1s71D?p=1&vd_source=27aa87ce82d65a5df4c722ca0e300116
 */
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

    // 有效期为 60 * 60 * 1000 一小时
    public static final Long JWT_TTL = 60 * 60 * 1000L;


    // 签发人
    public static final String JWT_ISSUER = "shanbei-backend";

    /**
     * 设置密钥明文
     * 这个密钥可以是任意字符串
     */
    public static final String JWT_KEY = "shanbei";
    public static final String KEY = "ax0Sw8q5jQP4pp6CZ3HvRMV6/u1TFRAyPj//ov0I1y8=";

    /**
     * 获得不带 "-" 的UUID
     *
     * @return UUID
     */
    public static String getUUID() {
        // String::replaceAll 方法的底层实现每次调用时都会调用 java.util.regex.Pattern.compile() 方法。
        // 即使第一个参数不是一个正则表达式也是如此。
        // 这会带来显著的性能开销，因此应该谨慎使用。
        //当使用 String::replaceAll 方法时，第一个参数应该是一个真正的正则表达式。
        // 如果不是这样，String::replace 方法执行的操作与 String::replaceAll 相同，但没有正则表达式的性能损失。
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成jwt
     *
     * @param subject 主题
     * @return JWT字符串
     */
    public static String createJWT(String subject) {
        return getJwtBuilder(subject, null, getUUID());
    }

    /**
     * 创建token
     *
     * @param id
     * @param subject   主题
     * @param ttlMillis 存活时间
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        return getJwtBuilder(subject, ttlMillis, id);
    }

    /**
     * @param subject   主题
     * @param ttlMillis JWT的存活时间（time to live Millisecond）
     * @param uuid      UUID
     * @return
     */
    private static String getJwtBuilder(String subject, Long ttlMillis, String uuid) {

        // 对密钥进行解码
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));

        // 设置过期时间
        // 第一步：获取当前系统时间，以毫秒为单位。
        // 第二步：如果参数没给JWT存活时间，则使用默认时间。
        // 第三步：获取过期时的系统时间。
        long nowMillis = System.currentTimeMillis();
        Date nowDate = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        // 自定义信息
        Map<String, String> claim = new HashMap<>();
        claim.put("phone", "123456");

        // 构建jwt
        return Jwts.builder()
                .setClaims(claim) // 设置自定义的声明
                .setIssuer(JWT_ISSUER) // 设置标准的签发人
                .setSubject(subject) // 设置标准的主题（内容）
                .setAudience("shanbei-frontend") // 设置标准的签收人
                .setExpiration(expDate)// 设置标准的存活时间
                .setIssuedAt(nowDate) // 标准的签发日期
                //.setNotBefore() // 标准的令牌生效的时间
                .setId(uuid) // 设置标准的id
                //.signWith(key, SignatureAlgorithm.HS256)
                .signWith(key) // 生成JWT
                .compact(); // 序列化为一个字符串返回
    }

    /**
     * 生成一个密钥
     *
     * @return 密钥
     */
    public static String generationKey() {
        // 创建一个符合对称算法HS256的密钥。
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // 对密钥进行Base64的编码，因为生成的密钥可能不符合网络传输的规范，因此要使用编码Base64进行优化。
        return Encoders.BASE64.encode(key.getEncoded());
    }

    /**
     * 使用Java的标准加密来生成密钥。
     * 生成加密后的密钥
     */
    public static SecretKey generalKey() {
        // 使用Base64对字符串进行解码。
        byte[] encodedKey = Base64.getDecoder().decode(JWT_KEY);

        // var1：表示用于密钥的字节数组
        // var2：表示从 var1 数组的哪个位置开始读取字节，用于构造密钥
        // var3：表示从 var1 数组中读取多少个字节，用于构造密钥
        // var4：表示用于构造密钥的算法名称
        //return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

        // var1：表示用于密钥的字节数组
        // var2：表示用于构造密钥的算法名称
        return new SecretKeySpec(encodedKey, "AES");
    }


    public static void main(String[] args) throws Exception {
        String a = createJWT("123");
        System.out.println(a);
        System.out.println(parseJWT(a).getIssuedAt()); // 获取标准的信息
        System.out.println(parseJWT(a).get("phone")); // 获取自定义的信息
    }


    /**
     * 解析JWT
     * https://github.com/jwtk/jjwt#reading-a-jws
     *
     * @param jwsString
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwsString) throws JwtException {
        Jws<Claims> jws;
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));
        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwsString);

            // 我们可以放心地相信JWT
            // we can safely trust the JWT
            return jws.getBody();
        } catch (JwtException ex) {

            // 我们不能按照JWT的创建者的意图使用JWT
            // we *cannot* use the JWT as intended by its creator
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * 获取请求token
     *
     * @param request 请求
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        // 从请求头中获取token
        String token = request.getHeader(header);

        // 如果Token存在且以"Bearer"为前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith("Bearer")) {
            token = token.replace("Bearer", "");
        }
        return token;
    }

}
