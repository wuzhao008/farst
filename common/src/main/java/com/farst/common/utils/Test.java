package com.farst.common.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lkq
 * @date 2020/06/30
 * @description:
 **/
public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        List list = new ArrayList<>();//入库数据
        //初始化，第一周至少四天,周天数占比
        WeekFields wfs = WeekFields.of(DayOfWeek.MONDAY, 4);
        //一年最后一天日期的LocalDate，如果该天获得的周数为1或52，那么该年就只有52周，否则就是53周
        //获取指定时间所在年的周数
        for (int y = 0; y < 10; y++) {//y控制年限
            int num = LocalDate.of(2020 + y, 12, 31).get(wfs.weekOfWeekBasedYear());
            num = num == 1 ? 52 : num;
            for (int i = 1; i <= num; i++) {//num控制周数
                String days = getDay(2020 + y, i, DayOfWeek.MONDAY) + "," +
                        getDay(2020 + y, i, DayOfWeek.TUESDAY) + "," +
                        getDay(2020 + y, i, DayOfWeek.WEDNESDAY) + "," +
                        getDay(2020 + y, i, DayOfWeek.THURSDAY) + "," +
                        getDay(2020 + y, i, DayOfWeek.FRIDAY) + "," +
                        getDay(2020 + y, i, DayOfWeek.SATURDAY) + "," +
                        getDay(2020 + y, i, DayOfWeek.SUNDAY);
                String[] split = trim(days, ",").split(",");
                days = split[0] + "~" + split[split.length - 1];
                //System.out.println(2020 + y + "年,第" + i + "周，日期:" + days);

                String name = (2020+y)+"-"+i;
                String timeStart = (2020 + y) + "-" + split[0] + " 00:00:00";
                String timeEnd = (2020 + y) + "-" + split[split.length - 1] + " 23:59:59";
                System.out.println("insert into clockin_statistics_date(name,type,time_start,time_end,create_date)value('"+name+"',1,'"+timeStart+"','"+timeEnd+"',now());");
                //如果 需要入库,用list即可
                HashMap<Object, Object> data = new HashMap<>();
                data.put("year", 2020 + y);
                data.put("week", i);
                data.put("days", days);
                list.add(data);
            }
        }

    }

    private static String getDay(Integer year, Integer num, DayOfWeek dayOfWeek) {
        //周数小于10在前面补个0
        String numStr = num < 10 ? "0" + String.valueOf(num) : String.valueOf(num);
        //2019-W01-01获取第一周的周一日期，2019-W02-07获取第二周的周日日期
        String weekDate = String.format("%s-W%s-%s", year, numStr, dayOfWeek.getValue());
        String date = LocalDate.parse(weekDate, DateTimeFormatter.ISO_WEEK_DATE).toString();
        String[] split = date.split("-");
        if (!split[0].equals(year.toString())) {
            //返回日期范围属于指定年
            return "";
        }
        date = split[1] + "-" + split[2];
        return date;
    }


    /**
     * 去除首尾指定字符
     *
     * @param str     字符串
     * @param element 指定字符
     * @return
     */
    public static String trim(String str, String element) {
        if (str == null || str.equals("")) return str;
        boolean beginIndexFlag = true;
        boolean endIndexFlag = true;
        do {
            int beginIndex = str.indexOf(element) == 0 ? 1 : 0;
            int endIndex = str.lastIndexOf(element) + 1 == str.length() ? str.lastIndexOf(element) : str.length();
            str = str.substring(beginIndex, endIndex);
            beginIndexFlag = (str.indexOf(element) == 0);
            endIndexFlag = (str.lastIndexOf(element) + 1 == str.length());
        } while (beginIndexFlag || endIndexFlag);
        return str;
    }

}