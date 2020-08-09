package com.example.waterintake.realm_db;

import android.app.Application;

import io.realm.Realm;

public class MyApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Realm.init(this);

  }
}
