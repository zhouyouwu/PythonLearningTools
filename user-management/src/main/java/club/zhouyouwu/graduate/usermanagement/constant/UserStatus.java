package club.zhouyouwu.graduate.usermanagement.constant;

public enum UserStatus {
    UNDEFINE;

    private String statusCode;
    private String msg;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
