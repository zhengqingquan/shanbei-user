package com.github.shanbei.shanbeiuser.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    // 有效期为60*60*1000 一小时
    public static final Long JWT_TTl = 60 * 60 * 1000L;

    // 设置密钥明文
    public static final String JWT_KEY = "shanbei";

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
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //
        SecretKey secretKey = generalKey();
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

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

        // setIssuer: sets the iss (Issuer) Claim
        // setSubject: sets the sub (Subject) Claim
        // setAudience: sets the aud (Audience) Claim
        // setExpiration: sets the exp (Expiration Time) Claim
        // setNotBefore: sets the nbf (Not Before) Claim
        // setIssuedAt: sets the iat (Issued At) Claim
        // setId: sets the jti (JWT ID) Claim

        String jwt = Jwts.builder()
                .setId(uuid)
                .setSubject(subject)
                .setIssuer("sg")
                .setIssuedAt(nowDate)
                .signWith(key)
                .setExpiration(expDate).compact();

        System.out.println(        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt));
        // 构建jwt
        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)
                .setIssuer("sg")
                .setIssuedAt(nowDate)
                .signWith(key)
                .setExpiration(expDate);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(createJWT("123"));
        System.out.println(parseJWT(createJWT("123")));
    }

    /**
     * 生成加密后的密钥
     */
    public static SecretKey generalKey() {
        // 使用Base64对字符串进行解码。
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);

        // 第一个参数表示用于创建密钥的原始二进制数据。
        // 第二个参数表示从 encodedKey 数组的起始位置开始使用数据。
        // 第三个表示使用 encodedKey 数组的长度
        // 第四个参数表示使用的加密算法。
        // return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    /**
     * 解析JWT
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJwt(jwt)
                .getBody();
    }


}
