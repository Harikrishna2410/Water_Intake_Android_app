package com.example.waterintake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astritveliu.boom.Boom;

public class Screen_3 extends AppCompatActivity {

  TextView main_title,sub_title_1,sub_title_2;
  EditText manual_intake;
  CardView btn,btn_done_dialog;
  sharedPreference sp;
  float Intakes;
  ImageView back_press;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_3);
    controlBinding();
    sp = new sharedPreference(Screen_3.this);

    Intakes = sp.client_pref.getFloat("Intakes",0);

    main_title.setText(Intakes+"L");
    sub_title_1.setText("Your Daily Intake is "+Intakes+"L");

    new Boom(main_title);
    new Boom(btn);

    back_press.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackPressed();
      }
    });

    // region Main Title Click Manual Intake Dialog
    main_title.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(Screen_3.this, String.valueOf(Intakes), Toast.LENGTH_SHORT).show();
        final Dialog d = new Dialog(Screen_3.this);
        d.setTitle("NumberPicker");
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_manual_intake_input_of_screen_3);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.getWindow().setGravity(Gravity.CENTER);
        manual_intake = d.findViewById(R.id.manual_intake);
        btn_done_dialog = d.findViewById(R.id.card_button_leta_instake_screen_4);
        new Boom(btn_done_dialog);

        btn_done_dialog.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            manual_intake.getText().toString();
            sp.editor_client_pref.putFloat("Intakes",Float.valueOf(manual_intake.getText().toString()));
            sp.editor_client_pref.commit();

            Intakes = sp.client_pref.getFloat("Intakes",0);
            main_title.setText(Intakes+"L");
            sub_title_1.setText("Your Daily Intake is "+Intakes+"L");

            d.dismiss();
          }
        });
        d.show();
      }
    });
// endregion

    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(Screen_3.this,Screen_4.class);
        startActivity(intent);
      }
    });
  }

  public void controlBinding(){
    main_title = findViewById(R.id.inteke_level_main_title_screen_3);
    sub_title_1 = findViewById(R.id.sub_title_1);
    sub_title_2 = findViewById(R.id.sub_title_2);
    btn = findViewById(R.id.card_button_leta_instake_screen_4);
    back_press = findViewById(R.id.back_press_screen_3);

  }
}