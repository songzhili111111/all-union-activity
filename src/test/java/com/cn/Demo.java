package com.cn;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * 
 * @author songzhili
 * 2016年7月8日下午1:56:43
 */

public class Demo {
    
    private static DateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
    private String dayInfo[][];
    private int dayCount;//间隔天数
 
     
    public static void main(String[] args) throws ParseException {
    	Integer[] ints = new Integer[2];
    	ints[0] = 0;
    	ints[1] = 1;
    	Integer[] ints0 = new Integer[5];
    	System.arraycopy(ints, 0, ints0, 0, 1);
    	System.out.println(ints0[0]);
    	System.out.println(ints0[1]);
    	System.out.println(ints0[2]);
    }
     
    public  void initDayInfo(String start,String end)
    {
        //初始化日期信息
    	Calendar cal1 = Calendar.getInstance();
    	Calendar cal2 = Calendar.getInstance();
    	Calendar cal3 = Calendar.getInstance();
        int year,month,day;
        int i=0;
        year=Integer.parseInt(start.substring(0,4));
        month=Integer.parseInt(start.substring(4,6));
        day=Integer.parseInt(start.substring(6,8));
        cal1.set(year, month-1, day);
        cal3.set(year, month-1, day);
        year=Integer.parseInt(end.substring(0,4));
        month=Integer.parseInt(end.substring(4,6));
        day=Integer.parseInt(end.substring(6,8));
        cal2.set(year, month-1, day);
        while(!cal2.before(cal3))
        {   
            i++;
            cal3.add(java.util.Calendar.DAY_OF_MONTH, 1);//日期时间+1
        }
        //每日数据列表
        dayInfo=new String[i+1][3];
        i=0;
        while(!cal2.before(cal1))
        {   
            dayInfo[i][0]=sDateFormat.format(cal1.getTime());
            i++;
            cal1.add(java.util.Calendar.DAY_OF_MONTH, 1);
        }
         
        this.dayCount = i;
        for (int j=0;j<i;j++)
        {
            this.dayInfo[j][1]="0";
            this.dayInfo[j][2]="0";
        }
         
    }
    public int getDayCount() {
        return dayCount;
    }
 
    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }
 
    public String[][] getDayInfo() {
        return dayInfo;
    }
 
    public void setDayInfo(String[][] dayInfo) {
        this.dayInfo = dayInfo;
    }
}