package com.yx.home.ss.form;

import javax.validation.constraints.NotEmpty;

public class UserQueryForm {
    @NotEmpty(message = "OA不能为空")
    private String oaCode;

    private String name;

    public String getOaCode() {
        return oaCode;
    }

    public void setOaCode(String oaCode) {
        this.oaCode = oaCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
