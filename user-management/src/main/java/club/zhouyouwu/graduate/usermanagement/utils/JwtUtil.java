package club.zhouyouwu.graduate.usermanagement.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Slf4j
public class JwtUtil {

    private static final String KEY = "022bdc63c3c5a45879ee6581508b9d03adfec4a4658c0ab3d722e50c91a351c42c231cf43bb8f86998202bd301ec52239a74fc0c9a9aeccce604743367c9646b";

    public static SecretKey generateKey() {
        byte[] encodedKey = Base64.decodeBase64(KEY);

        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * @param id        设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token
     * @param issuer    jwt签发人
     * @param subject   sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roleId之类的，作为什么用户的唯一标志。
     * @param ttlMillis 有效期
     * @return
     */
    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {
        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        SecretKey key = generateKey();
        // 这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                // iat: jwt的签发时间
                .setIssuedAt(now)
                .setIssuer(issuer)
                .setSubject(subject)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);
        // 设置过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) {
        //签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generateKey();
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
    }

    /**
     * 更新并延长token时间
     * @param claims
     * @param extendTime
     * @return
     */
    public static String extendTtlMillis(Claims claims, long extendTime){
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims);

        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        builder.setIssuedAt(now);

        Date ttl = new Date(nowMillis + extendTime);
        builder.setExpiration(ttl);
        return builder.compact();
    }

}
