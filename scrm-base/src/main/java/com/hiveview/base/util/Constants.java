package com.hiveview.base.util;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public final class Constants {

    /**
     * 管道符号
     */
    public final static String BAR = "|";

    /**
     * 下划线符号
     */
    public final static String UNDERLINE = "_";

    public static final String PASSWORD_SALT = "7f03519ef84128e7bdc9a704d0c9458e";

    /**
     * 允许访问的url
     */
    public static final String PERMIT_URL = "permitUrl";

    /**
     * 禁止访问的URl
     */
    public static final String FORBID_URL = "forbidUrl";

    /**
     * 未登录roleID
     */
    public final static Long NOT_LOGIN_ROLE_ID = 1L;

    /**
     * 已登录roleID
     */
    public final static Long LOGIN_IN_ROLE_ID = 2L;

    /**
     * 一分钟毫秒数
     */
    public final static Long ONE_MINUTE_MILLISECOND = TimeUnit.MINUTES.toMillis(1);

    /**
     * 分页默认显示10条
     */
    public static final Integer PAGE_COUNT = 5;

    /**
     * 章节 资源类型
     */
    public static final Integer RESOURCE_CHAPTER_TYPE = 1;

    /**
     * 课程 资源类型
     */
    public static final Integer RESOURCE_LESSON_TYPE = 2;

    /**
     * 课时 资源类型
     */
    public static final Integer RESOURCE_CLASS_TYPE = 3;

    /**
     * 环节 资源类型
     */
    public static final Integer RESOURCE_STEP_TYPE = 4;

    /**
     * 父资源/源资源
     */
    public static final Long RESOURCE_ROOT = -1L;

    /**
     * 生成验证码的最大数量
     */
    public static final Integer PICTURE_CODE_RANDOM_MAX = 9999;

    /**
     * redis 验证码数据存储前缀
     */
    public static final String PICTURE_CODE = "pictureCode:";


    /**
     * 二维码类型
     *
     * * 1. 个人中心收件人
     * 2. 商城搜索
     * 3. 发票信息
     */
    public static final int RECEIVER_QRCODE = 1;

    public static final int SEARCH_QRCODE = 2;

    public static final int INVOICE_QRCODE = 3;


    /**
     * 后续需要移动配置文件
     */
    /**
     * MD5 KEY
     */
    //public static final String HIVE_PARTNER_KEY = "31871fa18f49742f95295ef7fe5d3550"; //3773434fec2587bcbaad2bfe0220679f

    /**
     * 支付回调URL KEY
     */
    //public static final String HIVE_PAYMENT_NOTIFY_KEY = "3773434fec2587bcbaad2bfe0220679f";

    /**
     * 支付退款KEY
     */
    //public static final String HIVE_PAY_KEY = "31871fa18f49742f95295ef7fe5d3550"; //14432604858cb9aa02bc48c7e79443b7


    /**
     * 用户中心域名
     */
    //public static final String PASSPORT_SERVICE = PropertiesUtil.getString("service.passport");
    /**
     * 线上域名
     */
    //public static final String PASSPORT_SERVICE = "http://best.passport.domybox.local/";
    /**
     * 测试域名
     */
    //public static final String PASSPORT_SERVICE_TEST = "http://passport.pthv.gitv.tv/";

    /**
     * 用户中心查询用户接口
     */
    //public static final String USER_API = PASSPORT_SERVICE_TEST  + "api/user/getUserInfo.json" ;

    /**
     * 关联ID
     */
    //public static final String HIVE_PARTNER_ID = "hiveview";

    /**
     * 测试退款接口
     */
    //public static final String REFUND_PAYMENT = "http://test.pay.pthv.gitv.tv/trade/unified/refund.json";

    /**
     * 测试退款接口
     */
    //public static final String REFUND_PAYMENT_TEST = "http://test.pay.pthv.gitv.tv/trade/unified/refund.json";


    /**
     * 退款状态
     */
    public static final int REFUND_SUCCESS_STATUS = 4;

    public static final int REFUND_FAILED_STATUS = 2;

    /**
     * 接口返回成功状态
     */
    public static final String REFUND_API_RETURN_SUCCESS = "000";

    /**
     *矩阵定义
     */
    public static final int MATRIX_IMAGE_TYPE = 1;
    /**
     * VIDEO
     */
    public static final int MATRIX_VIDEO_TYPE = 2;

    /*token请求参数名*/
    public static final String TOKEN_PARAMETER = "token";

    public static final String REQUIRED_PARAMETER = "params";

    public static final String SIGN_PARAMETER = "sign";

    /*userid请求参数名*/
    public static final String USERID_PARAMETER = "userid";



    /*不限制时间*/
    public static final int REDIS_IME_INFINITY = 0;
    /**
     * 用户LIMIT 10DAY
     */
    public static final int REDIS_USER_TIME_INFINITY = 3600 * 240;
    /**
     * TOKEN有效期
     */
    public static final int REDIS_TOKEN_TIME_INFINITY = 3600 * 240;


    /**
     * 地址界面SESSION TOKEN有效期
     * 半小时
     */
    public static final int REDIS_SESSION_TOKEN_TIME_INFINITY = 1800;


    /**
     * 支付
     */
    /**
     * 支付成功
     */
    public static final int PAY_SUCCESS = 2;

    /**
     * 返回APP的字符串
     */
    public static final int NETTY_OP_SUCESS = 0;

    public static final int NETTY_OP_FAILED = -1;

    public static final String PAY_SUCCESS_STR = "PAY_SUCCESS";

    public static final String ADD_RECEIVER_SUCCESS_STR = "ADD_RECEIVER_SUCCESS";

    public static final String ADD_RECEIVER_FAILED_STR = "ADD_RECEIVER_FAILED";

    public static final String UPDATE_RECEIVER_SUCCESS_STR = "UPDATE_RECEIVER_SUCCESS";

    public static final String UPDATE_RECEIVER_FAILED_STR = "UPDATE_RECEIVER_FAILED";


    /**
     * 支付
     */
    public static final int PAY_ACTION_TYPE = 1;

    /**
     * 增加收件人
     */
    public static final int ADD_ADDRESS_ACTION_TYPE = 2;

    /**
     * 修改收件人
     */
    public static final int MODIFY_ADDRESS_ACTION_TYPE = 3;



    /**
     * 订单相关
     *
     */
    /**
     * 订单未支付关闭订单时间
     */
    public static final int ORDER_NOPAY_TIMEOUT = 2;//(2*60*60*1000); //2个小时

    /**
     * 订单类型单独
     */
    public static final int SINGLE_ORDER = 1;

    /**
     * 订单类型合并
     */
    public static final int MERGER_ORDER = 2;

    /**
     * 未知订单
     */
    public static final int UNKNOW_ORDER = 0;

    /**
     * 售后退货款
     */
    public static final int UNSERVICE_TYPE = 0;

    public static final int REFUND_TYPE = 1;

    /**
     * 售后换货
     */
    public static final int EXCHANGE_TYPE = 2;


    /**
     * 快递消息提供方定义
     *
     */
    public static final int KD100_TYPE = 2;

    /** 最大收藏商品数 */
    public static final Integer MAX_FAVORITE_COUNT = 100;

    /**
     * 购物车
     */
    /** 超时时间 */
    public static final int TIMEOUT = 604800; //7*24*3600 7天时间

    /** 最大数量 */
    public static final Integer MAX_QUANTITY = 1000;

    /** 最大购物车项数量 */
    public static final Integer MAX_CART_ITEM_COUNT = 50;

    /****************************
     * 收件人
     ****************************/
    public static final int MAX_RECEIVER_COUNT = 50;

    /***************************
     * 退款
     **************************/
    /**
     * SKU单件退款
     */
    public static final int REFUND_SKU = 1;

    /**
     * 订单退款
     */
    public static final int REFUND_ORDER = 2;

    /**
     * 退款状态(处理中/退款中(确认|家视)/已退款)/申请关闭.
     */
    public static final int REFUND_PROCESS = 1;
    /**
     * 退款中-商户
     */
    public static final int REFUND_GOING_SELLER = 2;
    /**
     * 退款中-家视
     */
    public static final int REFUND_GOING_HIVEVIEW = 3;
    /**
     * 已退款
     */
    public static final int REFUND_SUCCESS = 4;
    /**
     * 申请关闭
     */
    public static final int REFUND_CLOSE = 5;

    /**
     * 处理中（待发货-商户确认) 新增退款状态为待发货发生退款后进入这个状态,此状态只有一个允许退款,
     * 即:如果商户已经发货,哪么该退款只能在这个状态wait,直到买家寄回给商户,商户再点允许退款.如果没发货,商户直接确认
     */
    public static final int REFUND_SHIPMENT_SELLER = 6;


    /**
     * 换货单(处理中)
     */
    public static final int EXCHANGE_PROCESS = 1;

    /**
     * 换货单(已处理)
     */
    public static final int EXCHANGE_SUCCESS = 2;

    /**
     * 换货单(申请关闭)
     */
    public static final int EXCHANGE_CLOSE = 3;


    /**
     * 发票处理状态(未处理)
     */
    public static final int INVOICE_PROCESS = 1;

    /**
     * 发票处理状态(已处理)
     */
    public static final int INVOICE_SUCCESS = 2;



    /**
     * KDNIAO
    物流状态: 0-无轨迹，1-已揽收，2-在途中 201-到达派件城市，3-签收,4-问题件
    */
    public static final String NO_TRACK_KDNIAO = "0";

    public static final String EMBRACE_KDNIAO = "1";

    public static final String ON_WAY_KDNIAO  = "2";

    public static final String ARRIVED_CITY_KDNIAO  = "201";

    public static final String RECEIVED_KDNIAO  = "3";

    public static final String QUESTION_KDNIAO  = "4";

    /**
     * KD100
     * 物流状态
     *  0：在途，即货物处于运输过程中；
     1：揽件，货物已由快递公司揽收并且产生了第一条跟踪信息；
     2：疑难，货物寄送过程出了问题；
     3：签收，收件人已签收；
     4：退签，即货物由于用户拒签、超区等原因退回，而且发件人已经签收；
     5：派件，即快递正在进行同城派件；
     6：退回，货物正处于退回发件人的途中；
     */
    public static final String ON_WAY_KD100 = "0";

    public static final String EMBRACE_KD100 = "1";

    public static final String QUESTION_KD100 = "2";

    public static final String RECEIVED_KD100 = "3";

    public static final String BACK_SIGN_KD100 = "4";

    public static final String ARRIVED_CITY_KD100 = "5";

    public static final String REBACK_KD100 = "6";
    /**
     * 0：物流单暂无结果，
     1：查询成功，
     2：接口出现异常，
     */
    public static final String DELIVERY_NO_RESULT_KD100 = "0";

    public static final String DELIVERY_QUERY_SUCCESS_KD100 = "1";

    public static final String DELIVERY_API_ERROR_KD100 = "2";


    /**
     * 商品上架状态
     */
    /**
     * 未知状态
     */
    public static final int GOODS_UNKNOW_STATUS = 0;

    /**
     * 商品上架
     */
    public static final int GOODS_ONLINE_STATUS = 1;

    /**
     * 商品下架
     */
    public static final int GOODS_OFFLINE_STATUS = 2;

    /**
     * 专题上架状态
     */
    /**
     * 状态状态(0:默认下线状态,1:上线状态).
     */
    public static final int SPECIAL_ONLINE_STATUS = 0;

    public static final int SPECIAL_OFFLINE_STATUS = 1;

}
