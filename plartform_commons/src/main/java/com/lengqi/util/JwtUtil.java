package com.lengqi.util;

import com.lengqi.entity.User;
import io.jsonwebtoken.*;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    /**
     * jwt的加密密钥
     */
    private static final String KEY = "kdjqdoqwjsoqs_wqkwqdscdscn";
    public static final Long TTL = 7 * 24 * 60 * 60 * 1000L;//七天过期
    /**
     * 根据用户对象 - 生成JWT token
     * @return
     */
    public static String createJwtToken(User user){
        JwtBuilder builder = Jwts.builder()
                .setId(user.getId()+"")
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())//签发时间
                .setExpiration(new Date(System.currentTimeMillis()+TTL))//过期时间
                .claim("id",user.getId())
                .claim("username",user.getUsername())
                .claim("nickname",user.getNickname())
                .signWith(SignatureAlgorithm.HS256,KEY);//签证

        return builder.compact();
    }
    public static User parseJwtToken(String token){
        User user = null;
        try {
            Claims body = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
            Integer id = (Integer)body.get("id");
            String username = (String)body.get("username");
            String nickname = (String)body.get("nickname");
            user = new User().setId(id).setUsername(username).setNickname(nickname);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {
//        User user = new User().setId(1).setUsername("张三").setNickname("小黑");
//        String token = createJwtToken(user);
//        System.out.println(token);
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoi5byg5LiJIiwiaWF0IjoxNTkwNjgxMDE4LCJleHAiOjE1OTEyODU4MTgsImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSIsIm5pY2tuYW1lIjoi5bCP6buRIn0.WHBfglgmm6A1d_hcJWNIjCKr5J7MBBsR763dqieFiyY";
        User user1 = parseJwtToken(token);
        System.out.println(user1);
    }
}
