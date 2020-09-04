package com.example.waterintake.Modal_Classis;

import java.util.Date;

public class DailyHistory {
  private int id ;
  private Date datetime;
  private int water_intake_level;

  public DailyHistory() {
  }

  public DailyHistory(int id, Date datetime, int water_intake_level) {
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
