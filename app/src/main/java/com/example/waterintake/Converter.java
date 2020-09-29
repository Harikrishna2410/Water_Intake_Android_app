package com.example.waterintake;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;

public class Converter {

  public static final int WEIGHT_CONVERTOR(Context context,int weight){
    sharedPreference sp = new sharedPreference(context);
    double user_weight = 0;
    if (sp.client_pref.getString("default_weight", null).equals("lbs")){

      user_weight = weight * 2.20462;
      Log.d("lbs weight ", String.valueOf(user_weight));
//      WaveVariable.getRulerValuePicker().setMinMaxValue(100,350);
      return Integer.parseInt(Constants.DF0.format(user_weight));

    }else {
      return weight;
    }

  }

  public static final Serializable WEIGHT_CONVERTOR_LBS_TO_KG(Context context,int weight){
    double userweight = 0 ;
    userweight = weight * 0.453592;

    return Constants.DF0.format(userweight);
  }

  public static final Serializable UNIT_CONVERTER(Context context, int water_intake_value) {
    sharedPreference sp = new sharedPreference(context);
    double finalvalue = 0;

    if (sp.client_pref.getString("default_unit", null).equals("Oz")) {

      finalvalue = water_intake_value*0.033814;
      return Constants.DF2.format(finalvalue);

    } else if (sp.client_pref.getString("default_unit", null).equals("L")) {

      finalvalue = water_intake_value*0.001;
      return Constants.DF2.format(finalvalue);

    } else {
      finalvalue = water_intake_value;
      return Constants.DF0.format(finalvalue);

    }
  }

}
