package com.yx.home.ss.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.yx.home.ss.bo.LoginUserInfo;
import com.yx.home.ss.service.RedisService;
import com.yx.home.ss.service.TokenService;
import com.yx.home.ss.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenServiceImpl implements TokenService {
    private static final String ALG = "HS256";

    private static final String TYP = "JWT";

    private static final String ISS = "ERM_TOKEN";

    private static final String SUB = "ACCESS_TOKEN";

    private static final String AUD = "ERM_USER";

    private static final String JWT_ID = "JWT_ID";

    private static final String CLAIM_NAME = "LOGIN_USER";

    private static final int EXPIRE_MINUS = 1;

    private static final String LOGIN_USER_CNT_KEY_PRE = "login_user_cnt_";

    @Autowired
    private RedisService redisService;

    private String buildLoginUserCntKey(String oaCode) {
        return LOGIN_USER_CNT_KEY_PRE + oaCode;
    }

    private String genSalt(String oaCode) {
        String loginUserCntKey = buildLoginUserCntKey(oaCode);

        long loginUserCnt = (long) redisService.get(loginUserCntKey);

        return "123456";
    }

    private Map<String, Object> claimMap(LoginUserInfo loginUserInfo) {
        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put(LoginUserInfo.OA_CODE, loginUserInfo.getOaCode());
        claimMap.put(LoginUserInfo.STATUS, loginUserInfo.getStauts());
        claimMap.put(LoginUserInfo.LOGIN_CNT, loginUserInfo.getLoginCnt());

        return claimMap;
    }

    private LoginUserInfo claimMap2LoginUserInfo(Map<String, Object> claimMap) {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setOaCode((String) claimMap.get(LoginUserInfo.OA_CODE));
        loginUserInfo.setStauts((Integer) claimMap.get(LoginUserInfo.STATUS));
        loginUserInfo.setLoginCnt((Integer) claimMap.get(LoginUserInfo.LOGIN_CNT));

        return loginUserInfo;
    }

    @Override
    public String createToken(LoginUserInfo loginUserInfo) {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", ALG);
        header.put("typ", TYP);

        Date now = new Date();

        // 生成盐供加密使用
        String salt = genSalt(loginUserInfo.getOaCode());
        Algorithm algorithm = Algorithm.HMAC256(salt);

        String token = JWT.create()
                .withHeader(header)
                .withIssuer(ISS)
                .withExpiresAt(DateUtils.addMinus(now, EXPIRE_MINUS))
                .withSubject(SUB)
                .withAudience(AUD)
                .withNotBefore(now)
                .withIssuedAt(now)
                .withJWTId(JWT_ID)
                .withClaim(CLAIM_NAME, claimMap(loginUserInfo))
                .sign(algorithm);

        return token;
    }

    @Override
    public LoginUserInfo verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256("123456");
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Object> map = decodedJWT.getClaim("user").asMap();

        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setOaCode((String) map.get(LoginUserInfo.OA_CODE));
        loginUserInfo.setStauts((Integer) map.get(LoginUserInfo.STATUS));

        return loginUserInfo;
    }

    @Override
    public LoginUserInfo parseLoginUserInfo(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Map<String, Object> claimMap = decodedJWT.getClaim(CLAIM_NAME).asMap();

        return claimMap2LoginUserInfo(claimMap);
    }

    public static void main(String[] args) {
//        JwtTokenServiceImpl jwtTokenService = new JwtTokenServiceImpl();
//        LoginUserInfo loginUserInfo = new LoginUserInfo();
//        loginUserInfo.setOaCode("123456");
//        loginUserInfo.setStauts(1);
//        String token = jwtTokenService.createToken(loginUserInfo);
//        System.out.println(token);

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBQ0NFU1NfVE9LRU4iLCJhdWQiOiJFUk1fVVNFUiIsIm5iZiI6MTYwMzQ0MTc2MiwiaXNzIjoiRVJNX1RPS0VOIiwiZXhwIjoxNjAzNDQxODIyLCJpYXQiOjE2MDM0NDE3NjIsImp0aSI6IkpXVF9JRCJ9.WEAmG2Ae_NwqfU0KCyDpp0HSk25S0s4DYI_N3PZCqyY";
        DecodedJWT decodedJWT = JWT.decode(token);
        System.out.println(decodedJWT.getClaim(CLAIM_NAME).asMap().get(LoginUserInfo.OA_CODE));
    }
}
