package com.yx.home.ss.service.impl;

import com.yx.home.ss.bo.LoginUserInfo;
import com.yx.home.ss.service.RedisService;
import com.yx.home.ss.service.UserLoginService;
import com.yx.home.ss.utils.CookieUtils;
import com.yx.home.ss.utils.DateUtils;
import com.yx.home.ss.utils.JwtUitls;
import com.yx.home.ss.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserLoginServiceImpl implements UserLoginService {
    private static final String ALG = "HS256";

    private static final String TYP = "JWT";

    private static final String ISS = "ERM_TOKEN";

    private static final String SUB = "ACCESS_TOKEN";

    private static final String AUD = "ERM_USER";

    private static final String JWT_ID = "JWT_ID";

    private static final String CLAIM_NAME = "LOGIN_USER";

    private static final int EXPIRE_MINUS = 30;

    private static final int REFRESH_MINUS = 5;

    @Autowired
    private RedisService redisService;

    private String parseTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (TOKEN_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    @Override
    public boolean isLoggedIn(HttpServletRequest request) {
        String token = parseTokenFromRequest(request);

        return token == null ? false : true;
    }

    @Override
    public String parseToken(HttpServletRequest request) {
        return parseTokenFromRequest(request);
    }

    @Override
    public boolean isLoginExpired(String token) {
        Date expiresAt = JwtUitls.getExpiresAt(token);

        return DateUtils.isDateExpired(expiresAt);
    }

    @Override
    public boolean isNeedRefreshToken(String token) {
        Date issuedAt = JwtUitls.getIssuedAt(token);
        // 计算刷新时间
        Date refreshAt = DateUtils.addMinus(issuedAt, REFRESH_MINUS);

        return DateUtils.isDateExpired(refreshAt);
    }

    private String buildSaltKey(LoginUserInfo loginUserInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append(loginUserInfo.getOaCode()).append("_").append(loginUserInfo.getLoginId());

        return sb.toString();
    }

    private String genSalt(LoginUserInfo loginUserInfo) {
        String salt = BCrypt.gensalt();

        return salt;
    }

    private String querySalt(LoginUserInfo loginUserInfo) {
        return (String) redisService.get(buildSaltKey(loginUserInfo));
    }

    private Map<String, Object> claimMap(LoginUserInfo loginUserInfo) {
        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put(LoginUserInfo.OA_CODE, loginUserInfo.getOaCode());
        claimMap.put(LoginUserInfo.LOGIN_ID, loginUserInfo.getLoginId());

        return claimMap;
    }

    private JwtUitls.JwtHeader buildJwtHeader() {
        JwtUitls.JwtHeader jwtHeader = new JwtUitls.JwtHeader();
        jwtHeader.setAlg(ALG);
        jwtHeader.setTyp(TYP);

        return jwtHeader;
    }

    JwtUitls.JwtPayoad buildJwtPayload(Date issueAt, LoginUserInfo loginUserInfo) {
        JwtUitls.JwtPayoad jwtPayoad = new JwtUitls.JwtPayoad();
        jwtPayoad.setIssuer(ISS);
        jwtPayoad.setExpiresAt(DateUtils.addMinus(issueAt, EXPIRE_MINUS));
        jwtPayoad.setSubject(SUB);
        jwtPayoad.setAudience(AUD);
        jwtPayoad.setNotBefore(issueAt);
        jwtPayoad.setIssuedAt(issueAt);
        jwtPayoad.setJWTId(JWT_ID);
        jwtPayoad.setClaimName(CLAIM_NAME);
        jwtPayoad.setClaimMap(claimMap(loginUserInfo));

        return jwtPayoad;
    }

    @Override
    public String createToken(String oaCode) {
        JwtUitls.JwtHeader jwtHeader = buildJwtHeader();

        Date now = new Date();
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setOaCode(oaCode);
        String loginId = StringUtils.uuid();
        loginUserInfo.setLoginId(loginId);
        JwtUitls.JwtPayoad jwtPayoad = buildJwtPayload(now, loginUserInfo);

        // 生成盐，过期时间和必须token过期时间一致
        String salt = genSalt(loginUserInfo);
        String token = JwtUitls.createToken(jwtHeader, jwtPayoad, salt);

        return token;
    }

    public static void main(String[] args) {
        UserLoginServiceImpl loginService = new UserLoginServiceImpl();
        JwtUitls.JwtHeader jwtHeader = loginService.buildJwtHeader();
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setOaCode("Jack");
        loginUserInfo.setLoginId(StringUtils.uuid());
        JwtUitls.JwtPayoad jwtPayoad = loginService.buildJwtPayload(new Date(), loginUserInfo);

        JwtUitls.createToken(jwtHeader, jwtPayoad, "123456");
    }

    @Override
    public LoginUserInfo queryLoginUserInfo(String oaCode) {
        return null;
    }

    private LoginUserInfo claimMap2LoginUserInfo(Map<String, Object> claimMap) {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setOaCode((String) claimMap.get(LoginUserInfo.OA_CODE));
        loginUserInfo.setStauts((Integer) claimMap.get(LoginUserInfo.STATUS));
        loginUserInfo.setLoginId((String) claimMap.get(LoginUserInfo.LOGIN_ID));

        return loginUserInfo;
    }

    @Override
    public LoginUserInfo parseLoginUserInfo(String token) {
        Map<String, Object> claimMap = JwtUitls.getClaimMap(token, CLAIM_NAME);

        return claimMap2LoginUserInfo(claimMap);
    }

    @Override
    public Cookie createTokenCookie(String token) {
        Cookie cookie = CookieUtils.setCookie(TOKEN_NAME, token, -1);

        return cookie;
    }
}
