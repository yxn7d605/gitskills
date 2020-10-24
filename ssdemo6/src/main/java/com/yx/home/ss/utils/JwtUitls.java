package com.yx.home.ss.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>jwt token生成工具</p>
 *
 * <p>1. jwt三部分：header，payload，signature</p>
 *
 * <p>2. header部分：{"alg": "HS256", "typ": "JWT"}</p>
 *
 * <p>
 * 3. payload部分：
 * <li>iss (issuer)：签发人</li>
 * <li>exp (expiration time)：过期时间</li>
 * <li>sub (subject)：主题</li>
 * <li>aud (audience)：受众</li>
 * <li>nbf (Not Before)：生效时间</li>
 * <li>iat (Issued At)：签发时间</li>
 * <li>jti (JWT ID)：编号</li>
 * </p>
 */
public class JwtUitls {
    public static class JwtHeader {
        private String alg;

        private String typ;

        public String getAlg() {
            return alg;
        }

        public void setAlg(String alg) {
            this.alg = alg;
        }

        public String getTyp() {
            return typ;
        }

        public void setTyp(String typ) {
            this.typ = typ;
        }
    }

    public static class JwtPayoad {
        private String issuer;
        private Date expiresAt;
        private String subject;
        private String audience;
        private Date notBefore;
        private Date issuedAt;
        private String JWTId;
        private String claimName;
        private Map<String, Object> claimMap;

        public String getIssuer() {
            return issuer;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        public Date getExpiresAt() {
            return expiresAt;
        }

        public void setExpiresAt(Date expiresAt) {
            this.expiresAt = expiresAt;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getAudience() {
            return audience;
        }

        public void setAudience(String audience) {
            this.audience = audience;
        }

        public Date getNotBefore() {
            return notBefore;
        }

        public void setNotBefore(Date notBefore) {
            this.notBefore = notBefore;
        }

        public Date getIssuedAt() {
            return issuedAt;
        }

        public void setIssuedAt(Date issuedAt) {
            this.issuedAt = issuedAt;
        }

        public String getJWTId() {
            return JWTId;
        }

        public void setJWTId(String JWTId) {
            this.JWTId = JWTId;
        }

        public String getClaimName() {
            return claimName;
        }

        public void setClaimName(String claimName) {
            this.claimName = claimName;
        }

        public Map<String, Object> getClaimMap() {
            return claimMap;
        }

        public void setClaimMap(Map<String, Object> claimMap) {
            this.claimMap = claimMap;
        }
    }

    public static Map<String, Object> jwtHeader2Map(JwtHeader jwtHeader) {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", jwtHeader.getAlg());
        header.put("typ", jwtHeader.getTyp());

        return header;
    }

    /**
     * 生成一个新的token
     *
     * @param jwtHeader
     * @param jwtPayoad
     * @param salt
     * @return
     */
    public static String createToken(JwtHeader jwtHeader, JwtPayoad jwtPayoad, String salt) {
        // 生成盐供加密使用
        Algorithm algorithm = Algorithm.HMAC256(salt);

        String token = JWT.create()
                .withHeader(jwtHeader2Map(jwtHeader))
                .withIssuer(jwtPayoad.getIssuer())
                .withExpiresAt(jwtPayoad.getExpiresAt())
                .withSubject(jwtPayoad.getSubject())
                .withAudience(jwtPayoad.getAudience())
                .withNotBefore(jwtPayoad.getNotBefore())
                .withIssuedAt(jwtPayoad.getIssuedAt())
                .withJWTId(jwtPayoad.getJWTId())
                .withClaim(jwtPayoad.claimName, jwtPayoad.getClaimMap())
                .sign(algorithm);

        return token;
    }

    public static Date getExpiresAt(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Date expiresAt = decodedJWT.getExpiresAt();

        return expiresAt;
    }

    public static Date getIssuedAt(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Date issuedAt = decodedJWT.getIssuedAt();

        return issuedAt;
    }

    public static Map<String, Object> getClaimMap(String token, String claimName) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Claim claim = decodedJWT.getClaim(claimName);

        return claim.asMap();
    }

    public static void main(String[] args) {
//        jack-password
        String pwd = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("jack-password");
        System.out.println(pwd);
    }
}
