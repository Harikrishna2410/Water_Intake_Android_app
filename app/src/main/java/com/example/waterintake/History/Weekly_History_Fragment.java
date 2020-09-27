package com.example.waterintake.History;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.astritveliu.boom.Boom;
import com.example.waterintake.Constants;
import com.example.waterintake.Modal_Classis.DailyHistory;
import com.example.waterintake.R;
import com.example.waterintake.realm_db.Daily_history;
import com.example.waterintake.sharedPreference;
import com.example.waterintake.todays_history_rv_adapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Weekly_History_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Weekly_History_Fragment extends Fragment {
  ViewGroup root;
  ConstraintLayout btn_change_date;
  TextView tv_date, tv_WeeklyTotal;
  BarChart barChart;
  BarEntry barEntry;
  BarData barData;
  BarDataSet barDataSet;
  RecyclerView recyclerView;
  int year, month, day, day1;
  static String spdate;
  public static BarChart weekly_barChart;
  public static ArrayList<BarEntry> weekly_total;
  public static BarData weekly_barData;
  public static BarDataSet weekly_barDataSet;
  public static RealmQuery<Daily_history> weekly_histroy;
  public static Realm realm;
  todays_history_rv_adapter todays_history_rv_adapter;
  RealmResults<Daily_history> Weekly_histories;
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
  Date finalenddate, currentDate, d1, d2;
  static long l1 = 0;
  static long l2 = 0;
  ArrayList<DailyHistory> Weekly_History = new ArrayList<>();
  sharedPreference sp;

  public static Weekly_History_Fragment instance;

  public static Date fromdate;


  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public Weekly_History_Fragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment Weekly_History_Fragment.
   */
  // TODO: Rename and change types and number of parameters
  public static Weekly_History_Fragment newInstance(String param1, String param2) {
    Weekly_History_Fragment fragment = new Weekly_History_Fragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  public static Weekly_History_Fragment getInstace() {
    return instance;
  }


  @SuppressLint("SetTextI18n")
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    root = (ViewGroup) inflater.inflate(R.layout.fragment_weekly__history_, container, false);
    instance = this;
    btn_change_date = root.findViewById(R.id.date_change_weekly_history);
    tv_date = root.findViewById(R.id.tv_intake_total_in_ml);
    recyclerView = root.findViewById(R.id.rv_weekly_history);
    weekly_barChart = root.findViewById(R.id.weekly_barChart);

    sp = new sharedPreference(getActivity());
    sp.editor_client_pref.putBoolean("weeklyreport", true);
    sp.editor_client_pref.commit();

    realm = Realm.getDefaultInstance();

    Calendar c = Calendar.getInstance();
    year = c.get(Calendar.YEAR);
    month = c.get(Calendar.MONTH);
    day = c.get(Calendar.DAY_OF_MONTH);

    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MILLISECOND, 0);
    c.add(Calendar.DATE, -7);


    currentDate = new Date();
    Calendar b = Calendar.getInstance();
    b.setTime(currentDate);
    b.set(Calendar.HOUR_OF_DAY, 0);
    b.set(Calendar.MINUTE, 0);
    b.set(Calendar.SECOND, 0);
    b.set(Calendar.MILLISECOND, 0);

    b.add(Calendar.DATE, 0);

    currentDate = c.getTime();

    finalenddate = b.getTime();

    tv_date.setText(dateFormat.format(currentDate) + " To " + dateFormat.format(finalenddate));

    btn_change_date.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        DatePickerDialog dp = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
          @RequiresApi(api = Build.VERSION_CODES.O)
          @Override
          public void onDateSet(DatePicker datePicker, int year, int month, int day) {

            Calendar startdate = Calendar.getInstance();
            startdate.set(Calendar.YEAR, year);
            startdate.set(Calendar.MONTH, month);
            startdate.set(Calendar.DAY_OF_MONTH, day);
            startdate.set(Calendar.HOUR_OF_DAY, 0);
            startdate.set(Calendar.MINUTE, 0);
            startdate.set(Calendar.SECOND, 0);
            startdate.set(Calendar.MILLISECOND, 0);
            startdate.add(Calendar.DATE, 0);
            fromdate = startdate.getTime();

            Date currentdate = startdate.getTime();
            Calendar enddate = Calendar.getInstance();
            enddate.setTime(currentdate);
            enddate.set(Calendar.HOUR_OF_DAY, 0);
            enddate.set(Calendar.MINUTE, 0);
            enddate.set(Calendar.SECOND, 0);
            enddate.set(Calendar.MILLISECOND, 0);
            enddate.add(Calendar.DATE, 6);

            finalenddate = enddate.getTime();

            tv_date.setText(dateFormat.format(fromdate) + " To " + dateFormat.format(finalenddate));
            Weekly_History.clear();
            todays_history_rv_adapter.notifyDataSetChanged();
            GetWeeklyData(fromdate, finalenddate);
            MpChartDisplay();

          }
        }, year, month, day);
        dp.show();
      }
    });

    MpChartDisplay();

    Toast.makeText(getActivity(), String.valueOf(Constants.FULL_DATE_FORMAT.format(currentDate)), Toast.LENGTH_SHORT).show();
    Toast.makeText(getActivity(), String.valueOf(Constants.FULL_DATE_FORMAT.format(finalenddate)), Toast.LENGTH_SHORT).show();


    GetWeeklyData(currentDate, finalenddate);

    new Boom(btn_change_date);

    return root;
  }

  static int WeeklyTotal;

  public void GetWeeklyData(Date fromDate, Date toDate) {
    Weekly_histories = realm.where(Daily_history.class).between("datetime", fromDate, toDate).findAll();
    Log.i("WeeklyData", Weekly_histories.toString());
    System.out.println(Weekly_histories.toString());

//    WeeklyTotal = Weekly_histories.sum("water_intake_level").intValue();

    for (int i = 0; i < Weekly_histories.size(); i++) {
      DailyHistory dailyHistory = new DailyHistory();
      dailyHistory.setId(Weekly_histories.get(i).getId());
      dailyHistory.setDatetime(Weekly_histories.get(i).getDatetime());
      dailyHistory.setWater_intake_level(Weekly_histories.get(i).getWater_intake_level());
      Weekly_History.add(dailyHistory);
    }

    todays_history_rv_adapter = new todays_history_rv_adapter(Weekly_History, getActivity());
    RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager1);
    recyclerView.setNestedScrollingEnabled(false);
    recyclerView.setAdapter(todays_history_rv_adapter);

  }

  Date temp;
  String cdatedatabase, s, fdate;

  public void MpChartDisplay() {
//    weekly_histroy = realm.where(Daily_history.class).equalTo("date", spdate);

    RealmResults<Daily_history> WeeklyDistinctDataForChart = realm.where(Daily_history.class)
      .between("datetime", currentDate, finalenddate)
      .findAll();

    ArrayList<DailyHistory> abc = new ArrayList<>();
    temp = currentDate;
    s = Constants.DATE_FORMAT.format(temp);
    fdate = Constants.DATE_FORMAT.format(finalenddate);
    int WTotal = 0, tempt;

    Calendar start = Calendar.getInstance();
    Calendar end = Calendar.getInstance();
    start.setTime(currentDate);
    end.setTime(finalenddate);

    List<Integer> chartdata = new ArrayList<>();

    int total = 0;
    RealmResults<Daily_history> usersquery = realm.where(Daily_history.class).findAll();
    while (start.before(end)) {

      for (int i = 0; i < usersquery.size(); i++) {

        String str_date = Constants.DATE_FORMAT.format(usersquery.get(i).getDatetime());
        String currentdate = Constants.DATE_FORMAT.format(start.getTime());

        if (currentdate.equals(str_date)) {
          int temp = usersquery.get(i).getWater_intake_level();
          Log.d("usersquery", String.valueOf(temp) + "| Date :-" + String.valueOf(usersquery.get(i).getDatetime()));
          total = temp+total;
        }
      }
      Log.w("Date_&_Total",Constants.DATE_FORMAT.format(start.getTime())+" | "+String.valueOf(total));
      chartdata.add(total);
      total = 0;
      start.add(Calendar.DATE, 1);
    }

    realm.setAutoRefresh(true);
    weekly_total = new ArrayList<>();

    for (int j = 0;j<chartdata.size();j++){
      weekly_total.add(new BarEntry(j, chartdata.get(j)));
    }

    weekly_barChart.notifyDataSetChanged();
    weekly_barChart.invalidate();

    weekly_barDataSet = new BarDataSet(weekly_total, "Weekly Intake");
    weekly_barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
    weekly_barDataSet.setValueTextColor(Color.BLACK);
    weekly_barDataSet.setValueTextSize(12f);

    weekly_barData = new BarData(weekly_barDataSet);
    weekly_barChart.setFitBars(true);
    weekly_barChart.setData(weekly_barData);
    weekly_barChart.animate();
  }
}