package com.example.waterintake.realm_db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Users extends RealmObject {

  @PrimaryKey
  int id;
  String name;
  String gender;
  int weight;
  String activity_lvl;
  int goal;

  public Users() {
  }

  public Users(int id, String name, String gender, int weight, String activity_lvl,int goal) {
    this.id = id;
    this.name = name;
    this.gender = gender;
    this.weight = weight;
    this.activity_lvl = activity_lvl;
    this.goal = goal;
  }

  public Users(String name, String gender, int weight, String activity_lvl) {
    this.name = name;
    this.gender = gender;
    this.weight = weight;
    this.activity_lvl = activity_lvl;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public String getActivity_lvl() {
    return activity_lvl;
  }

  public void setActivity_lvl(String activity_lvl) {
    this.activity_lvl = activity_lvl;
  }

  public int getGoal() {
    return goal;
  }

  public void setGoal(int goal) {
    this.goal = goal;
  }
}
