package com.topsci.qh.webmanagement.Service.Normal.DatabaseStatistic;

import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Resources.BasicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
/**
 * @ClassName: DiseaseSeasonService  
 * @Description: TODO
 * @author tgeng
 * @date 2016-12-6 上午10:46:14  
 *
 */
@Service
public class DiseaseSeasonService extends BasicService {
    private static Logger logger = LoggerFactory.getLogger(DiseaseSeasonService.class);

	public Map<String, JSONObject> getDiseaseSeasonData(String starttime,String endtime, String disease) {
		/*Map<String,JSONObject> result = new HashMap<>();
        String sql = "select tmp.year,tmp.season,sum(tmp.amount) from "+
        			"(select SUBSTRING(date_month,1,4) as year, case when SUBSTRING(date_month,6,2)>=3 and SUBSTRING(date_month,6,2)<=5 then '春季' "+
						"when SUBSTRING(date_month,6,2)>=6 and SUBSTRING(date_month,6,2)<=8 then '夏季' "+
						"when SUBSTRING(date_month,6,2)>=9 and SUBSTRING(date_month,6,2)<=11 then '秋季' "+
						"when SUBSTRING(date_month,6,2)=12 or SUBSTRING(date_month,6,2)=1 or SUBSTRING(date_month,6,2)=2 then '冬季' end as season, "+
						"amount from ST_DQRQJB where date_month>=? and date_month<=? "+
						"and disease = ? order by date_month) tmp group by tmp.season,tmp.year order by tmp.season,tmp.year;";
        List list = findBySQLMysql(sql,starttime,endtime,disease);
        List<String> cols = new ArrayList<String>();
        List<String> dates = new ArrayList<>();
        for(Object obj : list)
        {
            Object[] result_col = (Object[]) obj;
            if(!cols.contains((String)result_col[1])){
            	cols.add((String)result_col[1]);
            }
            if(!dates.contains((String)result_col[0])){
            	dates.add((String)result_col[0]);
            }
        }
        
        Collections.sort(dates);

        //chart
        JSONObject root = new JSONObject();
        JSONArray tables = new JSONArray();

        List<Map<String,Integer>> dataarr = new ArrayList<>();
        Map<String,Integer> labeleddata = new TreeMap<>();
        String tmp = "";
        for(int i = 0 ; i < list.size() ;i++)
        {
        	String season = (String)((Object[])list.get(i))[1];
        	if(!tmp.equals(season)){
        		tmp = season;
        		if(i!=0){
        			dataarr.add(labeleddata);
        		}
        		labeleddata = new TreeMap<>();
        	}
        	labeleddata.put((String)((Object[])list.get(i))[0],Integer.valueOf(((Object[])list.get(i))[2].toString()));
        }
        if(labeleddata.size()>0){
        	dataarr.add(labeleddata);
        }

        JSONObject t1 = EChartsTool.generateLineChartJson("相同疾病，不同季节分布态势",dataarr,
                "日期","例",new ArrayList<>(dates),new ArrayList<>(cols));
        tables.add(t1);
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
            maps[index].put(((Object[])list.get(i))[1].toString(),((Object[])list.get(i))[2].toString());
        }

        for(Map m : maps)
        {
            dataarr1.add(m);
        }

        //table
        JSONObject tableroot = EChartsTool.generateTableJson(dataarr1,new ArrayList<>(dates),new ArrayList<>(cols),"日期\\统计数量");

        result.put("table",tableroot);
        return result;*/
        return null;
	}
}
