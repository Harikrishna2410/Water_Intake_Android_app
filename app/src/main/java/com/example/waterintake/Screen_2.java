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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.reflect.Array;
import java.util.ArrayList;

import lib.kingja.switchbutton.SwitchMultiButton;

public class Screen_2 extends AppCompatActivity {

  ConstraintLayout weight_button,activity_button,weather_button;
  TextView user_weight,user_activity_level,weather;
  EditText user_name;
  SwitchMultiButton gender;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_2);
    controlbinding();

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