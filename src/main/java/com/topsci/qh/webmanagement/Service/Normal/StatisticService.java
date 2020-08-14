package com.topsci.qh.webmanagement.Service.Normal;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Tools.EChartsTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lzw.
 * 16-11-30
 */
@Service
public class StatisticService extends BasicService {
    private static Logger logger = LoggerFactory.getLogger(StatisticService.class);

    public Map<String,JSONObject> getTestData(String starttime,String endtime)
    {
       /* Map<String,JSONObject> result = new HashMap<>();

        String sql = "select date_month,disease_code,amount " +
                "from ST_TEST1 " +
                "where date_month >= ? and date_month <= ?" +
                "order by disease_code,date_month;";
        List list = findBySQLMysql(sql,starttime,endtime);
        Set<String> cols = new TreeSet<String>();
        Set<String> dates = new TreeSet<>();
        for(Object obj : list)
        {
            Object[] result_col = (Object[]) obj;
            cols.add((String)result_col[1]);
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
            labeleddata.put((String)((Object[])list.get(i))[1],Integer.valueOf(((Object[])list.get(i))[2].toString()));
            if(i % dates.size() == dates.size() -1)
            {

                dataarr.add(labeleddata);
            }
        }

        JSONObject t1 = EChartsTool.generateLineChartJson("慢性疾病门诊统计",dataarr,
                "日期","患者/人",new ArrayList<>(dates),new ArrayList<>(cols));
        tables.add(t1);
        root.put("chart",tables);
        result.put("chart",root);


        //table
        sql = "select sum(amount) " +
                "from ST_TEST1 " +
                "where date_month >= ? and date_month <= ? " +
                "group by date_month order by disease_code,date_month";
        list = findBySQLMysql(sql,starttime,endtime);
        List<Object> tmpdatalist = new ArrayList<>();
        for(Object obj : list)
        {
            tmpdatalist.add(((BigDecimal) obj).intValue());
        }
//        JSONObject tableroot = EChartsTool.generateTableJson(dataarr,true,true,new ArrayList<>(cols),
//                "总量",tmpdatalist,new ArrayList<>(dates));

//        result.put("table",tableroot);

        return result;*/
        return null;
    }
}
