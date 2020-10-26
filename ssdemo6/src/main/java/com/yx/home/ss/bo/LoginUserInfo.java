package com.yx.home.ss.bo;

public class LoginUserInfo {
    public static final String OA_CODE = "oa_code";

    public static final String STATUS = "status";

    public static final String LOGIN_ID = "login_id";

    private String oaCode;

    private String password;

    private Integer stauts;

    private String loginId;

    public String getOaCode() {
        return oaCode;
    }

    public void setOaCode(String oaCode) {
        this.oaCode = oaCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStauts() {
        return stauts;
    }

    public void setStauts(Integer stauts) {
        this.stauts = stauts;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
}
