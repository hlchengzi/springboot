package springbootredis.demo.constants;



import springbootredis.demo.util.DateUtil;
import springbootredis.demo.util.EnumUtil;

import java.io.Serializable;


/**
 * @param <T>
 * @ClassName ResponseBean

 * @author: xcxu
 * @since: 2018年8月28日 下午2:03:17
 */
public class ResponseBean<T> implements Serializable {

    /**
     * serialVersionUID:1L
     */
    private static final long serialVersionUID = 1L;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 返回编码
     */
    private Integer return_code;

    /**
     * 返回消息
     */
    private String return_msg;

    /**
     * 返回扩展信息
     */
    private String return_ext;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 时间
     */
    private String dateTime;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getReturn_code() {
        return return_code;
    }

    public void setReturn_code(Integer return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getReturn_ext() {
        return return_ext;
    }

    public void setReturn_ext(String return_ext) {
        this.return_ext = return_ext;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * ResponseBean.
     */
    public ResponseBean() {
        super();
    }

    /**
     * 参数为数据，直接判定成功返回
     *
     * @param data
     */
    public ResponseBean(T data) {
        super();
        this.success = true;
        this.return_code = 200;
        this.return_msg = "请求成功";
        this.data = data;
        this.dateTime = DateUtil.getCurrDates();
    }

    /**
     * 参数为编码，根据编码判断返回(适用于增删改操作).
     *
     * @param code
     */
    public ResponseBean(Integer code) {
        if (200 == code) {
            this.success = true;
            this.return_code = code;
            this.return_msg = "请求成功";
            this.dateTime = DateUtil.getCurrDates();
        } else {
            this.success = false;
            this.return_code = code;
            this.return_msg = EnumUtil.getMsgEnumByCode(code).getMsg();
            this.dateTime = DateUtil.getCurrDates();
        }
    }

    /**
     * 参数为编码/数据，根据编码判断返回(适用于查询操作).
     *
     * @param code
     * @param data
     */
    public ResponseBean(Integer code, T data) {
        if (200 == code) {
            this.success = true;
            this.return_code = code;
            this.return_msg = "请求成功";
            this.data = data;
            this.dateTime = DateUtil.getCurrDates();
        } else {
            this.success = false;
            this.return_code = code;
            this.return_msg = EnumUtil.getMsgEnumByCode(code).getMsg();
            this.data = data;
            this.dateTime = DateUtil.getCurrDates();
        }
    }

    /**
     * 参数为status，根据状态判断返回(适用于增删改操作).
     *
     * @param status
     */
    public ResponseBean(String status) {
        if ("OK".equals(status)) {
            this.success = true;
            this.return_code = 200;
            this.return_msg = "请求成功";
            this.dateTime = DateUtil.getCurrDates();
        } else if ("FAIL".equals(status)) {
            this.success = false;
            this.return_code = 999;
            this.return_msg = "系统异常";
            this.dateTime = DateUtil.getCurrDates();
        } else {
            // ...
        }
    }

    /**
     * 参数为success，根据状态判断返回(适用于增删改操作).
     *
     * @param success
     */
    public ResponseBean(boolean success) {
        super();
        if (success) {
            this.success = success;
            this.return_code = 200;
            this.return_msg = "请求成功";
            this.dateTime = DateUtil.getCurrDates();
        } else {
            this.success = false;
            this.return_code = 999;
            this.return_msg = "系统异常";
            this.dateTime = DateUtil.getCurrDates();
        }
    }

    /**
     * 参数为枚举，判断返回.
     *
     * @param dataEnum
     * @param data
     */
    public ResponseBean(DataEnum dataEnum, T data) {
        super();
        this.success = dataEnum.isSuccess();
        this.return_code = dataEnum.getCode();
        this.return_msg = dataEnum.getMsg();
        this.data = data;
        this.dateTime = DateUtil.getCurrDates();
    }


    /**
     * 完整格式数据交互(带扩展参数).
     *
     * @param success
     * @param code
     * @param ext
     * @param data
     */
    public ResponseBean(boolean success, Integer code, String ext, T data) {
        super();
        this.success = success;
        this.return_code = code;
        this.return_msg = EnumUtil.getMsgEnumByCode(code).getMsg();
        this.return_ext = ext;
        this.data = data;
        this.dateTime = DateUtil.getCurrDates();
    }

    /**
     * 完整格式数据交互(枚举条件，带扩展参数).
     *
     * @param dataEnum
     * @param ext
     * @param data
     */
    public ResponseBean(DataEnum dataEnum, String ext, T data) {
        super();
        this.success = dataEnum.isSuccess();
        this.return_code = dataEnum.getCode();
        this.return_msg = dataEnum.getMsg();
        this.return_ext = ext;
        this.data = data;
        this.dateTime = DateUtil.getCurrDates();
    }

}
