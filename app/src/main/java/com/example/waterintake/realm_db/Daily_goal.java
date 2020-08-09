package com.example.waterintake.realm_db;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Daily_goal extends RealmObject {

  @PrimaryKey
  private int id ;
  private String date;
  private float goal;
  private float water_intake_level;

  public Daily_goal() {
  }

  public Daily_goal(int id, String date, float goal, float water_intake_level) {
    this.id = id;
    this.date = date;
    this.goal = goal;
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

  public float getGoal() {
    return goal;
  }

  public void setGoal(float goal) {
    this.goal = goal;
  }

  public float getWater_intake_level() {
    return water_intake_level;
  }

  public void setWater_intake_level(float water_intake_level) {
    this.water_intake_level = water_intake_level;
  }
}
