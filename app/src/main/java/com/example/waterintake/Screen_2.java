package com.example.waterintake;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

import lib.kingja.switchbutton.SwitchMultiButton;

public class Screen_2 extends AppCompatActivity {

  ConstraintLayout weight_button,activity_button,weather_button;
  TextView user_weight,user_activity_level,weather,Ruler_Title;
  EditText user_name;
  SwitchMultiButton gender;
  RulerValuePicker RVP;
  ListView activity_level_list;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_2);
    controlbinding();
    user_weight.setText("Weight in Kg");
    final String[] activity_level_arrayList;

//    final int w = Integer.parseInt(user_weight.toString());

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
            Ruler_Title.setText("Your Weight "+ Integer.toString(selectedValue) +" Kg");

          }
        });

        d.show();

      }
    });

    activity_level_arrayList = getResources().getStringArray(R.array.activity_level);
    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,activity_level_arrayList);


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
          }
        });

        d.show();



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

    user_name = findViewById(R.id.user_name);

    gender = findViewById(R.id.gender);

  }
}