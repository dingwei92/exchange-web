package com.topsci.qh.webmanagement.Service.Normal.DatabaseStatistic;

import com.alibaba.fastjson.JSONObject;
import com.topsci.qh.webmanagement.Resources.BasicService;
import com.topsci.qh.webmanagement.Tools.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @ClassName: AreaDiseaseDeadService  
 * @Description: TODO
 * @author tgeng
 * @date 2016-12-7 下午1:12:24  
 *
 */
@Service
public class AreaDiseaseDeadService extends BasicService {
    private static Logger logger = LoggerFactory.getLogger(AreaDiseaseDeadService.class);
    
    @Resource
    private Cache cache;

	public Map<String, JSONObject> getDiseaseDeadData(String starttime,String endtime, String areacode) {
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
		List<String> hdisease = getHDiseases(starttime,endtime,codeLen,codeStr);
        String sql = "select year,disease,FORMAT(sum(dead_amount)/sum(amount)*100,2) from ST_DQJBSWL "+
        				"where year >= ? and year <= ? and substring(area_code,1,"+codeLen+") = ? and disease in(";
        if(hdisease.size()>0){
        	for(int i = 0;i<hdisease.size();i++){
        		if(i==0){
        			sql += "'"+hdisease.get(i)+"'";
        		}else{
        			sql += ",'"+hdisease.get(i)+"'";
        		}
        	}
        }else{
        	sql += "''";
        }
        sql += ")group by disease,year order by disease,year;";
        List list = findBySQLMysql(sql,starttime,endtime,codeStr);
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

        List<Map<String,Float>> dataarr = new ArrayList<>();
        Map<String,Float> labeleddata = new TreeMap<>();
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
        	labeleddata.put((String)((Object[])list.get(i))[0],Float.valueOf(((Object[])list.get(i))[2].toString()));
        }
        if(labeleddata.size()>0){
        	dataarr.add(labeleddata);
        }

        JSONObject t1 = EChartsTool.generateLineChartJson("区域高发疾病死亡趋势",dataarr,
                "日期","死亡率(%)",new ArrayList<>(dates),new ArrayList<>(cols));
        JSONObject t2 = EChartsTool.generateBarChartJson("区域高发疾病死亡率统计",dataarr,
                "日期","死亡率(%)",new ArrayList<>(dates),new ArrayList<>(cols));
        
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
            maps[index].put(((Object[])list.get(i))[1].toString(),((Object[])list.get(i))[2].toString()+"%");
        }

        for(Map m : maps)
        {
            dataarr1.add(m);
        }

        //table
        JSONObject tableroot = EChartsTool.generateTableJson(dataarr1,new ArrayList<>(dates),new ArrayList<>(cols),"日期\\死亡率");

        result.put("table",tableroot);

        return result;*/
        return null;
	}
	
	public List<String> getHDiseases(String starttime,String endtime,int codeLen,String codeStr) {
		/*String sql = "select disease from ST_DQJBSWL where year>=? and year<=? and substring(area_code,1,"+codeLen+") = ?  group by disease order by sum(amount) desc limit 5;";
		List list = findBySQLMysql(sql,starttime,endtime,codeStr);
		return (List<String>)list;*/
		return null;
	}
	
	public List<String> getYears(){
		List<String> years = new ArrayList<>();
		int nowYear = (int)cache.getAttr(Cache.DATE_YEAR_YYYY);
		for(int i=1980;i<nowYear;i++){
			years.add(String.valueOf(i));
		}
		return years;
	}
}
