package com.hiveview.base.util;

/**
 *
 */
public class SmsConstants {

    /**
     * 验证码时间key
     */
    public final static String SMS_TIME = "smsTime";


    public final static String SMS_TIME_MESSAGE = "短信发送太频繁";

    public final static String SMS_MESSAGE = "【教学助手】（x）验证码";

    public final static String SMS_MESSAGE_ERROR = "发送验证码短信失败";

    public final static String MOBILE_REPATE = "手机号已注册";

    public final static String VALID_CODE_ERROR = "验证码不正确";

    /**
     * 短信发送状态
     */
    public final static Integer STATUS_SECCESS = 100;
    public final static String STATUS_SECCESS_NAME = "全部成功";
    public final static Integer STATUS_PARAM_ERROR = 101;
    public final static String STATUS_PARAM_ERROR_NAME = "参数错误";
    public final static Integer STATUS_MOBILE_ERROR = 102;
    public final static String STATUS_MOBILE_ERROR_NAME = "号码错误";
    public final static Integer STATUS_TODAY_COUNT_NOT_ENOUGH = 103;
    public final static String STATUS_TODAY_COUNT_NOT_ENOUGH_NAME = "号当日余量不足";
    public final static Integer STATUS_POST_OVER_TIME = 104;
    public final static String STATUS_POST_OVER_TIME_NAME = "请求超时";
    public final static Integer STATUS_USER_COUNT_NOT_ENOUGH = 105;
    public final static String STATUS_USER_COUNT_NOT_ENOUGH_NAME = "用户余量不足";
    public final static Integer STATUS_USER_ERROR = 106;
    public final static String STATUS_USER_ERROR_NAME = "非法用户";
    public final static Integer STATUS_MOBILE_COUNT_VOER = 107;
    public final static String STATUS_MOBILE_COUNT_VOER_NAME = "提交号码超限";
    public final static Integer STATUS_SIGN_ERROR = 111;
    public final static String STATUS_SIGN_ERROR_NAME = "签名不合法";
    public final static Integer STATUS_CONTENT_OVER = 120;
    public final static String STATUS_CONTENT_OVER_NAME = "内容长度超长，请不要超过500个字";
    public final static Integer STATUS_CONTENT_ERROR = 121;
    public final static String STATUS_CONTENT_ERROR_NAME = "内容中有屏蔽词";
    public final static Integer STATUS_IP_ERROR = 131;
    public final static String STATUS_IP_ERROR_NAME = "IP非法";
}
