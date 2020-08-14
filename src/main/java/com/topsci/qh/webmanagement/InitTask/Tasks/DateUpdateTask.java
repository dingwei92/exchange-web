package com.topsci.qh.webmanagement.InitTask.Tasks;


import com.topsci.qh.webmanagement.Tools.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

/**
 * Created by lzw on 2016/12/6.
 */
@Component
public class DateUpdateTask extends TimerTask{
    private Logger logger = LoggerFactory.getLogger(DateUpdateTask.class);

    @Resource
    private Cache cache;

    @Override
    public void run() {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            int year = c.get(Calendar.YEAR) ;
            int month = c.get(Calendar.MONTH)+1 ;
            int day = c.get(Calendar.DAY_OF_MONTH);

            cache.setAttr(Cache.DATE_YEAR_YYYY, year);
            cache.setAttr(Cache.DATE_MONTH_MM, month);
            cache.setAttr(Cache.DATE_DAY_DD, day);
        }
        catch (Exception ex)
        {
            logger.error("时间缓存更新线程错误");
        }
    }
}
