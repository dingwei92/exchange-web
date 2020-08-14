package com.topsci.qh.webmanagement.Service.Normal.DatabaseStatistic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Tools.EChartsTool;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lzw on 2016/12/2.
 */
@Service
public class ChronicDiseaseService extends BasicService{
    //定时统计任务sql语句(冠心病)
    //select date_format(clinic_date,"%Y-%m"),"冠心病",left(report_org_qx,6),count(*) from ZR_BASIC_CORONARY_HEART_DISEASE
    // group by date_format(clinic_date,"%y-%m"),left(report_org_qx,6)
    //其他相关表：
    //ZR_BASIC_DIABETES  糖尿病
    //ZR_BASIC_HIGH_BLOOD_PRESSURE  高血压
    //ZR_BASIC_INFECTION_DISEASE  传染病
    //ZR_BASIC_STROKE  脑卒中
    //ZR_BASIC_TUMOUR  肿瘤



    private Logger logger = LoggerFactory.getLogger(ChronicDiseaseService.class);

    public Map<String,JSONObject> getData(String starttime,String endtime,String areacode)
    {
        /*Map<String,JSONObject> result = new HashMap<>();
        if(StringUtils.isEmpty(areacode))
        {
            areacode = "630000000000";
        }
        areacode = areacode.replaceAll("0000000000","").replaceAll("00000000","").replaceAll("000000","");

        String sql = "select date_month,disease_name,sum(amount) " +
                "from ST_CHRONIC_DISEASE " +
                "where area_code like '"+areacode+"%' and date_month >= ? and date_month <= ? " +
                "group by disease_name,date_month order by disease_name,date_month;";
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
        List<Integer> piedata = new ArrayList<>();
        int count=0;
        for(int i = 0 ; i < list.size() ;i++)
        {
            if(i % dates.size() == 0)
            {
                labeleddata = new TreeMap<>();
                count = 0;
            }
            labeleddata.put((String)((Object[])list.get(i))[0],Integer.valueOf(((Object[])list.get(i))[2].toString()));
            count += Integer.valueOf(((Object[])list.get(i))[2].toString());
            if(i % dates.size() == dates.size() -1)
            {
                dataarr.add(labeleddata);
                piedata.add(count);
            }
        }


        JSONObject t1 = EChartsTool.generateLineChartJson("慢性病发病情况趋势",dataarr,
                "日期","患者/人",new ArrayList<>(dates),new ArrayList<>(cols));

        JSONObject t2 = EChartsTool.generatePieChartJson("慢性病发病情况占比",new ArrayList<>(cols),piedata);

        tables.add(t1);
        tables.add(t2);
        root.put("chart",tables);
        result.put("chart",root);


        //table
        sql = "select sum(t) from (select date_month,sum(amount) as t from ST_CHRONIC_DISEASE " +
                "where area_code like '"+areacode+"%' and  date_month >= ? and date_month <= ? " +
                "group by disease_name,date_month " +
                "order by disease_name,date_month) result "+
                "group by date_month";
        list = findBySQLMysql(sql,starttime,endtime);
        List<Object> tmpdatalist = new ArrayList<>();
        for(Object obj : list)
        {
            tmpdatalist.add(((BigDecimal) obj).intValue());
        }
        JSONObject tableroot = EChartsTool.generateTableJson(dataarr,true,true,new ArrayList<>(cols),
                "总量",tmpdatalist,new ArrayList<>(dates));

        result.put("table",tableroot);

        return result;*/
        return null;
    }
}
