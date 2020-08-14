package com.topsci.qh.webmanagement.Service.Normal.DatabaseStatistic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Tools.Cache;
import com.topsci.qh.webmanagement.Tools.EChartsTool;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

/**
 * Created by lzw.
 * 16-11-30
 */
@Service
public class DqjbrqService extends BasicService {
    private static Logger logger = LoggerFactory.getLogger(DqjbrqService.class);
    
    private Map<String,String> ageGroupMap;
    
    @Resource
    private Cache cache;
    
    public DqjbrqService(){
    	ageGroupMap = new HashMap<String,String>(){{
    		put("1","0-20岁");
    		put("2","21-40岁");
    		put("3","41-60岁");
    		put("4","61-80岁");
    		put("5","80岁以上");
    	}};
    }

	public Map<String, JSONObject> getDqjbrqData(String starttime,String endtime, String areacode, String disease) {
		/*Map<String,JSONObject> result = new HashMap<>();

		String codeStr;
		int codeLen;
		if(StringUtils.isEmpty(areacode) || areacode.endsWith("0000000000")){
			//省
			codeStr = "63";
			codeLen = 2;
		}else if(StringUtils.isNotEmpty(areacode) && areacode.endsWith("00000000")){
			//市
			codeStr = areacode.substring(0,4);
			codeLen = 4;
		}else{
			//县
			codeStr = areacode.substring(0,6);
			codeLen = 6;
		}
		
        String sql = "select date_month,age_group,sum(amount) " +
                "from ST_DQRQJB " +
                "where date_month >= ? and date_month <= ? and disease=? and substring(area_code,1,"+codeLen+") = ? " +
                "group by age_group,date_month order by age_group,date_month;";
        List list = findBySQLMysql(sql,starttime,endtime,disease,codeStr);
        List<String> cols = new ArrayList<String>();
        List<String> dates = new ArrayList<>();
        for(Object obj : list)
        {
            Object[] result_col = (Object[]) obj;
            if(!cols.contains(ageGroupMap.get((String)result_col[1]))){
            	cols.add(ageGroupMap.get((String)result_col[1]));
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
        	String age_group = (String)((Object[])list.get(i))[1];
        	if(!tmp.equals(age_group)){
        		tmp = age_group;
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

        JSONObject t1 = EChartsTool.generateLineChartJson("相同地区，相同疾病，人群分布态势",dataarr,
                "日期","例",new ArrayList<>(dates),new ArrayList<>(cols));
//        JSONObject t2 = EChartsTool.generateBarChartJson("相同地区，相同疾病，人群分布态势",dataarr,
//                "日期","数量",new ArrayList<>(dates),new ArrayList<>(cols));
        tables.add(t1);
//        tables.add(t2);
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
            maps[index].put(ageGroupMap.get(((Object[])list.get(i))[1].toString()),((Object[])list.get(i))[2].toString());
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
	
	public Map<String, String> getDiseasesCodeNameMap(String starttime,String endtime,String ageGroup) {
		/*Map<String, String> diseasesCodeNameMap = new HashMap<>();
		String sql;
		if(StringUtils.isEmpty(ageGroup)){
			sql = "select disease from ST_DQRQJB where date_month>=? and date_month<=? group by disease order by sum(amount) desc limit 5;";
		}else{
			sql = "select disease from ST_DQRQJB where date_month>=? and date_month<=? and age_group='"+ageGroup+"' group by disease order by sum(amount) desc limit 5;";
		}
		List list = findBySQLMysql(sql,starttime,endtime);
		for(Object obj : list)
        {
            diseasesCodeNameMap.put((String)obj,(String)obj);
        }
		return diseasesCodeNameMap;*/
        return null;
	}

	public Map<String, String> getAgeGroupsCodeNameMap() {
		return ageGroupMap;
	}

	public Map<String, JSONObject> getDqrqjbData(String starttime,String endtime, String areacode, String ageGroup) {
		/*Map<String,JSONObject> result = new HashMap<>();

		String codeStr;
		int codeLen;
		if(StringUtils.isEmpty(areacode) || areacode.endsWith("0000000000")){
			//省
			codeStr = "63";
			codeLen = 2;
		}else if(StringUtils.isNotEmpty(areacode) && areacode.endsWith("00000000")){
			//市
			codeStr = areacode.substring(0,4);
			codeLen = 4;
		}else{
			//县
			codeStr = areacode.substring(0,6);
			codeLen = 6;
		}
		Map<String, String> diseases = getDiseasesCodeNameMap(starttime,endtime,ageGroup);
		boolean has = false;
		
        String sql = "select date_month,disease,sum(amount) " +
                "from ST_DQRQJB " +
                "where date_month >= ? and date_month <= ? and age_group=? and substring(area_code,1,"+codeLen+") = ? and disease in(";
        if(diseases.size()>0){
        	for(String key:diseases.keySet()){
        		if(!has){
        			sql += "'"+key+"'";
        			has = true;
        		}else{
        			sql += ",'"+key+"'";
        		}
        	}
        }else{
        	sql += "''";
        }
        sql+=") group by disease,date_month order by disease,date_month;";
        List list = findBySQLMysql(sql,starttime,endtime,ageGroup,codeStr);
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
        	String disease = (String)((Object[])list.get(i))[1];
        	if(!tmp.equals(disease)){
        		tmp = disease;
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

        JSONObject t1 = EChartsTool.generateLineChartJson("相同地区，相同人群，疾病分布态势",dataarr,
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

	public Map<String, JSONObject> getJbrqdqData(String starttime,String endtime, String areacode, String ageGroup,String disease) {
		/*Map<String,JSONObject> result = new HashMap<>();

		String codeStr;
		int codeLen;
		int codeLLen;
		if(StringUtils.isEmpty(areacode) || areacode.endsWith("0000000000")){
			//查所有市
			codeStr = "63";
			codeLLen = 2;
			codeLen = 4;
		}else if(StringUtils.isNotEmpty(areacode) && areacode.endsWith("00000000")){
			//查该市下面的县
			codeStr = areacode.substring(0,4);
			codeLLen = 4;
			codeLen = 6;
		}else{
			//查一个县
			codeStr = areacode.substring(0,6);
			codeLLen = 6;
			codeLen = 6;
		}
		
        String sql = "select tmp.date_month,tmp.area_code,sum(tmp.amount) from (select date_month,SUBSTRING(area_code,1,"+codeLen+") as area_code,amount from ST_DQRQJB "+
        		"where date_month >=? and date_month <= ? and age_group = ? and disease = ? and "+
        				"SUBSTRING(area_code,1,"+codeLLen+") = '"+codeStr+"') tmp group by tmp.area_code,tmp.date_month order by tmp.area_code,tmp.date_month;";
        List list = findBySQLMysql(sql,starttime,endtime,ageGroup,disease);
        List<String> cols = new ArrayList<String>();
        List<String> dates = new ArrayList<>();
        Map<String,String> citymap = (Map<String, String>) cache.getAttr(Cache.STATISTIC_CITY_CODE_NAME_MAP);
        Map<String,String> areamap = (Map<String, String>) cache.getAttr(Cache.STATISTIC_AREA_CODE_NAME_MAP);
        for(Object obj : list)
        {
            Object[] result_col = (Object[]) obj;
            if((StringUtils.isEmpty(areacode)||areacode.endsWith("0000000000")) && !cols.contains(citymap.get((String)result_col[1]+"00000000"))){
            	cols.add(citymap.get((String)result_col[1]+"00000000"));
            }else if(StringUtils.isNotEmpty(areacode) && !areacode.endsWith("0000000000") && !cols.contains(areamap.get((String)result_col[1]+"000000"))){
            	cols.add(areamap.get((String)result_col[1]+"000000"));
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
        	String area_code = (String)((Object[])list.get(i))[1];
        	if(!tmp.equals(area_code)){
        		tmp = area_code;
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

        JSONObject t1 = EChartsTool.generateLineChartJson("相同疾病，相同人群，地区分布态势",dataarr,
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
            if((StringUtils.isEmpty(areacode)||areacode.endsWith("0000000000"))){
            	maps[index].put(citymap.get(((Object[])list.get(i))[1].toString()+"00000000"),((Object[])list.get(i))[2].toString());
            }else if(StringUtils.isNotEmpty(areacode) && !areacode.endsWith("0000000000")){
            	maps[index].put(areamap.get(((Object[])list.get(i))[1].toString()+"000000"),((Object[])list.get(i))[2].toString());
            }
            
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
