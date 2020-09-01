package com.example.waterintake.realm_db;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Daily_history extends RealmObject {

  @PrimaryKey
  private int id ;
  private Date datetime;
  private int water_intake_level;

  public Daily_history() {
  }

  public Daily_history(int id, Date datetime, int water_intake_level) {
    this.id = id;
    this.datetime = datetime;
    this.water_intake_level = water_intake_level;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getDatetime() {
    return datetime;
  }

  public void setDatetime(Date datetime) {
    this.datetime = datetime;
  }

  public int getWater_intake_level() {
    return water_intake_level;
  }

  public void setWater_intake_level(int water_intake_level) {
    this.water_intake_level = water_intake_level;
  }
}
