package com.example.waterintake;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class sharedPreference {

  public static final String pref = "Client_Preference_file";
  public SharedPreferences client_pref;
  public SharedPreferences.Editor editor_client_pref;
  Context ctx;

  public sharedPreference(Context context){
    this.ctx = context;
    this.client_pref = ctx.getSharedPreferences(pref, MODE_PRIVATE);
    this.editor_client_pref = client_pref.edit();
  }

}
