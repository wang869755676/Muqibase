package com.muqi.frame.project.contants;

/**
 * @author John
 * @category 通过网络访问数据，后台接口统一定义在这里
 */
public class NetWorkApi {
    /**
     * HTTP请求的IP地址
     */

    public final static String SERVER_IP = "http://47.90.16.112/evidencebased/qtuktuk/";// tuktuk服务器地址

//	public final static String SERVER_IP = "http://192.168.1.127/evidencebased/qtuktuk/";
	//public final static String SERVER_IP = "http://192.168.1.125/evidencebased/qtuktuk/";


    /******************************* 请求服务的模块名以及方法名 ******************************/

    /******************************* jiaowenhui api ******************************/
    /**
     * 获取Hx用户信息
     */
    public final static String GET_HX_USERINFOS_API = "common/list_hxusers_info";

    public final static String LOGIN_APP_API = "login/user_login";

    public final static String REGISTER_APP_API = "login/user_registed";

    public final static String SEARCH_CARS_BYKEYS_API = "appwork/seach_cars_bykeys";

    public final static String GET_USER_DETAILS_API = "common/get_userdetails";

    public final static String SAVE_USER_LOVED_API = "common/save_collection";


    /*上传本地头像图片的地址*/
    public final static String UPLOAD_AVATAR_API = "upload/uploadAvatar";

    /*上传本地车辆图片的地址*/
    public final static String UPLOAD_CAR_IMG_API = "upload/uploadfile";

    /*更新用户信息的地址*/
    public final static String UPDATE_USER_INFO_API = "common/updateUserinfos";

    /*上传车辆信息*/
    public final static String UPLOAD_NEWCAR_API = "appwork/create_newcar";

    /*上传车辆信息第二步*/
    public final static String UPLOAD_CARINFO_API = "appwork/create_car_infos";

    /*上传认证*/
    public final static String UPLOAD_AUTHEN_API = "upload/uploadConfirm";

    /*获取车辆信息*/
    public final static String GET_CARINFO_API = "appwork/get_user_cars";
    /**
     * 获取城市列表
     */
    public static final String GET_CITY_LIST = "common/list_citys";
    /**
     * 获取城市列表
     */
    public static final String GET_HOT_CITY_LIST = "common/list_hotcitys/";


    /**
     * 获取城市列表
     */
    public static final String SET_NEW_PASSWORD = "login/forgot_password";


    /**
     * 搜索城市
     */
    public static final String SEARCH_CITY_BY_KEY = "common/searchcitys_bykey";

    /*变更已发布车辆状态*/
    public static final String CHANGE_STATE_API = "appwork/change_car_state";

    /*修改车辆本身信息*/
    public static final String UPDATE_CAR_INFO_API = "appwork/update_car_infos";

    /*修改车辆的服务类型  包车或者租车*/
    public static final String UPDATE_CAR_TYPE_API = "appwork/update_car_servicetype";

    /*修改车辆城市信息*/
    public static final String UPDATE_CITY_INFO_API = "appwork/update_car_location";

    /*获取其他服务信息*/
    public static final String UPLOADE_OTHER_SERVICE = "common/list_other_service";


	/*获取语言信息*/
	public static final String UPLOADE_LANGUAGE_LIST = "common/list_languages";

    /*设置车辆休息的时间*/
    public static final String UPDATE_REST_DAYS = "appwork/set_rest_days";

    /*认证服务列表*/
    public static final String UPDATE_CONFIRM_LIST = "common/list_confirmtable";

    /*获取某项认证信息*/
    public static final String UPDATE_CONFIRM_INFO = "appwork/get_userconfirm_infos";

    /*上传行驶证件照*/
    public final static String UPLOAD_CARPAPER_API = "upload/uploadCarpaper";

    /*上传意见反馈*/
    public final static String UPLOAD_FEED_BACK = "common/app_advise";

    /*用户交易明细*/
    public final static String UPDATE_TRANSACTION_RECORDS = "order/list_transactionrecords";

    /*车辆收藏列表*/
    public final static String UPADATE_CAR_COLLECTION = "common/list_collection2";

    /*车辆的评论信息*/
    public final static String UPADATE_CAR_COMMENT = "common/get_comments";


    /*上传 获取订单*/
    public final static String UPADATE_CREATE_ORDER = "order/create_order";


    /*收款账户列表*/
    public final static String UPLOAD_BANK_LIST = "bank/list_banks";


    /*修改默认付款方式*/
    public final static String MODIFY_DEFAULT_PAY = "bank/set_defaultbank";


    /*删除账户*/
    public final static String DELETE_PAY_ACOUNT = "bank/delete_bank";


    /*删除账户*/
    public final static String COMPLAIT_REFUND = "order/add_complaint";




/*******************************订单信息**************************************/
	/*
	 旅途订单列表
	 */
	public static final String JOURNEY_ORDER_GOING = "order/get_orders";

    /*
         出租订单列表
         */
    public static final String RENT_ORDER_GOING = "order/get_seller_orders";

    /*
         订单详情order/get_depositdetail
         */
    public static final String ORDER_DETIAL = "order/get_order_byid";


    /*
         获取退还押金的信息
         */
    public static final String DESPOIT_DETIAL = "order/get_depositdetail";


    /* 买家确定退款
     */
    public static final String CONFIRM_DEPOSIT = "order/confirm_deposit";

    /**
     *  退款相关的
     */
    public static final String CONFIRM_REFUND = "order/agree_refund";

    public static final String DIS_CONFIRM_REFUND = "order/refuse_refund";


    public static final String REFUND_DETIAL= "order/get_refund";

    public static final String REFUND_RESEAON= "order/list_refunditems";


    public static final String COMFIRM_ORDER= "order/confirm_order";

    public static final String APPLY_REFUND= "order/apply_refund";

    public static final String AGREE_RETURN_DEPOSIT= "order/back_deposit";
    public static final String COMMENT_ORDER= "common/add_comment";

    public static final String ADD_PAYPAL= "bank/add_bank";
    /******************************* 一些常量信息 ******************************/
    /**
     * 请求成功
     */
    public final static String REQUEST_SUCCESS = "Success";
    /**
     * 网络断开
     */
    public final static String REQUEST_NETWORK_BREAKED = "您的网络已网络断开";
    /**
     * 请求失败
     */
    public final static String REQUEST_FAILED = "服务器连接失败,请稍后";
    /**
     * 数据解析失败
     */
    public final static String REQUEST_JSON_FAILED = "抱歉,数据解析失败,请稍后再试";
    /**
     * 请求返回值--成功
     */
    public final static int RETURN_SUCCESS = 1;
    /**
     * 请求返回值--失败
     */
    public static int RETURN_ERROR = 0;
    /**
     * 请求返回值--网络断开
     */
    public final static int RETURN_NETWORK_BREAKED = -1;
    /**
     * 请求返回值--json err
     */
    public final static int RETURN_JSON_FAILED = -2;


}
