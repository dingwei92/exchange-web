package com.topsci.qh.webmanagement.Service.Normal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.topsci.qh.webmanagement.Pojo.WebMsg;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.PageInfo;

/**
 * @ClassName: MsgService  
 * @Description: TODO
 * @author tgeng
 * @date 2017-1-6 下午3:02:01  
 *
 */
@SuppressWarnings("unchecked")
@Service
public class MsgService extends BasicService {

	public List<WebMsg> getMsgByPage(PageInfo pageInfo,String search) {
		/*String hql;
        if(StringUtils.isNotEmpty(search)) {
            hql = "from WebMsg msg where (msg.theme like '%"+search+"%' or msg.content like '%"+search+"%') order by msg.createTime desc";
        }else {
            hql = "from WebMsg msg order by msg.createTime desc";
        }
        pageInfo.setTotalResult(getCount(hql));
        return findPageByHQL(hql, pageInfo);*/
        return null;
	}

	public WebMsg getMsg(String id)
    {
      /*  String hql = "from WebMsg where id = ?";
        List<WebMsg> lists = findByHQL(hql,Integer.parseInt(id));
        if(lists != null && lists.size()>0)
        {
            return lists.get(0);
        }
        return new WebMsg();*/
        return null;
    }

	public String isRepeat(String content) {
		/*if(StringUtils.isNotEmpty(content))
        {
            String hql = "";
            int result;
            hql = "from WebMsg msg where msg.content = ?";
            result = getCount(hql,content);

            if(result > 0){
                return "{\"result\":\"false\"}";
            }else{
                return "{\"result\":\"true\"}";
            }
        }
        return "{\"result\":\"false\"}";*/
        return null;
	}

	public void saveMsg(WebMsg msg, HttpServletRequest request) {
	/*	oprRecord.logOpr(msg,null,"发布消息",request);
		if(msg.getId()!=0)
        {
            update(msg);
        }
        else {
            save(msg);
        }*/
	}

    public void deleteMsg(String id,HttpServletRequest request)
    {
     /*   String hql = "delete WebMsg where id = ?";
        execUpdateHQL(hql,Integer.parseInt(id));
        oprRecord.logOpr(null,null,"删除消息"+id,request);*/
    }
}
