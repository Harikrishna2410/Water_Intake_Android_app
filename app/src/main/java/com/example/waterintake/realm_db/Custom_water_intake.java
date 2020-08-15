package com.example.waterintake.realm_db;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class Custom_water_intake extends RealmObject {

  @PrimaryKey
  int id;
  int custom_intake;


  public Custom_water_intake(int id, int custom_intake) {
    this.id = id;
    this.custom_intake = custom_intake;
  }


  public Custom_water_intake() {
  }

  public Custom_water_intake(FragmentActivity activity, int nextId, String valueOf, int small_waterbottle) {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCustom_intake() {
    return custom_intake;
  }

  public void setCustom_intake(int custom_intake) {
    this.custom_intake = custom_intake;
  }
}
