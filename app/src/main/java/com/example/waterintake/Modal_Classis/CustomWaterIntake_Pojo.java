package com.example.waterintake.Modal_Classis;

public class CustomWaterIntake_Pojo {
  int id;
  int custom_intake;

  public CustomWaterIntake_Pojo() {
  }

  public CustomWaterIntake_Pojo(int id, int custom_intake) {
    this.id = id;
    this.custom_intake = custom_intake;
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
