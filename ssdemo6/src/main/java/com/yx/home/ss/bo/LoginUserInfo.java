package com.yx.home.ss.bo;

public class LoginUserInfo {
    public static final String OA_CODE = "oa_code";

    public static final String STATUS = "status";

    public static final String LOGIN_CNT = "login_cnt";

    private String oaCode;

    private Integer stauts;

    private Integer loginCnt;

    public String getOaCode() {
        return oaCode;
    }

    public void setOaCode(String oaCode) {
        this.oaCode = oaCode;
    }

    public Integer getStauts() {
        return stauts;
    }

    public void setStauts(Integer stauts) {
        this.stauts = stauts;
    }

    public Integer getLoginCnt() {
        return loginCnt;
    }

    public void setLoginCnt(Integer loginCnt) {
        this.loginCnt = loginCnt;
    }
}
