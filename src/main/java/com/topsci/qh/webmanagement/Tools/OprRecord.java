package com.topsci.qh.webmanagement.Tools;

import com.topsci.qh.webmanagement.Service.Normal.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lzw.
 * 16-6-25
 */
@Component
public class OprRecord {
    @Resource
    private SessionManager sessionManager;
    @Resource
    private UserService userService;

    public<T> void logOpr(T newdata, T olddate,String description,HttpServletRequest request)
    {
       /* WebLog log = new WebLog();
        if(newdata != null)
        {
            log.setDataNew(JSON.toJSONString(newdata));
        }
        if(olddate!=null)
        {
            log.setDataOld(JSON.toJSONString(olddate));
        }
        log.setOperationContent(description);
        log.setOperationTime(new Timestamp(new Date().getTime()));
        if(Constants.INIT_ID.equals(sessionManager.getSessionAttr(request, Constants.USERID)))
        {
            log.setLoginName(Constants.INIT_USERNAME);
            log.setUserUuid(Constants.INIT_ID);
            log.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
            session.save(log);
        }
        else {
            WebUsers user = userService.getUser(sessionManager.getSessionAttr(request, Constants.USERID));
            log.setLoginName(user.getLoginName());
            log.setUserUuid(user.getUuid());
            log.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
            session.save(log);
        }*/
    }
}
