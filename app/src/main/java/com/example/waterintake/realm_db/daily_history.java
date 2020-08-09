package com.example.waterintake.realm_db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class daily_history extends RealmObject {

  @PrimaryKey
  long id;
  long dg_id;

}
