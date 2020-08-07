package com.example.waterintake;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.TextView;

public class Screen_1 extends AppCompatActivity {

  CardView btn;

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_1);
    controlBind();

    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(Screen_1.this ,Screen_2.class);
        startActivity(intent);
      }
    });

    Slide slide = new Slide();
    slide.setDuration(1000);
    getWindow().setEnterTransition(slide);

  }

  public void controlBind(){

    btn =(CardView) findViewById(R.id.Card_Button_Continue);

  }
}