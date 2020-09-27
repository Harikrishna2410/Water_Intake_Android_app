package com.example.waterintake.Constant_Classes;

import android.graphics.Color;
import android.util.Log;

import com.example.waterintake.Constants;
import com.example.waterintake.R;
import com.example.waterintake.WaveVariable;
import com.example.waterintake.realm_db.Daily_history;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MPChart {
  public static ArrayList<BarEntry> weekly_total;
  public static BarData weekly_barData;
  public static BarDataSet weekly_barDataSet;
  public Realm realm;

  public MPChart() {

  }

  public static void Monthly_MPChartDisplay(Date currentDate, Date finalenddate, String LabelDiscription) {

    weekly_total = new ArrayList<BarEntry>();
    Calendar start = Calendar.getInstance();
    Calendar end = Calendar.getInstance();
    start.setTime(currentDate);
    end.setTime(finalenddate);

    List<Integer> chartdata = new ArrayList<Integer>();
    int a = 0;
    int total = 0;
    while (start.before(end)) {

      for (int i = 0; i < Realm_Constants.ALL_DAILY_HISTORY.size(); i++) {

        String str_date = Constants.DATE_FORMAT.format(Realm_Constants.ALL_DAILY_HISTORY.get(i).getDatetime());
        String currentdate = Constants.DATE_FORMAT.format(start.getTime());

        if (currentdate.equals(str_date)) {
          int temp = Realm_Constants.ALL_DAILY_HISTORY.get(i).getWater_intake_level();
          Log.d("usersquery", temp + "| Date :-" + Realm_Constants.ALL_DAILY_HISTORY.get(i).getDatetime());
          total = temp + total;
        }

      }
      Log.w("Date_&_Total", Constants.DATE_FORMAT.format(start.getTime()) + " | " + String.valueOf(total));
      chartdata.add(total);
      total = 0;
      start.add(Calendar.DATE, 1);
    }


    for (int j = 0; j < chartdata.size(); j++) {

      weekly_total.add(new BarEntry(j, chartdata.get(j)));

    }

    WaveVariable.getMonthly_Barchart().notifyDataSetChanged();
    WaveVariable.getMonthly_Barchart().invalidate();

    weekly_barDataSet = new BarDataSet(weekly_total, LabelDiscription);
    weekly_barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
    weekly_barDataSet.setValueTextColor(Color.BLACK);
    weekly_barDataSet.setValueTextSize(12f);

    weekly_barData = new BarData(weekly_barDataSet);
    WaveVariable.getMonthly_Barchart().setFitBars(true);
    WaveVariable.getMonthly_Barchart().setData(weekly_barData);
    WaveVariable.getMonthly_Barchart().animate();


  }

}
