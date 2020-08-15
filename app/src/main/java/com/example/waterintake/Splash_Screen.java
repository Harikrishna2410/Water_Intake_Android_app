package com.example.waterintake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.waterintake.realm_db.Custom_water_intake;
import com.example.waterintake.realm_db.Users;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class Splash_Screen extends AppCompatActivity {

  Realm realm;
  sharedPreference sp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash__screen);

    sp = new sharedPreference(this);

    sp.editor_client_pref.putBoolean("refresh_home",false);
    sp.editor_client_pref.commit();

    final RealmConfiguration configuration = new RealmConfiguration.Builder().name("sample.realm").schemaVersion(1).build();
    Realm.setDefaultConfiguration(configuration);
    Realm.getInstance(configuration);

    Realm.init(this);

    realm = Realm.getDefaultInstance();

    RealmResults<Users> query = realm.where(Users.class).equalTo("id", 1).findAll();

    if (query.size() == 0){
      Toast.makeText(this, "Database Empty", Toast.LENGTH_SHORT).show();
      Number current_id = realm.where(Custom_water_intake.class).max("id");

      int nextId;
      if (current_id == null) {
        nextId = 1;
      } else {
        nextId = current_id.intValue() + 1;
      }

      realm.beginTransaction();
      Custom_water_intake custom_water_intake = new Custom_water_intake();
      custom_water_intake.setCustom_intake(250);
      custom_water_intake.setId(nextId);
      realm.copyToRealm(custom_water_intake);
      realm.commitTransaction();

      Number current_id1 = realm.where(Custom_water_intake.class).max("id");

      int nextId1;
      if (current_id1 == null) {
        nextId1 = 1;
      } else {
        nextId1 = current_id1.intValue() + 1;
      }

      realm.beginTransaction();
      Custom_water_intake custom_water_intake1 = new Custom_water_intake();
      custom_water_intake1.setCustom_intake(500);
      custom_water_intake1.setId(nextId1);
      realm.copyToRealm(custom_water_intake1);
      realm.commitTransaction();

      Number current_id2 = realm.where(Custom_water_intake.class).max("id");

      int nextId2;
      if (current_id2 == null) {
        nextId2 = 1;
      } else {
        nextId2 = current_id2.intValue() + 1;
      }


      realm.beginTransaction();
      Custom_water_intake custom_water_intake2 = new Custom_water_intake();
      custom_water_intake2.setCustom_intake(750);
      custom_water_intake2.setId(nextId2);
      realm.copyToRealm(custom_water_intake2);
      realm.commitTransaction();

      startActivity(new Intent(this,Screen_1.class));

    } else {

      startActivity(new Intent(this,Home_screen.class));
    }

  }
}