package indi.jcl.magicutils.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token工具类
 * Created by jcl on 2019/1/3
 */
public class TokenUtil {
    private static final String TOKEN_SECRET = "";
    private static final int TOKEN_EXPIRE_TIME = 60;

    /**
     * 创建token
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String createToken(Map<String, String> data) {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,TOKEN_EXPIRE_TIME);
        Date expiresDate = nowTime.getTime();
        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        JWTCreator.Builder builder = JWT.create().withHeader(map) // header
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate); // expire time
        for (Map.Entry<String, String> e : data.entrySet()) {
            builder.withClaim(e.getKey(), e.getValue());
        }
        String token = builder.sign(Algorithm.HMAC256(TOKEN_SECRET)); // signature
        return token;
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> parse(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }

    /**
     * 校验token
     *
     * @param token
     * @return 1:校验通过,0:过期,-1:校验不通过
     */
    public static int verify(String token) {
        try {
            parse(token);
            return 1;
        } catch (TokenExpiredException e) {
            return 0;
        } catch (JWTDecodeException | SignatureVerificationException e) {
            return -1;
        }
    }

}
