package com.topsci.qh.webmanagement.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lzw.
 * 16-8-3
 */
@Component
public class Cache {
    private Logger logger = LoggerFactory.getLogger(Cache.class);

    //@Resource

    public static final String BASE_NATION = "BASE_NATION";
    public static final String BASE_GENDER = "BASE_GENDER";
    public static final String BASE_ID = "BASE_ID";
    public static final String BASE_CATA_POP = "BASE_CATA_POP";
    public static final String STATISTIC_AREA_CODE = "STATISTIC_AREA_CODE";
    public static final String STATISTIC_ORGAN_CODE = "STATISTIC_ORGAN_CODE";
    public static final String STATISTIC_CITY_CODE_NAME_MAP="STATISTIC_CITY_CODE_NAME_MAP";
    public static final String STATISTIC_AREA_CODE_NAME_MAP="STATISTIC_AREA_CODE_NAME_MAP";

    public static final String DATE_YEAR_YYYY="DATE_YEAR_YYYY";
    public static final String DATE_MONTH_MM="DATE_MONTH_MM";
    public static final String DATE_DAY_DD="DATE_DAY_DD";

    private Map<String, Object> cache;

    public Cache() {
        cache = new HashMap<>();
    }

    public Object getAttr(String key) {
        return cache.get(key);
    }

    public void setAttr(String key, Object value) {
        cache.put(key, value);
    }
}
