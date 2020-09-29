package com.example.waterintake.History;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.waterintake.Constant_Classes.MPChart;
import com.example.waterintake.Constants;
import com.example.waterintake.Converter;
import com.example.waterintake.Modal_Classis.DailyHistory;
import com.example.waterintake.R;
import com.example.waterintake.WaveVariable;
import com.example.waterintake.home_fregment;
import com.example.waterintake.realm_db.Daily_history;
import com.example.waterintake.sharedPreference;
import com.example.waterintake.todays_history_rv_adapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Todays_History_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Todays_History_Fragment extends Fragment{
  ViewGroup root;
  todays_history_rv_adapter todays_history_rv_adapter;
  RealmResults<Daily_history> daily_histories;
  static String spdate;
  public sharedPreference sp;
  public static Realm realm;
  RecyclerView recyclerView;
  public static BarChart barChart;
  public static ArrayList<BarEntry> total;
  public static BarData barData;
  public static BarDataSet barDataSet;
  public static RealmQuery<Daily_history> usersquery;
  ArrayList<DailyHistory> today_history = new ArrayList<>();
  String Error = "Error in Todays History Fragment";
  static Context context;


  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public Todays_History_Fragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment Todays_History_Fragment.
   */
  // TODO: Rename and change types and number of parameters
  public static Todays_History_Fragment newInstance(String param1, String param2) {
    Todays_History_Fragment fragment = new Todays_History_Fragment();
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
    root = (ViewGroup) inflater.inflate(R.layout.fragment_todays__history_, container, false);
    recyclerView = root.findViewById(R.id.rv_today_history);
    barChart = root.findViewById(R.id.barChart);
    context = getActivity();

    WaveVariable.setTodays_Barchart(barChart);

    realm = Realm.getDefaultInstance();
    sp = new sharedPreference(getActivity());

    sp.editor_client_pref.putBoolean("deleteBtnVisible",true);
    sp.editor_client_pref.commit();

    try {

      daily_histories = realm.where(Daily_history.class).findAll();
      Log.d("trrrue", String.valueOf(daily_histories));

      for (int i = 0; i < daily_histories.size(); i++) {
        DailyHistory dailyHistory = new DailyHistory();

        String date = Constants.DATE_FORMAT.format(daily_histories.get(i).getDatetime());
        String time = Constants.TIME_FORMAT.format(daily_histories.get(i).getDatetime());
        String currentdate = Constants.DATE_FORMAT.format(Constants.calender.getTime());
        if (date.equals(currentdate)) {
          Log.d("trrrue", String.valueOf(daily_histories.get(i)));
          dailyHistory.setId(daily_histories.get(i).getId());
          dailyHistory.setDatetime(daily_histories.get(i).getDatetime());
          dailyHistory.setWater_intake_level(daily_histories.get(i).getWater_intake_level());
          today_history.add(dailyHistory);
        }
      }

      todays_history_rv_adapter = new todays_history_rv_adapter(today_history, getActivity());
      RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
      recyclerView.setLayoutManager(layoutManager1);
      recyclerView.setNestedScrollingEnabled(false);
      recyclerView.setAdapter(todays_history_rv_adapter);


    }catch (Exception e){
      Log.e(Error,e.getMessage());
    }

//    MpChartDisplay();
    MPChart.Todays_MPChartDisplay(getActivity());

    return root;
  }



  public static void MpChartDisplay(){

    int results = home_fregment.Today_intake_total;
    realm.setAutoRefresh(true);

    total = new ArrayList<>();
    total.add(new BarEntry(0, results));

    barChart.notifyDataSetChanged();
    barChart.invalidate();


    barDataSet = new BarDataSet(total,"Todays Total Intake");
    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
    barDataSet.setValueTextColor(Color.BLACK);
    barDataSet.setValueTextSize(16f);

    barData = new BarData(barDataSet);
    barChart.setFitBars(true);
    barChart.setData(barData);
    barChart.animate();
  }

}