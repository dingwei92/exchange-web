package com.topsci.qh.webmanagement.Tools;

import com.topsci.qh.webmanagement.Resources.Constants;
import com.topsci.qh.webmanagement.Service.Normal.FunctionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lzw.
 * 16-6-21
 */

@Component
public class SessionManager {

    @Resource
    private FunctionService functionService;

    /**
     * 存入session缓存map
     * @param request
     * @param key
     * @param value
     */
    public void setCache(HttpServletRequest request,String key,Object value)
    {
        HttpSession session = request.getSession();
        Map<String,Object> cache = (Map<String,Object>)session.getAttribute(Constants.LOG_CACHE);
        if(cache == null)
        {
            cache = new HashMap<>();
        }
        cache.put(key,value);
        session.setAttribute(Constants.LOG_CACHE,cache);
    }

    /**
     * 从session缓存map中读取
     * @param request
     * @param key
     * @return
     */
    public Object getCache(HttpServletRequest request,String key)
    {
        HttpSession session = request.getSession();
        Map<String,Object> cache = (Map<String,Object>)session.getAttribute(Constants.LOG_CACHE);
        if(cache != null)
        {
            Object obj = cache.get(key);
            cache.remove(key);
            session.setAttribute(Constants.LOG_CACHE,cache);
            return obj;
        }
        else
        {
            return null;
        }
    }

    /**
     * 带有前缀key的缓存存入
     * @param request
     * @param key
     * @param value
     * @param prefix
     */
    public void setCache(HttpServletRequest request,String key,Object value,String prefix)
    {
        setCache(request,prefix+key,value);
    }

    public Object getCache(HttpServletRequest request,String key,String prefix)
    {
        return getCache(request,prefix+key);
    }

    /**
     * 存入session属性值
     * @param request
     * @param key
     * @param value
     */
    public void setSessionAttr(HttpServletRequest request, String key, Object value)
    {
        HttpSession session = request.getSession();
        session.setAttribute(key,value);
    }

    /**
     * 获取session属性值
     * @param request
     * @param key
     * @return
     */
    public String getSessionAttr(HttpServletRequest request, String key)
    {
        Object attr = request.getSession().getAttribute(key);
        if(attr != null) {
            return String.valueOf(request.getSession().getAttribute(key));
        }
        else
        {
            return null;
        }
    }

    public Object getSessionAttrObj(HttpServletRequest request,String key)
    {
        return request.getSession().getAttribute(key);
    }

    /**
     * 删除一个session属性值
     * @param request
     * @param key
     */
    public void removeSessionAttr(HttpServletRequest request,String key)
    {
        HttpSession session = request.getSession();
        session.removeAttribute(key);
    }

    /**
     * 登陆时添加用户信息至session
     * @param request
     * @param uuid
     * @param username
     */
    public void login(HttpServletRequest request,String uuid,String username,String loginname,boolean superuser)
    {
        setSessionAttr(request, Constants.USERID, uuid);
        setSessionAttr(request, Constants.USERNAME, username);
        setSessionAttr(request,Constants.LOGINNAME,loginname);
        String userid = getSessionAttr(request,Constants.USERID);
        String funcs;
        if(Constants.INIT_USERNAME.equals(getSessionAttr(request, Constants.LOGINNAME)))
        {
            funcs = functionService.getAllFuncListStr(null);
        }
        else
        {
            funcs = functionService.getAllFuncListStr(userid);
        }
        setSessionAttr(request,Constants.USERFUNCPATH,funcs);
        setSessionAttr(request,Constants.SUPERUSER,superuser);
    }

    /**
     * 退出时删除用户信息
     * @param request
     */
    public void logout(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        session.removeAttribute(Constants.USERID);
        session.removeAttribute(Constants.USERNAME);
        session.removeAttribute(Constants.LOGINNAME);
        session.removeAttribute(Constants.USERFUNCPATH);
        session.removeAttribute(Constants.SUPERUSER);
    }
}
