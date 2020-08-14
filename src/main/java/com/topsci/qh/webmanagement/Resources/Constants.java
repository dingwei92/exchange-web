package com.topsci.qh.webmanagement.Resources;

import java.util.ArrayList;
import java.util.List;

import com.topsci.qh.webmanagement.WebSocket.WebsocketEndPoint;


/**
 * Created by lzw.
 * 16-6-20
 */
public class Constants {
    //用户ID
    public static final String USERID = "USERID";

    //用户名称
    public static final String USERNAME = "USERNAME";

    //用户登陆名
    public static final String LOGINNAME = "LOGINNAME";

    //用户权限
    public static final String SUPERUSER = "SUPERUSER";

    //用户有权限的功能的地址
    public static final String USERFUNCPATH = "FUNCPATH";

    //后台初始登陆用户名
    public static final String INIT_USERNAME = "webmanager321";

    //后台初始登陆密码
    public static final String INIT_PASSWORD = "godblessme123";

    public static final String INIT_ID = "0";

    //登陆地址
    public static final String LOGINPATH = "/system/login.do";

    //错误地址
    public static final String ERRORPATH = "/system/error.do";

    public static final String ACCESSDENYPATH = "/system/accessdeny.do";

    //首页地址
    public static final String INDEXPATH = "/system/index.do";

    //用户名cookie缓存key
    public static final String COOKIE_USER = "username";

    //密码cookie缓存key
    public static final String COOKIE_PASS = "password";

    //删除标志位
    public static final String DELETED = "Y";

    //未删除标志位
    public static final String UNDELETED = "N";
    
    //审核中标志位
    public static final String AUDITING = "A";
    
    //审核失败标志位
    public static final String AUDITFAIL ="F";

    //登陆时临时标志位
    public static final String LOGIN_FLAG = "LOGIN_FLAG";

    //公用页面表格长度
    public static final int PUBLIC_PAGE_SIZE = 15;

    //request当前页面参数名称
    public static final String CURRENT_PAGE = "page";

    //日志使用的缓存map
    public static final String LOG_CACHE = "LOG_CACHE";

    //错误提示颜色
    public static final String TIP_ERROR = "red";

    //正确提示颜色
    public static final String TIP_SUCCESS = "green";

    //基础数据对应接口的配置项在配置文件中的前缀
    public static final String BASE_SUFFIX = "base_";

    public static final String REDIS_KEY_FAULT_PASSWORD="fault_password_";
    public static final String REDIS_KEY_ACCESS_TIME="access_time_";
    
    //审核科室等资源的角色
    public static final String REVIEW_ROLE_UUID = "d36a46d078bf48c6ae48a4710dc03987";

    public static List sockets = new ArrayList<WebsocketEndPoint>();
}
