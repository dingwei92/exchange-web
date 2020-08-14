package com.topsci.qh.webmanagement.Service.Normal;

import com.alibaba.fastjson.JSON;
import com.topsci.qh.webmanagement.Pojo.ServerMessageDetail;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.Constants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzw.
 * 16-7-27
 */
@Service
@SuppressWarnings("unchecked")
public class SystemMonitorService extends BasicService {
    public List<ServerMessageDetail> getDetails()
    {
       /* String hql = "from ServerMessageDetail smd order by smd.lastdate desc";
        return findPageByHQL(hql,0, Constants.PUBLIC_PAGE_SIZE);*/
       return null;
    }

    public String getDetailsJson()
    {
        String json;
        List<ServerMessageDetail> list = getDetails();
        json = JSON.toJSONString(list);
        return "{\"result\":"+json+"}";
    }
}
