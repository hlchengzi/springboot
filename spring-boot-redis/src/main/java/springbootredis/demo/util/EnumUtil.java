package springbootredis.demo.util;


import springbootredis.demo.constants.DataEnum;

/**
 * @ClassName EnumUtil
 * @author: xcxu
 * @since: 2018年8月29日 上午11:24:04
 */
public class EnumUtil {

    /**
     * 通过编码获取枚举
     */
    public static DataEnum getMsgEnumByCode(Integer code) {
        for (DataEnum dataEnum : DataEnum.values()) {
            if (code.equals(dataEnum.getCode())) {

                return dataEnum;
            }
        }
        return null;
    }

}