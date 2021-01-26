package club.zhouyouwu.graduate.common.entity;


import club.zhouyouwu.graduate.common.constant.CodeMsg;

public class Result {
    private int code;
    private String msg;
    private Object result;

    //返回成功信息
    public static Result ok() {
        Result result = new Result();
        result.setCode(CodeMsg.SUCCESS.getCode());
        result.setMsg(CodeMsg.SUCCESS.getMsg());
        return result;
    }

    public static Result ok(Object object) {
        Result result = new Result();
        result.setCode(CodeMsg.SUCCESS.getCode());
        result.setMsg(CodeMsg.SUCCESS.getMsg());
        result.setResult(object);
        return result;
    }

    //返回信息失败
    public static Result failed(Object object) {
        Result result = new Result();
        result.setCode(CodeMsg.FAIL_DEFAULT.getCode());
        result.setResult(result);
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
