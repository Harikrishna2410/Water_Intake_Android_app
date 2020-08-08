package com.example.waterintake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.astritveliu.boom.Boom;

public class Screen_4 extends AppCompatActivity {

  CardView btn;
  ImageView back_press;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_4);

    btn = findViewById(R.id.card_button_leta_instake_screen_4);
    back_press = findViewById(R.id.back_press_screen_4);
    new Boom(btn);
    new Boom(back_press);


    back_press.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackPressed();
      }
    });

    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(Screen_4.this,Home_screen.class));
      }
    });


  }

}