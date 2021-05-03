package com.mashibing.jwt01.util;

import io.jsonwebtoken.*;

import java.util.Calendar;
import java.util.Date;

/***********************
 * @Description: jwt(java web token) 工具类<BR>
 * @author: zhao.song
 * @since: 2021/5/3 10:17
 * @version: 1.0
 ***********************/
public final class JwtUtil {
    /**
     * 密钥,仅服务端存储
     */
    private static String secret = "ko34613h_we]rg3in_yip1!";

    /**
     * Description: 生成token <BR>
     *
     * @param subject:   消息体
     * @param issueDate: 签发时间
     * @return: {@link String}
     * @author: zhao.song   2021/5/3 10:19
     */
    public static String createToken(String subject, Date issueDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(issueDate);
        c.add(Calendar.DAY_OF_MONTH, 20);

        String compactJws = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issueDate)
                .setExpiration(c.getTime())
                //签名
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return compactJws;
    }

    /**
     * Description: 解密jwt <BR>
     *
     * @param token:
     * @return: {@link String}
     * @author: zhao.song   2021/5/3 10:25
     */
    public static String parseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            if (claims != null) {
                return claims.getSubject();
            }
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        String token = createToken("userid=1,role=admin,price=398", new Date());
        System.out.println("create token is: " + token);
        System.out.println(parseToken(token));
    }


}
