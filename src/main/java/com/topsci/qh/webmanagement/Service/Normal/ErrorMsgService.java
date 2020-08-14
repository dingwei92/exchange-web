package com.topsci.qh.webmanagement.Service.Normal;

import com.topsci.qh.webmanagement.Pojo.ErrorMsg7Day;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Resources.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class ErrorMsgService extends BasicService {
    private Map<String,String> errorcode;

    public ErrorMsgService()
    {
        errorcode = new HashMap<>();
        errorcode.put("1001","XML格式错误");
        errorcode.put("1002","数据为空");
        errorcode.put("1003","数据大小不符");
        errorcode.put("1004","上传数据超限");
        errorcode.put("1005","业务不合法");
        errorcode.put("1006","BODY解析错误");
        errorcode.put("1007","重复SN");
        errorcode.put("1008","重复session");
        errorcode.put("1009","请求过载");
        errorcode.put("1010","接口模板失效");
        errorcode.put("1011","主键标识错误");
        errorcode.put("2001","其他错误");
    }

    public Map<String,Object> getData(PageInfo pageInfo)
    {
        List<ErrorMsg7Day> data = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate first = today;
        String dates[] = new String[7];
        for(int i = 0 ; i < dates.length ;i++) {
            dates[dates.length-i-1] = today.getYear() + "-" + (today.getMonth().getValue()<10?"0"+today.getMonth().getValue():today.getMonth().getValue())
                    + "-" + (today.getDayOfMonth()<10?"0"+today.getDayOfMonth():today.getDayOfMonth());
            today = today.minusDays(1);
        }
        String tablename = "";
        if(first.getMonth() != today.getMonth())
        {
            tablename = "(select MSGTYPE from SERVER_MESSAGE_ERROR union select MSGTYPE from SERVER_M_E_"+
                    (today.getYear()+"")+(today.getMonth().getValue()<10?"0"+today.getMonth().getValue():today.getMonth().getValue())+")";
        }
        else{
            tablename = "SERVER_MESSAGE_ERROR";
        }
        String sql = "select sc.SERVER_NAME,bs.SYSTEM_NAME,t10.* from (" +
                "select nvl(t1.MSGTYPE,nvl(t2.MSGTYPE,nvl(t3.MSGTYPE,nvl(t4.MSGTYPE,nvl(t5.MSGTYPE,nvl(t6.MSGTYPE,nvl(t7.MSGTYPE,null )) ))))) msgtype, " +
                "nvl(t1.d1,0),nvl(t2.d2,0),nvl(t3.d3,0),nvl(t4.d4,0),nvl(t5.d5,0),nvl(t6.d6,0),nvl(t7.d7,0) " +
                "from ( " +
                "select " +
                "err.MSGTYPE, " +
                "count(*) d1 " +
                "from  "+tablename+" err " +
                "where LASTDATE like '"+dates[0]+"%' " +
                "group by MSGTYPE " +
                ")t1 " +
                "full outer join ( " +
                "select " +
                "err.MSGTYPE, " +
                "count(*) d2 " +
                "from  "+tablename+" err " +
                "where LASTDATE like '"+dates[1]+"%' " +
                "group by MSGTYPE " +
                ")t2 on t1.MSGTYPE = t2.MSGTYPE " +
                "full outer join ( " +
                "select " +
                "err.MSGTYPE, " +
                "count(*) d3 " +
                "from  "+tablename+" err " +
                "where LASTDATE like '"+dates[2]+"%' " +
                "group by MSGTYPE " +
                ")t3 on t2.MSGTYPE = t3.MSGTYPE " +
                "full outer join ( " +
                "select " +
                "err.MSGTYPE, " +
                "count(*) d4 " +
                "from  "+tablename+" err " +
                "where LASTDATE like '"+dates[3]+"%' " +
                "group by MSGTYPE " +
                ")t4 on t3.MSGTYPE = t4.MSGTYPE " +
                "full outer join ( " +
                "select " +
                "err.MSGTYPE, " +
                "count(*) d5 " +
                "from  "+tablename+" err " +
                "where LASTDATE like '"+dates[4]+"%' " +
                "group by MSGTYPE " +
                ")t5 on t4.MSGTYPE = t5.MSGTYPE " +
                "full outer join ( " +
                "select " +
                "err.MSGTYPE, " +
                "count(*) d6 " +
                "from  "+tablename+" err " +
                "where LASTDATE like '"+dates[5]+"%' " +
                "group by MSGTYPE " +
                ")t6 on t5.MSGTYPE = t6.MSGTYPE " +
                "full outer join ( " +
                "select " +
                "err.MSGTYPE, " +
                "count(*) d7 " +
                "from  "+tablename+" err " +
                "where LASTDATE like '"+dates[6]+"%' " +
                "group by MSGTYPE " +
                ")t7 on t6.MSGTYPE = t7.MSGTYPE " +
                ")t10 left join SERVER_CATALOG sc on t10.msgtype = sc.SERVER_SHORT " +
                "left join BUSINESS_SYSTEM bs on sc.BUSINESS_SYSTEM_ID = bs.ID";

       /* List list = findPageBySQL(sql,pageInfo);
        pageInfo.setTotalResult(getSQLCount(sql));
        for(Object obj : list)
        {
            Object[] objs = (Object[])obj;
            ErrorMsg7Day err = new ErrorMsg7Day();
            err.setMsgType((String)objs[2]);
            err.setMsgTypeName((String)objs[0]);
            err.setSystemName((String)objs[1]);
            err.setD1(objs[3]+"");
            err.setD2(objs[4]+"");
            err.setD3(objs[5]+"");
            err.setD4(objs[6]+"");
            err.setD5(objs[7]+"");
            err.setD6(objs[8]+"");
            err.setD7(objs[9]+"");
            data.add(err);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("data",data);
        result.put("date", Arrays.asList(dates));
        return result;*/
        return null;
    }

    public List<List<Object>> getDetail(String msgtype,String date,PageInfo pageInfo)
    {
        String tablename = "SERVER_MESSAGE_ERROR";
        if(StringUtils.isNotEmpty(date))
        {
            String month = date.substring(5,7);
            LocalDate today = LocalDate.now();
            if(Integer.parseInt(month) != today.getMonth().getValue())
            {
                tablename = "(select LASTDATE,ERROR_CODE,SESSIONID,MSGSN from SERVER_M_E"+date.substring(0,4)+month+" " +
                        "union select LASTDATE,ERROR_CODE,SESSIONID,MSGSN from SERVER_MESSAGE_ERROR )t1";
            }
        }
        String sql = "select LASTDATE,ERROR_CODE,SESSIONID,MSGSN from "+tablename+
                " where MSGTYPE='"+msgtype+"' and LASTDATE like '"+date+"%' order by LASTDATE desc";
        /*List list = findPageBySQL(sql,pageInfo);
        pageInfo.setTotalResult(getSQLCount(sql));
        List<List<Object>> result = new ArrayList<>();
        for(Object obj : list)
        {
            Object[] objs = (Object[])obj;
            objs[1] = errorcode.get(objs[1]);
            result.add(Arrays.asList((Object[])obj));
        }
        return result;*/
        return null;
    }
}
