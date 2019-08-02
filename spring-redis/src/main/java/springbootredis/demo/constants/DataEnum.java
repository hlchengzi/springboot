package springbootredis.demo.constants;

/**
 * @ClassName DataEnum
 * @Description: 数据枚举
 * @author: QIJJ
 * @since: 2018年8月28日 下午3:16:19
 */
public enum DataEnum {

    /********************************************公共错误码**************************************************/
    SUCCESS(200, "请求成功"),
    FAIL(999, "系统异常"),

    ORIGIN_MYSQL(301,"数据来自MYSQL"),
    ORIGIN_REDIS(302,"数据来自REDIS"),

    USER_IS_NOT_EXIST(401,"查找用户不存在"),
    RUN_TIME_EXCEPTION(402,"超时，重复领取奖励"),



    NOTHING(0000, "结束符");

    private Integer code;

    private String msg;

    /**
     * StatusInfo.
     */
    private DataEnum() {
    }

    /**
     * DataEnum.
     *
     * @param code
     * @param msg
     */
    private DataEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {

        return SUCCESS.getCode().equals(code);
    }

}
