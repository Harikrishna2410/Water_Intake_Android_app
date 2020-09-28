package com.example.waterintake.Constant_Classes;

import com.example.waterintake.realm_db.Custom_water_intake;
import com.example.waterintake.realm_db.Daily_history;
import com.example.waterintake.realm_db.Users;

import io.realm.Realm;
import io.realm.RealmResults;

public class Realm_Constants {
  public static Realm realm = Realm.getInstance(Realm.getDefaultConfiguration());

  public final static RealmResults<Daily_history> ALL_DAILY_HISTORY = realm.where(Daily_history.class).findAll();
  public final static RealmResults<Users> ALL_USERS = realm.where(Users.class).findAll();
  public final static RealmResults<Custom_water_intake> ALL_CUSTOM_WATER_INTAKES = realm.where(Custom_water_intake.class).findAll();


  public final static Users ONE_USER = realm.where(Users.class).equalTo("id",1).findFirst();

}

