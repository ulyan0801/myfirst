package com.my.springboot.utils.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtTokenUtil {
    private static int TOKEN_EXPIRATION; //过期时间
    private static String TOKEN_SIGNKEY; //签名
    private static String TOKEN_USERROLE_SIGNKEY; //签名
    private static String TOKEN_UUID_SIGNKEY; //签名

    private static String TOKEN_TIME_UNITS;//时间单位 时 分 秒 天 乘以以上 TOKEN_EXPIRATION过期时间 等于总时间


    @Value("${token.tokenTimeUnits}")
    public void setTokenTimeUnits(String tokenTimeUnits) {
        JwtTokenUtil.TOKEN_TIME_UNITS = tokenTimeUnits;
    }

    @Value("${token.tokenExpiration}")
    public void setTokenExpiration(int tokenExpiration) {
        JwtTokenUtil.TOKEN_EXPIRATION = tokenExpiration;
    }

    @Value("${token.tokenSignKey}")
    public void setTokenSignKey(String tokenSignKey) {
        JwtTokenUtil.TOKEN_SIGNKEY = tokenSignKey;
    }

    @Value("${token.tokenUserRoleSignKey}")
    public void setUserRoleKey(String userRoleKey) {
        JwtTokenUtil.TOKEN_USERROLE_SIGNKEY = userRoleKey;
    }

    @Value("${token.tokenUuidSignKey}")
    public void setUuidKey(String uuidKey) {
        JwtTokenUtil.TOKEN_UUID_SIGNKEY = uuidKey;
    }

    public static String createToken(String userName) {

        String token = Jwts.builder().setSubject(userName)
                .setExpiration(getCalendar().getTime())
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGNKEY).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    public static String createToken(String userName, String role) {
        String token = Jwts.builder().setSubject(userName)
                .claim(TOKEN_USERROLE_SIGNKEY, role)
                .setExpiration(getCalendar().getTime())
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGNKEY).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    public static String createTokenUuid(String userName, String role, String uuid) {
        String token = Jwts.builder().setSubject(userName)
                .claim(TOKEN_USERROLE_SIGNKEY, role)
                .claim(TOKEN_UUID_SIGNKEY, uuid)
                .setExpiration(getCalendar().getTime())
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGNKEY).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }


    public static Calendar getCalendar() {
        //时 Calendar.HOUR_OF_DAY
        //分钟 Calendar.MINUTE
        //秒 Calendar.SECOND
        //天 DAY_OF_MONTH


        Calendar c = Calendar.getInstance();

        if ("时".equals(TOKEN_TIME_UNITS)) {
            c.add(Calendar.HOUR_OF_DAY, TOKEN_EXPIRATION);
        } else if ("分".equals(TOKEN_TIME_UNITS)) {
            c.add(Calendar.MINUTE, TOKEN_EXPIRATION);
        } else if ("秒".equals(TOKEN_TIME_UNITS)) {
            c.add(Calendar.SECOND, TOKEN_EXPIRATION);
        } else if ("天".equals(TOKEN_TIME_UNITS)) {
            c.add(Calendar.DAY_OF_MONTH, TOKEN_EXPIRATION);
        } else {
            c.add(Calendar.HOUR_OF_DAY, TOKEN_EXPIRATION);
            System.out.println("----------时间单位设置有误,请检查时间单位的设置，已经设置默认为 时 为单位---------");
        }

        return c;
    }

    public static String getUserNameFromToken(String token) {
        String userName = Jwts.parser().setSigningKey(TOKEN_SIGNKEY).parseClaimsJws(token).getBody().getSubject();
        return userName;
    }

    public static String getUserRoleFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(TOKEN_SIGNKEY).parseClaimsJws(token).getBody();
        return claims.get(TOKEN_USERROLE_SIGNKEY).toString();
    }


    public static String getUuidFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(TOKEN_SIGNKEY).parseClaimsJws(token).getBody();
        return claims.get(TOKEN_UUID_SIGNKEY).toString();
    }

    /**
     * token验证方法
     */
//    JwtException 总异常
//
//    ClaimJwtException 获取Claim异常
//
//    ExpiredJwtException token过期异常
//
//    IncorrectClaimException token无效
//
//    MalformedJwtException 密钥验证不一致
//
//    MissingClaimException JWT无效
//
//    RequiredTypeException 必要类型异常
//
//    SignatureException 签名异常
//
//    UnsupportedJwtException 不支持JWT异常
    public static void verifyToken(String token) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(TOKEN_SIGNKEY).parseClaimsJws(token);
        Date d2 = ((Claims) claimsJws.getBody()).getExpiration();
        System.out.println("令牌过期时间：" + sdf.format(d2));
    }


}
