package com.example.waterintake;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.astritveliu.boom.Boom;
import com.example.waterintake.realm_db.Daily_goal;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SplittableRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_fregment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_fregment extends Fragment implements View.OnClickListener {

  ImageView back_press;
  TextView full_date, intake_level_home_frag;
  ConstraintLayout date_change, glass_water, bottle_water, custom_water;
  WaveLoadingView waveLoadingView;
  Context ctx;
  int year, month, day;
  float glass_w, glass_b, total, intake;
  sharedPreference sp;
  Daily_goal daily_goal;
  Realm realm;
  Number current_id;

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;
  private PersianDatePickerDialog persianDatePickerDialog = null;

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment home_fregment.
   */


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


//        controllBinding();

  }



  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home_fregment, container, false);
    date_change = root.findViewById(R.id.date_change_home_frag);
    glass_water = root.findViewById(R.id.glass_water_drink_home_fragment);
    bottle_water = root.findViewById(R.id.bottle_water_drink_home_fragment);
    custom_water = root.findViewById(R.id.custom_water_drink_home_fragment);
    waveLoadingView = root.findViewById(R.id.waveLoadingView);
    back_press = root.findViewById(R.id.back_press_home_screen_fragment);
    full_date = root.findViewById(R.id.full_date);
    intake_level_home_frag = root.findViewById(R.id.intake_level_home_frag);

    final RealmConfiguration configuration = new RealmConfiguration.Builder().name("sample.realm").schemaVersion(1).build();
    Realm.setDefaultConfiguration(configuration);
    Realm.getInstance(configuration);

    Realm.init(getActivity());

    realm = Realm.getDefaultInstance();

    sp = new sharedPreference(getActivity());

    date_change.setOnClickListener(this);

    intake = sp.client_pref.getFloat("Intakes", 0);


    intake_level_home_frag.setText(intake + "L");
    glass_water.setOnClickListener(new View.OnClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.O)
      @Override
      public void onClick(View view) {
        glass_w = (float) 0.25;
        insertData(glass_w);

      }
    });

    bottle_water.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        glass_b = (float) 0.5;
        insertData(glass_b);
      }
    });

    custom_water.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showRealmData();
      }
    });

    new Boom(date_change);
    new Boom(glass_water);
    new Boom(bottle_water);
    new Boom(custom_water);
    new Boom(back_press);

    Calendar c = Calendar.getInstance();
    year = c.get(Calendar.YEAR);
    month = c.get(Calendar.MONTH);
    day = c.get(Calendar.DAY_OF_MONTH);

    SimpleDateFormat f = new SimpleDateFormat("LLLL", Locale.getDefault());
    String mon = f.format(c.getTime());
    full_date.setText(day + "th " + mon + " " + year);

    return root;
  }

  public void insertData(float glass_w){

//    sp.editor_client_pref.putFloat("todays_intake", 0);
    total = sp.client_pref.getFloat("todays_intake", 0);

    total = total += glass_w;
    sp.editor_client_pref.putFloat("todays_intake", total);
    sp.editor_client_pref.commit();


    Date date = Calendar.getInstance().getTime();
    SimpleDateFormat dateee = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//        Toast.makeText(getActivity(), String.valueOf(total), Toast.LENGTH_SHORT).show();
    current_id = realm.where(Daily_goal.class).max("id");

    int nextId;
    if (current_id == null) {
      nextId = 1;
    } else {
      nextId = current_id.intValue() + 1;
    }

    String StringDate = dateee.format(date);

    realm.beginTransaction();
    Daily_goal d = new Daily_goal(nextId, StringDate, sp.client_pref.getFloat("Intakes", 0), total);
    realm.copyToRealm(d);
    realm.commitTransaction();

    Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_SHORT).show();

  };

  private void showRealmData() {
    List<Daily_goal> dailyGoals = realm.where(Daily_goal.class).findAll();
    for (int i = 0; i < dailyGoals.size(); i++) {
      Toast.makeText(getActivity(), "ID: " + dailyGoals.get(i).getId() + " \nGoal: " + dailyGoals.get(i).getGoal() + "\nDate: " + dailyGoals.get(i).getDate() + "\nintake: " + dailyGoals.get(i).getWater_intake_level(), Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onClick(View view) {

    switch (view.getId()) {

      case R.id.date_change_home_frag:

        DatePickerDialog dp = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
          @RequiresApi(api = Build.VERSION_CODES.O)
          @Override
          public void onDateSet(DatePicker datePicker, int year, int month, int day) {

            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);

            String date = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
            String d[] = date.split(" ");
            String m = d[2];
//                        Toast.makeText(getActivity(), m, Toast.LENGTH_SHORT).show();
            full_date.setText(day + "th " + m + " " + year);
          }
        }, year, month, day);
        dp.show();

        break;

    }

  }

}