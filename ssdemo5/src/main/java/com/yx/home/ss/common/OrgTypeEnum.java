package com.yx.home.ss.common;

/**
 * 机构类别
 */
public enum OrgTypeEnum {
    HEAD_OFFICE("1001", "总行各厅局"),

    BRANCH_OFFICE("1002", "分行"),
    ;

    private String tcode;

    private String tname;

    OrgTypeEnum(String tcode, String tname) {
        this.tcode = tcode;
        this.tname = tname;
    }

    public String getTcode() {
        return this.tcode;
    }

    public String getTname() {
        return this.tname;
    }
}
