package com.topsci.qh.webmanagement.Tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by lzw.
 * 16-12-1
 */
public class EChartsTool {
    private static Logger logger = LoggerFactory.getLogger(EChartsTool.class);

    public enum ChartType {
        pie,
        bar,
        linear
    }

    /**
     * 生成折线图json
     *
     * @param title      图表标题
     * @param data       图表数据，按横坐标(xAxisLabel)和图例(legends)的顺序传入值，例如横坐标轴为2个，图例为3个时：[[1,2,3],[1,2,3]]
     * @param xAxisName  横坐标轴单位
     * @param yAxisName  纵坐标轴单位
     * @param xAxisLabel 横坐标轴标签
     * @param legends    图例名称
     * @return
     */
    public static <T> JSONObject generateLineChartJson(String title, List<Map<String, T>> data, String xAxisName,
                                                       String yAxisName, List<String> xAxisLabel, List<String> legends) {
        return generateChartJson(ChartType.linear, title, data, xAxisName, yAxisName, xAxisLabel, legends);
    }

    /**
     * 生成柱状图json
     *
     * @param title      图表标题
     * @param data       图表数据，按横坐标(xAxisLabel)和图例(legends)的顺序传入值，例如横坐标轴为2个，图例为3个时：[[1,2,3],[1,2,3]]
     * @param xAxisName  横坐标轴单位
     * @param yAxisName  纵坐标轴单位
     * @param xAxisLabel 横坐标轴标签
     * @param legends    图例名称
     * @return
     */
    public static <T> JSONObject generateBarChartJson(String title, List<Map<String, T>> data, String xAxisName,
                                                      String yAxisName, List<String> xAxisLabel, List<String> legends) {
        return generateChartJson(ChartType.bar, title, data, xAxisName, yAxisName, xAxisLabel, legends);
    }

    /**
     * 生成饼状图json
     *
     * @param title 图表标题
     * @param names 图表各部分名称
     * @param datas 图表各部分的数据(按names顺序存入)
     * @return
     */
    public static <T> JSONObject generatePieChartJson(String title, List<String> names, List<T> datas) {
        JSONObject t1 = new JSONObject();
        try {
            t1.put("type", "pie");
            JSONObject data = new JSONObject();
            data.put("title", title);
            JSONArray pie_data = new JSONArray();
            for (int i = 0; i < names.size(); i++) {
                JSONObject one = new JSONObject();
                one.put("name", names.get(i));
                if (!(datas.get(i) instanceof Integer || datas.get(i) instanceof Float || datas.get(i) instanceof Double)) {
                    throw new Exception("图表只支持int,float,double类型参数");
                }
                one.put("value", datas.get(i));
                pie_data.add(one);
            }
            data.put("pie_data", pie_data);
            t1.put("data", data);
        } catch (Exception ex) {
            logger.error("ECharts工具类转换图表Json出错!", ex);
        }
        return t1;
    }

    private static <T> JSONObject generateChartJson(ChartType type, String title, List<Map<String, T>> data, String xAxisName,
                                                    String yAxisName, List<String> xAxisLabel, List<String> legends) {
        JSONObject t1 = new JSONObject();
        try {
            if (type == ChartType.bar) {
                t1.put("type", "bar");
            } else if (type == ChartType.linear) {
                t1.put("type", "line");
            }
            JSONObject t1_data = new JSONObject();
            t1_data.put("title", title);
            t1_data.put("x_title", xAxisName);
            t1_data.put("y_title", yAxisName);
            t1_data.put("col_type", listToArray(legends));
            t1_data.put("x_names", listToArray(xAxisLabel));

            JSONArray dataarrtable = new JSONArray();

            for (Map<String, T> dataobjlist : data) {
                List<Object> tmpList = new ArrayList<>();
                for (String label : xAxisLabel) {
                    if (dataobjlist.containsKey(label)) {
                        if (!(dataobjlist.get(label) instanceof Integer || dataobjlist.get(label) instanceof Float || dataobjlist.get(label) instanceof Double)) {
                            throw new Exception("图表只支持int,float,double类型参数");
                        }
                        tmpList.add(dataobjlist.get(label));
                    } else {
                        tmpList.add(0);
                    }
                }
                dataarrtable.add(listToArray(tmpList));
            }
            t1_data.put("col_data", dataarrtable);
            t1.put("data", t1_data);
        } catch (Exception ex) {
            logger.error("ECharts工具类转换图表Json出错!", ex);
        }
        return t1;
    }


    public static <T> JSONObject generateTableJson(List<Map<String, T>> data, List<String> leftColName, List<String> ColName) {
        return generateTableJson(data, true, false, leftColName, null, null, ColName);
    }


    public static <T> JSONObject generateTableJson(List<Map<String, T>> data, String footername, List<Object> footerdata, List<String> ColName) {
        return generateTableJson(data, false, true, null, footername, footerdata, ColName);
    }

    public static <T> JSONObject generateTableJson(List<Map<String, T>> data, List<String> leftColName, List<String> ColName, String lefttop) {
        return generateTableJson(data, true, false, leftColName, null, null, ColName, lefttop);
    }


    public static <T> JSONObject generateTableJson(List<Map<String, T>> data, boolean hasleft, boolean hasfooter, List<String> leftColName,
                                                   String footername, List<Object> footerdata, List<String> ColName, String lefttop) {
        JSONObject obj = generateTableJson(data, hasleft, hasfooter, leftColName, footername, footerdata, ColName);
        obj.put("lefttop", lefttop);
        return obj;
    }

    /**
     * 生成表格json
     *
     * @param data        图表数据，列为2个，行为3个时：[[1,2],[3,4],[5,6]]
     * @param hasleft     图表左边是否有标题
     * @param hasfooter   图表是否有footer
     * @param leftColName 当hasleft为true时，传入各标题名称
     * @param footername  当hasfooter和hasleft均为true时，传入footer在左边的名称
     * @param footerdata  当hasfooter为true是，传入footer的数据，列为2个时：[7,8]
     * @param ColName     列标题
     * @return
     */
    public static <T> JSONObject generateTableJson(List<Map<String, T>> data, boolean hasleft, boolean hasfooter, List<String> leftColName,
                                                   String footername, List<Object> footerdata, List<String> ColName) {
        JSONObject tableroot = new JSONObject();
        JSONObject settings = new JSONObject();
        try {
            if (hasleft) {
                tableroot.put("left_names", listToArray(leftColName));
            }
            settings.put("hasleft", hasleft);

            if (hasfooter) {
                JSONObject footer = new JSONObject();
                footer.put("name", footername);
                footer.put("data", listToArray(footerdata));
                tableroot.put("footer", footer);
            }
            settings.put("hasfoot", hasfooter);
            tableroot.put("settings", settings);
            tableroot.put("col_name", listToArray(ColName));

            JSONArray dataarrtable = new JSONArray();
            for (Map<String, T> dataobjlist : data) {
                List<Object> tmpList = new ArrayList<>();
                for (String label : ColName) {
                    if (dataobjlist.containsKey(label)) {
                        if (!(dataobjlist.get(label) instanceof Integer || dataobjlist.get(label) instanceof Float || dataobjlist.get(label) instanceof Double ||
                                dataobjlist.get(label) instanceof String)) {
                            throw new Exception("表格只支持,string,int,float,double类型参数");
                        }
                        tmpList.add(dataobjlist.get(label));
                    } else {
                        tmpList.add(0);
                    }
                }
                dataarrtable.add(listToArray(tmpList));
            }
            tableroot.put("data", dataarrtable);
            tableroot.put("lefttop", "&nbsp");
        }
        catch (Exception ex)
        {
            logger.error("ECharts工具类转换表格Json出错!", ex);
        }
        return tableroot;
    }

    private static <T> JSONArray listToArray(List<T> list) {
        JSONArray array = new JSONArray();
        if (list == null)
            return array;
        for (T obj : list) {
            array.add(obj);
        }
        return array;
    }
}
