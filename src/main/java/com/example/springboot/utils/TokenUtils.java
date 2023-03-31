package com.example.springboot.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
//生成token
public class TokenUtils {

    public static String genToken(String userId, String sign) {
        return JWT.create().withAudience(userId) //将userid保存到token里面。作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                .sign(Algorithm.HMAC256(sign));
    }
}


