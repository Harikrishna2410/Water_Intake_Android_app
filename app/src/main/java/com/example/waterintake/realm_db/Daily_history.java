package com.example.waterintake.realm_db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Daily_history extends RealmObject {

  @PrimaryKey
  private int id ;
  private String date;
  private int water_intake_level;

  public Daily_history() {
  }

  public Daily_history(int id, String date, int water_intake_level) {
    this.id = id;
    this.date = date;
    this.water_intake_level = water_intake_level;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public int getWater_intake_level() {
    return water_intake_level;
  }

  public void setWater_intake_level(int water_intake_level) {
    this.water_intake_level = water_intake_level;
  }

}
