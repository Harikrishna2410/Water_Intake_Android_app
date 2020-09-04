package com.example.waterintake;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.astritveliu.boom.Boom;
import com.example.waterintake.Modal_Classis.CustomWaterIntake_Pojo;
import com.example.waterintake.Modal_Classis.DailyHistory;
import com.example.waterintake.realm_db.Custom_water_intake;
import com.example.waterintake.realm_db.Daily_history;
import com.example.waterintake.realm_db.Users;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_fregment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_fregment extends Fragment {

  TextView intake_level_home_frag;
  public static TextView tv_intake_total_in_ml;
  static TextView tv_current_history;
  ConstraintLayout glass_water, bottle_water;
  WaveLoadingView waveLoadingView;
  int year, month, day;
  int intake;
  public sharedPreference sp;
  Realm realm;
  Number current_id;
  int img[] = {R.drawable.drink, R.drawable.water_bottle, R.drawable.small_waterbottle_1};
  int id[] = {1, 2, 3};
  ViewGroup root;
  RealmResults<Users> query;
  RecyclerView todays_history;
  public static todays_history_rv_adapter todays_history_rv_adapter;
  RealmResults<Daily_history> daily_histories;
  static String spdate;
  static int percent;
  static Date Todays_date = null;
  private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
  int total = 0;

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;
  private PersianDatePickerDialog persianDatePickerDialog = null;
  private Context context;

  public static int Today_intake_total;


  ArrayList<DailyHistory> today_history = new ArrayList<>();

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment home_fregment.
   */

  public static home_fregment instace;


  // TODO: Rename and change types and number of parameters
  public static home_fregment newInstance(String param1, String param2) {
    home_fregment fragment = new home_fregment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  public home_fregment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  public static home_fregment getInstace() {
    return instace;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    sp = new sharedPreference(getActivity());

    instace = this;

    // Inflate the layout for this fragment
    root = (ViewGroup) inflater.inflate(R.layout.fragment_home_fregment, container, false);
    glass_water = root.findViewById(R.id.card_glass_water_drink_rv);
    waveLoadingView = root.findViewById(R.id.waveLoadingView);
    WaveVariable.setWaveLoadingView(waveLoadingView);
    intake_level_home_frag = root.findViewById(R.id.intake_level_home_frag);
    todays_history = root.findViewById(R.id.todays_histroy);
    tv_current_history = root.findViewById(R.id.curent_history);
    tv_intake_total_in_ml = root.findViewById(R.id.tv_intake_total_in_ml);
    DecimalFormat df2 = new DecimalFormat("#.##");


    year = Constants.calender.get(Calendar.YEAR);
    month = Constants.calender.get(Calendar.MONTH);
    day = Constants.calender.get(Calendar.DAY_OF_MONTH);
//    String finaldate = Constants.DATE_FORMAT.format(calendar.getTime());

    spdate = sp.client_pref.getString("date", null);


    final RealmConfiguration configuration = new RealmConfiguration.Builder().name("sample.realm").schemaVersion(1).build();
    Realm.setDefaultConfiguration(configuration);
    Realm.getInstance(configuration);

    Realm.init(getActivity());

    realm = Realm.getDefaultInstance();
    realm.refresh();

    RealmResults<Users> query = realm.where(Users.class).equalTo("id", 1).findAll();

    for (int i = 0; i < query.size(); i++) {
      intake = query.get(i).getGoal();
    }


    RecyclerView rv = root.findViewById(R.id.custom_intake);
    rv.setHasFixedSize(true);

    sp.editor_client_pref.putBoolean("deleteBtnVisible",false);
    sp.editor_client_pref.commit();

//  STARTS FIRST RECYCLERVIEW ADAPTER DATA---------------------------------------------------------------------------------------------

    //region

    ArrayList<CustomWaterIntake_Pojo> customWaterIntake_pojo = new ArrayList<>();

    RealmResults<Custom_water_intake> custom_water_intakes_query = realm.where(Custom_water_intake.class).findAll();
    for (int i = 0; i < custom_water_intakes_query.size(); i++) {
      CustomWaterIntake_Pojo pojo = new CustomWaterIntake_Pojo();
      pojo.setId(custom_water_intakes_query.get(i).getId());
      pojo.setCustom_intake(custom_water_intakes_query.get(i).getCustom_intake());
      customWaterIntake_pojo.add(pojo);
    }
    customWaterIntake_pojo.add(new CustomWaterIntake_Pojo(5000, 20000));
    Custom_intakes_recycler_view_adapter adapter = new Custom_intakes_recycler_view_adapter(customWaterIntake_pojo, getActivity());
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    rv.setLayoutManager(layoutManager);
    rv.setAdapter(adapter);

    //endregion

//  ENDS FIRST RECYCLERVIEW ADAPTER DATA---------------------------------------------------------------------------------------------

    try {
      Todays_date = dateFormat.parse(spdate);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Log.i("todaysDate", String.valueOf(Constants.calender.getTime()));


//  STARTS SECOND RECYCLERVIEW ADAPTER DATA---------------------------------------------------------------------------------------------

    getTodaysData();

//  ENDS SECOND RECYCLERVIEW ADAPTER DATA---------------------------------------------------------------------------------------------


    sp = new sharedPreference(getActivity());

    intake_level_home_frag.setText(intake + "\nml");

    waveloadingprogress();

//    showRealmData();
    return root;
  }

  public void getTodaysData(){
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
    todays_history.setLayoutManager(layoutManager1);
    todays_history.setNestedScrollingEnabled(false);
    todays_history.setAdapter(todays_history_rv_adapter);
  }

  public void customIntakeAlert() {
    final Dialog d = new Dialog(getActivity());
    d.setTitle("NumberPicker");
    d.requestWindowFeature(Window.FEATURE_NO_TITLE);
    d.setContentView(R.layout.dialog_manual_intake_input_of_screen_3);
    d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    d.getWindow().setGravity(Gravity.CENTER);
    EditText custom_intake = d.findViewById(R.id.manual_intake);
    CardView btn_done_dialog = d.findViewById(R.id.card_button_leta_instake_screen_4);
    new Boom(btn_done_dialog);

    TextView dialog_title = d.findViewById(R.id.activity_level_title);
    dialog_title.setText("Enter the amount of water you drank in Leters");

    btn_done_dialog.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        int in = Integer.parseInt(custom_intake.getText().toString());

        insertData(in);

        Number current_id = realm.where(Custom_water_intake.class).max("id");

        int nextId;
        if (current_id == null) {
          nextId = 1;
        } else {
          nextId = current_id.intValue() + 1;
        }

        realm.beginTransaction();
        Custom_water_intake cs_intake = new Custom_water_intake(nextId, in);
        realm.copyToRealm(cs_intake);
        realm.commitTransaction();
        waveloadingprogress();
        todays_history_rv_adapter.notifyDataSetChanged();

        d.dismiss();
      }
    });
    d.show();
  }


  public void waveloadingprogress() {

    realm = Realm.getDefaultInstance();

    query = realm.where(Users.class).equalTo("id", 1).findAll();

    for (int i = 0; i < query.size(); i++) {
      intake = query.get(i).getGoal();
    }

    RealmResults<Daily_history> usersquery = realm.where(Daily_history.class).findAll();

    for (int i = 0; i < usersquery.size(); i++) {
      String str_date = Constants.DATE_FORMAT.format(usersquery.get(i).getDatetime());
      String currentdate = Constants.DATE_FORMAT.format(Constants.calender.getTime());

      if (currentdate.equals(str_date)) {

        int temp = usersquery.get(i).getWater_intake_level();
        Log.d("totall", String.valueOf(temp));

        total = temp + total;
      }
//      Log.i("strdate", str_date + " | " + currentdate);
    }

    Today_intake_total = total;

//    sp.editor_client_pref.putInt("Todays_Total_Intakes",total);
//    sp.editor_client_pref.commit();
//    Log.i("Total",String.valueOf(total));

    tv_intake_total_in_ml.setText(String.valueOf(total) + "\nml");

    percent = (total * 100) / intake;
    Log.d("percent", String.valueOf(percent));


    waveLoadingView = WaveVariable.getWaveLoadingView();
    waveLoadingView.setCenterTitle(percent + " %");
    waveLoadingView.refreshDrawableState();
    if (percent >= 100) {
      waveLoadingView.refreshDrawableState();
      waveLoadingView.setProgressValue(100);
    } else {
      waveLoadingView.setProgressValue(percent);
    }

  }


  public void insertData(int glass_w) {

    current_id = realm.where(Daily_history.class).max("id");

    int nextId;
    if (current_id == null) {
      nextId = 1;
    } else {
      nextId = current_id.intValue() + 1;
    }

    String StringTime = Constants.TIME_FORMAT.format(Constants.calender.getTimeInMillis());
    Date d1 = null, d2 = null;
    try {
      d1 = Constants.FULL_DATE_FORMAT.parse(String.valueOf(Constants.calender.getTimeInMillis()));
      d2 = dateFormat.parse(StringTime);
    } catch (ParseException e) {
      e.printStackTrace();
      Log.e("LogError", e.getMessage());
    }

    Toast.makeText(getActivity(), d1.toString() + " / " + d2.toString(), Toast.LENGTH_SHORT).show();

    realm.beginTransaction();
    Daily_history d = new Daily_history(nextId, d1, glass_w);
    realm.copyToRealm(d);
    realm.commitTransaction();

    Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_SHORT).show();

  }


  private void showRealmData() {
    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat timeformat = new SimpleDateFormat("h:mm a");
    List<Daily_history> dailyGoals = realm.where(Daily_history.class).findAll();

    for (int i = 0; i < dailyGoals.size(); i++) {

      String date = dateformat.format(dailyGoals.get(i).getDatetime());
      String time = timeformat.format(dailyGoals.get(i).getDatetime());
      Log.d("date", "date:- " + date + " Time -: " + time + " Intakes :- " + dailyGoals.get(i).getWater_intake_level());


//      String formateddate = format.format(date);
//      Log.d("result", "id :- " + dailyGoals.get(i).getId() + " Date :- " +  dailyGoals.get(i).getDatetime());
//      data.add(dailyGoals.get(i).getDatetime());

//      Toast.makeText(getActivity(), "ID: " + dailyGoals.get(i).getId() + "\nDate: " + dailyGoals.get(i).getDate() +"\nTime"+ dailyGoals.get(i).getTime() + "\nintake: " + dailyGoals.get(i).getWater_intake_level(), Toast.LENGTH_SHORT).show();
    }
  }

}