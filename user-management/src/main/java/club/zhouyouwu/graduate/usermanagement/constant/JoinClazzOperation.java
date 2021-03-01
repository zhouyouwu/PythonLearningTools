package club.zhouyouwu.graduate.usermanagement.constant;

public enum JoinClazzOperation {
    INVITE(1,"邀请"), //老师邀请
    PASS(3, "通过"), //学生|老师通过
    APPLICATION(2, "申请"), //学生申请
    REFUSE(-1, "拒绝"),//学生|老师拒绝
    UNDEFINE(0, "未定义");

    private int value;
    private String description;

    JoinClazzOperation(int i, String description) {
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static JoinClazzOperation getOperation(int value){
        for(JoinClazzOperation operation : values()){
            if(operation.value == value){
                return operation;
            }
        }
        return UNDEFINE;
    }
}
