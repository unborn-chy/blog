package com.scitc.blog.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.scitc.blog.model.UserInfo;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    /**
     * 过期时间
     */
    private static final long EXPIRE_TIME = 60 * 60 * 1000;
    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "b9dca87432d942ae8746593f5146f192";
    public static String sign(UserInfo userInfo) {
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            //私钥与加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            return JWT.create()
                    .withHeader(header)
                    .withClaim("userInfo", JSONObject.fromObject(userInfo).toString())
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    //解码

    /**
     * 校验token是否正确
     * @param token
     * @return
     */
    public static boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
    public static String getUserInfo(String token){
        try{
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userInfo").asString();
        }catch (Exception e){
            return null;
        }
    }


}
