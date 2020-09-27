package com.example.waterintake;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Constants {

  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
  public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("h:mm:sss a");
  public static final SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


  public static final SimpleDateFormat FULL_DATE_FORMAT_FOR_REPORTS = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS a");


  public static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MMMM");



  public static final Calendar calender = Calendar.getInstance();
  public static final String TRY_CATCH_MONTHLY_HISTORY_FRAGMENT= "TRY_CATCH_MONTHLY_HISTORY_FRAGMENT";




}
