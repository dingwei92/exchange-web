package com.topsci.qh.webmanagement.Service.System;

import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Pojo.WebUsers;
import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Service.Normal.FunctionService;
import com.topsci.qh.webmanagement.Service.Normal.UserService;
import com.topsci.qh.webmanagement.Tools.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by lzw.
 * 16-6-21
 */

/**
 * 登陆相关的服务类
 */
@Service
public class LoginService {

    private static Logger logger = LoggerFactory.getLogger(LoginService.class);
    @Resource
    private SessionManager sessionManager;
    @Resource
    private UserService userservice;
    @Resource
    private MD5Tool md5Tool;
    @Resource
    private FunctionService functionService;
    @Resource
    private Config config;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 检查登陆信息
     * @param username 用户名
     * @param password 密码(MD5加密后的)
     * @return 如果登陆正确则返回UUID，否则返回null
     */
    public Map<String,String> checkUser(String username,String password,boolean ssoUser)
    {
        Map<String,String> result;
        WebUsers user;
        if(Constants.INIT_USERNAME.equals(username) && md5Tool.getDigestPassword(Constants.INIT_PASSWORD).equals(password))
        {
            user = userservice.createSuperUser();
        }
        else
        {
            user = userservice.getUserByLoginName(username,ssoUser);
        }
        if(user == null)
        {
            return null;
        }
        else
        {
            if(user.getPasswd().equals(password))
            {
                result = new HashMap<>();
                result.put("uuid",user.getUuid());
                result.put("username",user.getUserName());
                result.put("loginname",user.getLoginName());
                return result;
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * 存储用户信息至cookie
     * @param username
     * @param password
     * @param path
     * @param response
     */
    public void rememberMe(String username,String password,String path,HttpServletResponse response)
    {
        //记忆一周
        int time = 60 * 60 *24 * 7;

        //添加用户名cookie
        Cookie cookie = new Cookie(Constants.COOKIE_USER,username);
        cookie.setMaxAge(time);
        cookie.setPath(path);
        response.addCookie(cookie);

        //添加密码cookie
        cookie = new Cookie(Constants.COOKIE_PASS, EncryptTool.encrypt(password));
        cookie.setMaxAge(time);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * 检查是否记忆过用户名密码
     * @param request
     * @return
     */
    public String[] remembered(HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();
        if(cookies == null)
        {
            return null;
        }
        String username=null;
        String password=null;
        for(Cookie cookie : cookies)
        {
            if(cookie.getName().equals(Constants.COOKIE_USER))
            {
                username = cookie.getValue();
            }
            if(cookie.getName().equals(Constants.COOKIE_PASS))
            {
                password = cookie.getValue();
            }
        }
//        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
//        {
//            return false;
//        }
//        else
//        {
//            Map<String,String> result = checkUser(username,password);
//            if(result == null)
//            {
//                return false;
//            }
//            else
//            {
//                sessionManager.setSessionAttr(request,Constants.USERID,result.get("uuid"));
//                return true;
//            }
//        }
        if(StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
            return new String[]{username, EncryptTool.decrypt(password)};
        }
        else
        {
            return null;
        }
    }

    public ModelAndView doLogin(HttpServletRequest request,String uuid,String username,String loginname,boolean log,boolean superuser)
    {
        ModelAndView model = new ModelAndView(new RedirectView("index.do",true,false,false));
        sessionManager.login(request, uuid, username,loginname,superuser);
        if(log) {
            userservice.loginLog(uuid);
        }
        return model;
    }

    public String[] ssoCheck(String sessionid)
    {
        String username = "";
        String password = "";
        String param = "";
        String result = "";
        try {
            String key = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
            String systemid = config.getSso_id() ;
            param = AESEncryptTool.encrypt(sessionid + "_"+systemid, key) + key;
            String authurl = config.getSso_auth_path() ;

            try {
                URI uri = new URI(authurl);
                SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
                ClientHttpRequest chr = schr.createRequest(uri, HttpMethod.POST);
                chr.getHeaders().setContentType(MediaType.TEXT_PLAIN);
                chr.getBody().write(param.getBytes());
                //chr.getBody().write(param.getBytes());//body中设置请求参数
                ClientHttpResponse res = chr.execute();
                InputStream is = res.getBody(); //获得返回数据,注意这里是个流
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String str = "";
                while((str = br.readLine())!=null){
                    result = result+str;
                }

            } catch (Exception e1) {
                logger.error("获取SSO系统请求返回值错误",e1);
            }
            key = result.substring(result.length()-16,result.length());
            String data = result.substring(0,result.length()-16);
            String jsons = AESEncryptTool.decrypt(data,key);
            JSONObject json = JSONObject.parseObject(jsons);
            String error = json.getString("error");
            if(StringUtils.isNotEmpty(error))
            {
                return new String[]{username,password};
            }
            username = json.getString("login_name");
            password = json.getString("password");
        }
        catch (Exception ex)
        {
            logger.error("sso登陆验证错误, sessionid:{}, param:{}, result:{}",sessionid,param,result);
        }
        return new String[]{username,password};
    }

    public boolean userFaultPasswordCheck(String username){
        Integer times = (Integer) redisTemplate.opsForValue().get(Constants.REDIS_KEY_FAULT_PASSWORD+username);
        return times == null || times.intValue() < 3;
    }

    public void userFaultPassword(String username)
    {
        Integer times = (Integer) redisTemplate.opsForValue().get(Constants.REDIS_KEY_FAULT_PASSWORD+username);
        if(times == null)
        {
            times = 0;
        }
        times++;
        redisTemplate.opsForValue().set(Constants.REDIS_KEY_FAULT_PASSWORD+username,times,2, TimeUnit.HOURS);
    }

    public void unlockFaultPassword(String username){
        redisTemplate.delete(Constants.REDIS_KEY_FAULT_PASSWORD+username);
    }
}
