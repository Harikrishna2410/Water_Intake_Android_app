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
import com.example.waterintake.realm_db.Users;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class Screen_3 extends AppCompatActivity {

  TextView main_title,sub_title_1,sub_title_2;
  EditText manual_intake;
  CardView btn,btn_done_dialog;
  sharedPreference sp;
  int Intakes;
  ImageView back_press;
  Realm realm;
  private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_3);
    controlBinding();
    sp = new sharedPreference(Screen_3.this);

    Calendar c = Calendar.getInstance();

    sp.editor_client_pref.putString("date",dateFormat.format(c.getTime()));
    sp.editor_client_pref.commit();
    final RealmConfiguration configuration = new RealmConfiguration.Builder().name("sample.realm").schemaVersion(1).build();
    Realm.setDefaultConfiguration(configuration);
    Realm.getInstance(configuration);

    Realm.init(Screen_3.this);

    realm = Realm.getDefaultInstance();

    RealmQuery<Users> query = realm.where(Users.class);
    query.equalTo("id",1);
    RealmResults<Users> results = query.findAll();

    for (int i = 0; i < results.size() ; i++){
      Intakes = results.get(i).getGoal();
      Toast.makeText(this, String.valueOf(Intakes), Toast.LENGTH_SHORT).show();
    }
//    Intakes = query.get()

    main_title.setText(Intakes+"ml");
    sub_title_1.setText("Your Daily Intake is "+Intakes+"ml");

    new Boom(main_title);
    new Boom(btn);


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
            int m_intake = Integer.parseInt(manual_intake.getText().toString());

            realm.beginTransaction();
            Users users = realm.where(Users.class).equalTo("id",1).findFirst();
            users.setGoal(m_intake);
            realm.copyToRealmOrUpdate(users);
            realm.commitTransaction();


//            sp.editor_client_pref.putFloat("Intakes",Float.valueOf(manual_intake.getText().toString()));
//            sp.editor_client_pref.commit();
//
//            Intakes = sp.client_pref.getFloat("Intakes",0);
            RealmResults<Users> query = realm.where(Users.class).equalTo("id", 1).findAll();

            for (int i = 0; i < query.size() ; i++){
              Intakes = query.get(i).getGoal();
            }
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

  }
}