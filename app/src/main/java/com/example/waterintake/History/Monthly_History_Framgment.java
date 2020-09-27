package com.example.waterintake.History;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.example.waterintake.Constant_Classes.MPChart;
import com.example.waterintake.Constants;
import com.example.waterintake.Modal_Classis.DailyHistory;
import com.example.waterintake.R;
import com.example.waterintake.WaveLoadingView;
import com.example.waterintake.WaveVariable;
import com.example.waterintake.realm_db.Daily_history;
import com.example.waterintake.todays_history_rv_adapter;
import com.github.mikephil.charting.charts.BarChart;
import com.kal.rackmonthpicker.RackMonthPicker;
import com.kal.rackmonthpicker.listener.DateMonthDialogListener;
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Monthly_History_Framgment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Monthly_History_Framgment extends Fragment {
  ViewGroup root;
  ConstraintLayout date_change_monthly_history_btn;
  TextView tv_month_selection;
  BarChart monthly_barChart;
  RecyclerView rv_monthly_history;
  Date fromDate, toDate;
  todays_history_rv_adapter todays_history_rv_adapter;
  Realm realm;
  MPChart mpChart;
  RealmResults<Daily_history> Monthly_histories;
  ArrayList<DailyHistory> Monthly_History = new ArrayList<>();
  ;
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;


  public Monthly_History_Framgment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment Monthly_History_Framgment.
   */
  // TODO: Rename and change types and number of parameters
  public static Monthly_History_Framgment newInstance(String param1, String param2) {
    Monthly_History_Framgment fragment = new Monthly_History_Framgment();
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

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    root = (ViewGroup) inflater.inflate(R.layout.fragment_monthly__history__framgment, container, false);
    date_change_monthly_history_btn = root.findViewById(R.id.date_change_monthly_history);
    tv_month_selection = root.findViewById(R.id.tv_month_selection);
    monthly_barChart = root.findViewById(R.id.monthly_barChart);
    rv_monthly_history = root.findViewById(R.id.rv_monthly_history);
    realm = Realm.getDefaultInstance();

    WaveVariable.setMonthly_Barchart(monthly_barChart);

    Date date = new Date();
    Date d;
    Calendar sDate = Calendar.getInstance();
    sDate.setTime(date);
    d = sDate.getTime();

    int month = d.getMonth() + 1;
    Log.d("Monthhhhh", String.valueOf(month));

    Calendar c = Calendar.getInstance();
    c.set(Calendar.MONTH, month - 1);
    c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DATE));
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MILLISECOND, 0);
    c.add(Calendar.DATE, 0);
    fromDate = c.getTime();

    Calendar eDate = Calendar.getInstance();
    eDate.set(Calendar.MONTH, month - 1);
    eDate.set(Calendar.DAY_OF_MONTH, eDate.getActualMaximum(Calendar.DATE));
    eDate.set(Calendar.HOUR_OF_DAY, 0);
    eDate.set(Calendar.MINUTE, 0);
    eDate.set(Calendar.SECOND, 0);
    eDate.set(Calendar.MILLISECOND, 0);
    toDate = eDate.getTime();

    Log.d("DateTimeFromDate", fromDate.toString());

    GetMonthlyData(fromDate, toDate);
    tv_month_selection.setText(String.valueOf(Constants.MONTH_FORMAT.format(d)));

    date_change_monthly_history_btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {


        new RackMonthPicker(getActivity())
          .setLocale(Locale.ENGLISH)
          .setPositiveButton(new DateMonthDialogListener() {
            @Override
            public void onDateMonth(int month, int startDate, int endDate, int year, String monthLabel) {

              Calendar sDate = Calendar.getInstance();
              sDate.set(Calendar.MONTH, month - 1);
              sDate.set(Calendar.DAY_OF_MONTH, startDate);
              sDate.set(Calendar.HOUR_OF_DAY, 0);
              sDate.set(Calendar.MINUTE, 0);
              sDate.set(Calendar.SECOND, 0);
              sDate.set(Calendar.MILLISECOND, 0);
              sDate.add(Calendar.DATE, 0);
              fromDate = sDate.getTime();

              Calendar eDate = Calendar.getInstance();
              eDate.set(Calendar.MONTH, month);
              eDate.set(Calendar.DAY_OF_MONTH, endDate);
              eDate.set(Calendar.HOUR_OF_DAY, 0);
              eDate.set(Calendar.MINUTE, 0);
              eDate.set(Calendar.SECOND, 0);
              eDate.set(Calendar.MILLISECOND, 0);
              eDate.add(Calendar.DATE, 0);
              toDate = eDate.getTime();

              Log.i("startdate&Enddate", fromDate + "|" + toDate);

              tv_month_selection.setText(Constants.MONTH_FORMAT.format(fromDate).toString());
              Monthly_History.clear();
              todays_history_rv_adapter.notifyDataSetChanged();
              GetMonthlyData(fromDate, toDate);
              MPChart.Monthly_MPChartDisplay(fromDate, toDate, "Monthly Intake");
            }
          })
          .setNegativeButton(new OnCancelMonthDialogListener() {
            @Override
            public void onCancel(AlertDialog dialog) {

            }
          }).show();


      }
    });

    MPChart.Monthly_MPChartDisplay(fromDate, toDate, "Monthly Intake");

    return root;
  }

  public void GetMonthlyData(Date fromDate, Date toDate) {
    Monthly_histories = realm.where(Daily_history.class).between("datetime", fromDate, toDate).findAll();
    Log.i("MonthlyData", Monthly_histories.toString());
    System.out.println(Monthly_histories.toString());

//    WeeklyTotal = Weekly_histories.sum("water_intake_level").intValue();

    for (int i = 0; i < Monthly_histories.size(); i++) {
      DailyHistory dailyHistory = new DailyHistory();
      dailyHistory.setId(Monthly_histories.get(i).getId());
      dailyHistory.setDatetime(Monthly_histories.get(i).getDatetime());
      dailyHistory.setWater_intake_level(Monthly_histories.get(i).getWater_intake_level());
      Monthly_History.add(dailyHistory);
    }

    todays_history_rv_adapter = new todays_history_rv_adapter(Monthly_History, getActivity());
    RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    rv_monthly_history.setLayoutManager(layoutManager1);
    rv_monthly_history.setNestedScrollingEnabled(false);
    rv_monthly_history.setAdapter(todays_history_rv_adapter);

  }

}