package com.github.shanbei.shanbeiuser.controller;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class functool {
    public static void main(String[] args) {
        // 获取当前日期
        LocalDate today = LocalDate.now();

        // 在这个程序中，我们使用了Java 8中的LocalDate类来获取当前日期，并使用WeekFields类来获取本地化设置中一周的第一天是星期几。然后，我们计算今天是一年中的第几周，并输出结果。
        // 当调用 today.get(weekFields.weekOfYear()) 时，将返回当前日期属于当年的第几周。然后通过 (today.getDayOfWeek().getValue() < firstDayOfWeek ? 1 : 0) 计算出当年的第一周开始的日期是周几，如果是周日，那么将返回0，否则返回1。
        //
        // 最后将第一周开始的日期所在周之前的所有天数减去，得到今天是今年的第几周。
        //
        // 例如，假设当年的第一周从星期三开始，如果今天是星期四

        // 获取本地化设置中一周的第一天是星期几
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int firstDayOfWeek = weekFields.getFirstDayOfWeek().getValue();
        System.out.println(firstDayOfWeek);

        // 计算今天是一年中的第几周
        int weekOfYear = today.get(weekFields.weekOfYear()) - (today.getDayOfWeek().getValue() < firstDayOfWeek ? 1 : 0);

        System.out.println("今天是第 " + weekOfYear + " 周");
    }
}
