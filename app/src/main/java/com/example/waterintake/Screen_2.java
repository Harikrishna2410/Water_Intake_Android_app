package com.example.waterintake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

import java.text.DecimalFormat;

import lib.kingja.switchbutton.SwitchMultiButton;

public class Screen_2 extends AppCompatActivity {

  ConstraintLayout weight_button,activity_button,weather_button;
  TextView user_weight,user_activity_level,weather,Ruler_Title,activity_level_title,gender_warning;
  EditText user_name;
  SwitchMultiButton gender;
  RulerValuePicker RVP;
  ListView activity_level_list,weather_list;
  CardView calculate_intake_btn;
  String sex,weight;
  sharedPreference sp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_2);
    controlbinding();
    user_weight.setHint("Weight in Kg");
    final String[] activity_level_arrayList,weather_List;
    sp = new sharedPreference(Screen_2.this);

    weight_button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        final Dialog d = new Dialog(Screen_2.this);
        d.setTitle("NumberPicker");
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.weight_dialog);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.getWindow().setGravity(Gravity.BOTTOM);
        RVP = d.findViewById(R.id.ruler_picker);
        Ruler_Title = d.findViewById(R.id.Ruler_Title);
        RVP.setValuePickerListener(new RulerValuePickerListener() {
          @Override
          public void onValueChange(final int selectedValue) {
//            Toast.makeText(Screen_2.this,Integer.toString(selectedValue), Toast.LENGTH_SHORT).show();
//            user_weight.setText(Integer.toString(selectedValue)+" Kg");
          }

          @Override
          public void onIntermediateValueChange(final int selectedValue) {
            user_weight.setText(Integer.toString(selectedValue)+" Kg");
            user_weight.setError(null);
            weight = Integer.toString(selectedValue);
            Ruler_Title.setText("Your Weight "+ Integer.toString(selectedValue) +" Kg");

          }
        });

        d.show();

      }
    });

    activity_level_arrayList = getResources().getStringArray(R.array.activity_level);
    weather_List = getResources().getStringArray(R.array.weather);
    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,activity_level_arrayList);
    final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,weather_List);


    activity_button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        final Dialog d = new Dialog(Screen_2.this);
        d.setTitle("NumberPicker");
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.activity_level_dialog);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.getWindow().setGravity(Gravity.BOTTOM);
        activity_level_list = d.findViewById(R.id.activity_level_list);
        activity_level_list.setAdapter(adapter);

        activity_level_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String activity = activity_level_arrayList[i];
//            Toast.makeText(Screen_2.this, activity, Toast.LENGTH_SHORT).show();
            user_activity_level.setText(activity);
            user_activity_level.setError(null);
            d.dismiss();
          }
        });
        d.show();
      }
    });

    weather_button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        final Dialog d = new Dialog(Screen_2.this);
        d.setTitle("NumberPicker");
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.activity_level_dialog);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.getWindow().setGravity(Gravity.BOTTOM);
        weather_list = d.findViewById(R.id.activity_level_list);
        weather_list.setAdapter(adapter1);
        activity_level_title = d.findViewById(R.id.activity_level_title);
        activity_level_title.setText("Choose Weather");
        weather_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String activity = weather_List[i];
//            Toast.makeText(Screen_2.this, activity, Toast.LENGTH_SHORT).show();
            weather.setText(activity);
            weather.setError(null);
            d.dismiss();
          }
        });
        d.show();
      }
    });

    gender.clearSelection();

    gender.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
      @Override
      public void onSwitch(int position, String tabText) {
        sex = tabText ;
        gender_warning.setVisibility(View.GONE);
      }
    });

    calculate_intake_btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        Boolean validation = true;
        String uname = user_name.getText().toString();
        String uweight = user_weight.getText().toString();
        String uactivity = user_activity_level.getText().toString();
        String uweather = weather.getText().toString();

        if (uname.isEmpty()){
//          user_name.requestFocus();
          user_name.setError("Field Should not be Empty");
          validation = false;
        }
        else {
          validation = true;
          user_name.setError(null);
        }
        if (sex == null){
//          user_name.requestFocus();
            gender_warning.setVisibility(View.VISIBLE);
          validation = false;
        }
        else {
          validation = true;
          gender_warning.setVisibility(View.GONE);
        }
        if (uweight.isEmpty()){
//          user_weight.requestFocus();
          user_weight.setError("Field Should not be Empty");
          validation = false;
        }
        else {
          validation = true;
          user_weight.setError(null);
        }
        if (uactivity.isEmpty()){
//          user_activity_level.requestFocus();
          user_activity_level.setError("Field Should not be Empty");
          validation = false;

        }
        else {
          validation = true;
          user_activity_level.setError(null);
        }
        if (uweather.isEmpty()){
//          weather.requestFocus();
          validation = false;
          weather.setError("Field Should not be Empty");
        }
        else {
          validation = true;
          weather.setError(null);
        }

        if (validation.equals(true)){
//          Toast.makeText(Screen_2.this, "Data Stored In SharedPreference", Toast.LENGTH_SHORT).show();
          sp.editor_client_pref.putString("username",uname);
          sp.editor_client_pref.putString("gender",sex);
          sp.editor_client_pref.putString("weight",weight);
          sp.editor_client_pref.putString("activity_level",uactivity);
          sp.editor_client_pref.putString("weather",uweather);
          sp.editor_client_pref.putFloat("todays_intake",0);

          int resultInMl;
          float genderMultiplier = (sex == "Male") ? 20 : 10;
          float resultInOunces = Integer.parseInt(weight) * genderMultiplier / 28.3f;
          float activityAdder = 0;

          switch (user_activity_level.getText().toString()){
            case "Steady":
              activityAdder = 100;
              break;
            case "Some-what Active":
              activityAdder = 150;
              break;
            case "Totally Active":
              activityAdder = 200;
              break;

          }

          DecimalFormat df = new DecimalFormat("#.##");


          resultInOunces += activityAdder;
          resultInMl = Math.round(resultInOunces * 500 / 40);
          double resultInL = (float) (resultInMl * 0.001);

          float Intakes =  Float.valueOf(df.format(resultInL));

          Toast.makeText(Screen_2.this, "Calculated Intakes In Liter is "+Intakes+"L", Toast.LENGTH_SHORT).show();
          sp.editor_client_pref.putFloat("Intakes",Intakes);
          sp.editor_client_pref.commit();

          Intent intent = new Intent(Screen_2.this,Screen_3.class);
          startActivity(intent);

        }

      }
    });

  }

  public void controlbinding(){

    weight_button = findViewById(R.id.weight_button);
    activity_button = findViewById(R.id.activity_button);
    weather_button = findViewById(R.id.weather_button);

    user_weight = findViewById(R.id.user_weight);
    user_activity_level =findViewById(R.id.uesr_activity_level);
    weather = findViewById(R.id.weather);
    gender_warning = findViewById(R.id.gender_warning);

    user_name = findViewById(R.id.user_name);

    gender = findViewById(R.id.gender);

    calculate_intake_btn = findViewById(R.id.card_button_leta_instake_screen_4);

  }
}