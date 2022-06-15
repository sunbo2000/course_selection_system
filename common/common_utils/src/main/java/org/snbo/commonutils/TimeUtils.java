package org.snbo.commonutils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * @author sunbo
 * @create 2022-04-19-22:59
 */
@Component
public class TimeUtils {

    /**
     * 获取登录记录时使用,获取当前'天',redis插入时用
     */
    public static String getNowLoginCountName() {
        return "LoginCount" + LocalDate.now().getDayOfMonth();
    }

    /**
     * 获取登录记录时使用,特定天,查询时用
     */
    public static String getLoginCountName(String day) {
        return "LoginCount" + day.substring(8);
    }

    /**
     * 获取视频播放记录时使用,获取当前'天',redis插入时用
     */
    public static String getNowVideoCountName() {
        return "videoCount" + LocalDate.now().getDayOfMonth();
    }

    /**
     * 获取视频播放记录时使用,特定天,查询时用
     */
    public static String getVideoCountName(String day) {
        return "videoCount" + day.substring(8);
    }

    /**
     * 当前时间到第二天失效时间(1:00)的时间差,秒为单位
     */
    public static long getInvalidTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

}
