package com.example.waterintake;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.TypedArrayUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.astritveliu.boom.Boom;
import com.example.waterintake.realm_db.Custom_water_intake;
import com.example.waterintake.realm_db.Daily_history;
import com.example.waterintake.realm_db.Users;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
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
  int glass_w, glass_b, total, intake;
  sharedPreference sp;
  Realm realm;
  Number current_id;
  int custom_intake_id, custom_intakes;
  ImageView custom_intake_icon;
  String[] ml = {"250", "500", "750"};
  int img[] = {R.drawable.drink, R.drawable.water_bottle, R.drawable.small_waterbottle_1};
  int id[] = {1, 2, 3};
  ViewGroup root;
  RealmResults<Users> query;

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
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    root = (ViewGroup) inflater.inflate(R.layout.fragment_home_fregment, container, false);
    date_change = root.findViewById(R.id.date_change_home_frag);
    glass_water = root.findViewById(R.id.card_glass_water_drink_rv);
    bottle_water = root.findViewById(R.id.bottle_water_drink_home_fragment);
    custom_water = root.findViewById(R.id.custom_water_drink_home_fragment);
    waveLoadingView = root.findViewById(R.id.waveLoadingView);
    full_date = root.findViewById(R.id.full_date);
    intake_level_home_frag = root.findViewById(R.id.intake_level_home_frag);
    DecimalFormat df2 = new DecimalFormat("#.##");

    final RealmConfiguration configuration = new RealmConfiguration.Builder().name("sample.realm").schemaVersion(1).build();
    Realm.setDefaultConfiguration(configuration);
    Realm.getInstance(configuration);

    Realm.init(getActivity());

    realm = Realm.getDefaultInstance();

    date_change.setOnClickListener(this);

    RealmResults<Users> query = realm.where(Users.class).equalTo("id", 1).findAll();

    for (int i = 0; i < query.size(); i++) {
      intake = query.get(i).getGoal();
    }

    RecyclerView rv = root.findViewById(R.id.custom_intake);
    rv.setHasFixedSize(true);

    RealmResults<Custom_water_intake> custom_water_intakes_query = realm.where(Custom_water_intake.class).findAll();

    Custom_intakes_recycler_view_adapter adapter = new Custom_intakes_recycler_view_adapter(custom_water_intakes_query, getActivity());
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
    rv.setLayoutManager(layoutManager);
    rv.setAdapter(adapter);

    sp = new sharedPreference(getActivity());

    intake_level_home_frag.setText(intake + "\nml");
    
    waveloadingprogress(getActivity());

    if (sp.client_pref.getBoolean("refresh_home",false) == true)
    {
      sp.editor_client_pref.putBoolean("refresh_home",false);
      sp.editor_client_pref.commit();
    }
    else {
//      waveloadingprogress();
    }

    custom_water.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
//        showRealmData();


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

            d.dismiss();
          }
        });
        d.show();

      }
    });

    new Boom(date_change);
    new Boom(glass_water);
    new Boom(bottle_water);
    new Boom(custom_water);

    Calendar c = Calendar.getInstance();
    year = c.get(Calendar.YEAR);
    month = c.get(Calendar.MONTH);
    day = c.get(Calendar.DAY_OF_MONTH);

    full_date.setText(day + "/" + month + "/" + year);

    return root;
  }



  public void waveloadingprogress(Context ctx){

    final RealmConfiguration configuration = new RealmConfiguration.Builder().name("sample.realm").schemaVersion(1).build();
    Realm.setDefaultConfiguration(configuration);
    Realm.getInstance(configuration);

    Realm.init(ctx);

    realm = Realm.getDefaultInstance();

    query = realm.where(Users.class).equalTo("id", 1).findAll();

    for (int i = 0; i < query.size(); i++) {
      intake = query.get(i).getGoal();
    }

    RealmQuery<Daily_history> usersquery = realm.where(Daily_history.class);
    int results = usersquery.sum("water_intake_level").intValue();

//    Toast.makeText(getActivity(), String.valueOf(results), Toast.LENGTH_SHORT).show();

    int percent = (results * 100) / intake;

    Toast.makeText(ctx, String.valueOf(percent), Toast.LENGTH_SHORT).show();

//    waveLoadingView = root.findViewById(R.id.waveLoadingView);

    waveLoadingView.setCenterTitle(percent+" %");
    waveLoadingView.refreshDrawableState();
    if (percent >= 100){
      waveLoadingView.setProgressValue(100);
    }else{
      waveLoadingView.setProgressValue(percent);
    }

  }


  public void insertData(int glass_w) {


    long date = Calendar.getInstance().getTimeInMillis();
    SimpleDateFormat dateee = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

//        Toast.makeText(getActivity(), String.valueOf(total), Toast.LENGTH_SHORT).show();
    current_id = realm.where(Daily_history.class).max("id");

    int nextId;
    if (current_id == null) {
      nextId = 1;
    } else {
      nextId = current_id.intValue() + 1;
    }

    String StringDate = dateee.format(date);

    realm.beginTransaction();
    Daily_history d = new Daily_history(nextId, StringDate, glass_w);
    realm.copyToRealm(d);
    realm.commitTransaction();

    Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_SHORT).show();

  }

  ;

  private void showRealmData() {
    List<Daily_history> dailyGoals = realm.where(Daily_history.class).findAll();
    for (int i = 0; i < dailyGoals.size(); i++) {
      Toast.makeText(getActivity(), "ID: " + dailyGoals.get(i).getId() + "\nDate: " + dailyGoals.get(i).getDate() + "\nintake: " + dailyGoals.get(i).getWater_intake_level(), Toast.LENGTH_SHORT).show();
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

//                        Toast.makeText(getActivity(), m, Toast.LENGTH_SHORT).show();
            full_date.setText(day + "/" + month + "/" + year);
          }
        }, year, month, day);
        dp.show();

        break;

    }

  }

}