package com.topsci.qh.webmanagement.Service.Normal.DatabaseStatistic;

import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Resources.BasicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lzw on 2016/12/7.
 */
@Service
public class HealthChargeService extends BasicService {



    //private Logger logger = LoggerFactory.getLogger(HospitalizedRateService.class);

    public Map<String,JSONObject> getHealthChargeData(String starttime, String endtime, String areacode)
    {
        //定时统计语句
        //select DATE_FORMAT(MJZFYZFRQTFSJ,'%Y-%m') as date_month,'门急诊',ceiling(sum(GRCDFY)),JZJGDM from XT_OE_CHARGE
        // group by date_month,JZJGDM order by date_month,JZJGDM; -- 门诊

        //select DATE_FORMAT(JSSJ,'%Y-%m') as date_month,'住院',ceiling(sum(ZJE)),ZZJGMC from XT_BE_HOSPITALIZED_CHARGE
        // group by date_month,ZZJGMC order by date_month,ZZJGMC; -- 住院
       /* Map<String,JSONObject> result = new HashMap<>();

        if(StringUtils.isEmpty(areacode))
        {
            areacode = "910000000001";
        }

        //organ_id like '"+areacode+"%' and
        String sql = "select date_month,sum(amount),type from ST_HEALTH_CHARGE " +
                "where date_month >= ? and date_month <= ? and organ_id in " +
                "(select id from IRPT_DEPARTMENTS where upid0 = ? or upid1 = ? or upid2 = ? " +
                "or upid3 = ? or upid4 = ? or upid5 = ?) " +
                "group by type,date_month order by type,date_month";
        List list = findBySQLMysql(sql,starttime,endtime,areacode,areacode,areacode,areacode,areacode,areacode);
        Set<String> dates = new TreeSet<>();
        Set<String> cols = new TreeSet<>();
        for(Object obj : list)
        {
            Object[] result_col = (Object[]) obj;
            dates.add((String)result_col[0]);
            cols.add((String)result_col[2]);
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


        JSONObject t1 = EChartsTool.generateLineChartJson("医疗费用收入变化",dataarr,
                "日期",":万元",new ArrayList<>(dates),new ArrayList<>(cols));
        JSONObject t2 = EChartsTool.generateBarChartJson("医疗费用收入量统计",dataarr,
                "日期",":万元",new ArrayList<>(dates),new ArrayList<>(cols));


        tables.add(t1);
        tables.add(t2);
        root.put("chart",tables);
        result.put("chart",root);


        List<Map<String,Integer>> dataarr1 = new ArrayList<>();
        Map[] maps = new Map[dates.size()];
        List collist = new ArrayList(dates);
        for(int i = 0 ; i < list.size() ;i++)
        {
            String key = ((Object[])list.get(i))[0].toString();
            int index = collist.indexOf(key);
            if(maps[index] == null )
            {
                maps[index] = new TreeMap<>();
            }
            maps[index].put(((Object[])list.get(i))[2].toString(),((Object[])list.get(i))[1].toString());
        }

        for(Map m : maps)
        {
            dataarr1.add(m);
        }

        //table
        JSONObject tableroot = EChartsTool.generateTableJson(dataarr1,true,false, new ArrayList<>(dates),null,null,new ArrayList<>(cols),"日期\\费用(元)");

        result.put("table",tableroot);

        return result;*/
        return null;
    }

    public Map<String,JSONObject> getAnnualCapitalData(String starttime, String endtime, String areacode)
    {
        //定时统计语句
        //select left(BBQ_,4) as date_year,sum(C2) as amount,USERID_,"医院" as type from TJ_INCOME_EXPENSE_1_1 group by date_year,USERID_ order by date_year,USERID_;
        //select left(BBQ_,4) as date_year,sum(C2) as amount,USERID_,"社区中心" as type from TJ_INCOME_EXPENSE_1_2 group by date_year,USERID_ order by date_year,USERID_;
        //select left(BBQ_,4) as date_year,sum(C3) as amount,USERID_,"急救中心" as type from TJ_INCOME_EXPENSE_1_5 group by date_year,USERID_ order by date_year,USERID_;
        //select left(BBQ_,4) as date_year,sum(C2) as amount,USERID_,"其他" as type from TJ_INCOME_EXPENSE_1_7 group by date_year,USERID_ order by date_year,USERID_;


       /* Map<String,JSONObject> result = new HashMap<>();

        if(StringUtils.isEmpty(areacode))
        {
            areacode = "910000000001";
        }

        //organ_id like '"+areacode+"%' and
        String sql = "select date_year,sum(amount),type from ST_HEALTH_ANNUAL_CAPITAL " +
                "where date_year >= ? and date_year <= ? and organ_id in " +
                "(select id from IRPT_DEPARTMENTS where upid0 = ? or upid1 = ? or upid2 = ? " +
                "or upid3 = ? or upid4 = ? or upid5 = ?) " +
                "group by type,date_year order by type,date_year desc";
        List list = findBySQLMysql(sql,starttime,endtime,areacode,areacode,areacode,areacode,areacode,areacode);
        Set<String> dates = new TreeSet<>();
        Set<String> cols = new TreeSet<>();
        for(Object obj : list)
        {
            Object[] result_col = (Object[]) obj;
            dates.add((String)result_col[0]);
            cols.add((String)result_col[2]);
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

        List<Integer> datapie = new ArrayList<>();
        for(Map<String,Integer> map : dataarr)
        {
            int i = 0;
            for(Map.Entry<String,Integer> m : map.entrySet())
            {
                i+=m.getValue();
            }
            datapie.add(i);
        }

        JSONObject t1 = EChartsTool.generateLineChartJson("年度医疗资金趋势",dataarr,
                "年份",":万元",new ArrayList<>(dates),new ArrayList<>(cols));
        JSONObject t2 = EChartsTool.generateBarChartJson("年度医疗资金统计",dataarr,
                "年份",":万元",new ArrayList<>(dates),new ArrayList<>(cols));
        JSONObject t3 = EChartsTool.generatePieChartJson("年度医疗资金比例",new ArrayList<>(cols),datapie);

        tables.add(t1);
        tables.add(t3);
        tables.add(t2);
        root.put("chart",tables);
        result.put("chart",root);


        List<Map<String,Integer>> dataarr1 = new ArrayList<>();
        Map[] maps = new Map[dates.size()];
        List collist = new ArrayList(dates);
        for(int i = 0 ; i < list.size() ;i++)
        {
            String key = ((Object[])list.get(i))[0].toString();
            int index = collist.indexOf(key);
            if(maps[index] == null )
            {
                maps[index] = new TreeMap<>();
            }
            maps[index].put(((Object[])list.get(i))[2].toString(),((Object[])list.get(i))[1].toString());
        }

        for(Map m : maps)
        {
            dataarr1.add(m);
        }

        //table
        JSONObject tableroot = EChartsTool.generateTableJson(dataarr1,true,false, new ArrayList<>(dates),null,null,new ArrayList<>(cols),"年份\\费用(元)");

        result.put("table",tableroot);

        return result;*/
        return null;
    }
}
