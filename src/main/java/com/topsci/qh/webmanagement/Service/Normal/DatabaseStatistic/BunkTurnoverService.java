package com.topsci.qh.webmanagement.Service.Normal.DatabaseStatistic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Tools.EChartsTool;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lzw on 2016/12/5.
 */
@Service
public class BunkTurnoverService extends BasicService{
    //定时统计语句
    //select DATE_FORMAT(RYRQSJ,"%Y-%m") as sj,YLJGZZJGDM,avg(SJZYTS) from XT_LEAVE_HOSPITALIZED_RECORD group by sj,YLJGZZJGDM order by sj,YLJGZZJGDM;

    private Logger logger = LoggerFactory.getLogger(ChronicDiseaseService.class);

    public Map<String,JSONObject> getData(String starttime, String endtime, String areacode)
    {
       /* Map<String,JSONObject> result = new HashMap<>();

        if(StringUtils.isEmpty(areacode))
        {
            areacode = "910000000001";
        }

        //organ_id like '"+areacode+"%' and
        String sql = "select date_month,ceiling(avg(amount)) from ST_BUNK_TURNOVER " +
                "where date_month >= ? and date_month <= ? and organ_id in " +
                "(select id from IRPT_DEPARTMENTS where upid0 = ? or upid1 = ? or upid2 = ? " +
                "or upid3 = ? or upid4 = ? or upid5 = ?)" +
                "group by date_month order by date_month;";
        List list = findBySQLMysql(sql,starttime,endtime,areacode,areacode,areacode,areacode,areacode,areacode);
        Set<String> dates = new TreeSet<>();
        for(Object obj : list)
        {
            Object[] result_col = (Object[]) obj;
            dates.add((String)result_col[0]);
        }

        //chart
        JSONObject root = new JSONObject();
        JSONArray tables = new JSONArray();

        List<Map<String,Integer>> dataarr = new ArrayList<>();
        Map<String,Integer> labeleddata = new TreeMap<>();
        for(int i = 0 ; i < list.size() ;i++)
        {
            if(i % dates.size() == 0)
            {
                labeleddata = new TreeMap<>();
            }
            labeleddata.put((String)((Object[])list.get(i))[0],Integer.valueOf(((Object[])list.get(i))[1].toString()));
            if(i % dates.size() == dates.size() -1)
            {
                dataarr.add(labeleddata);
            }
        }


        JSONObject t1 = EChartsTool.generateLineChartJson("床位平均周转时间",dataarr,
                "日期","时间/天",new ArrayList<>(dates),Arrays.asList("平均时间"));


        tables.add(t1);
        root.put("chart",tables);
        result.put("chart",root);


        List<Map<String,Integer>> dataarr1 = new ArrayList<>();
        for(int i = 0 ; i < list.size() ;i++)
        {
            labeleddata = new TreeMap<>();
            labeleddata.put("床位周转时间(天)",Integer.valueOf(((Object[])list.get(i))[1].toString()));
            dataarr1.add(labeleddata);
        }
        //table
        JSONObject tableroot = EChartsTool.generateTableJson(dataarr1,true,false, new ArrayList<>(dates),null,null,Arrays.asList(new String[]{"床位周转时间(天)"}));

        result.put("table",tableroot);

        return result;*/
        return null;
    }
}
