package com.example.waterintake;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.astritveliu.boom.Boom;
import com.example.waterintake.Constant_Classes.Realm_Constants;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile_Fragment extends Fragment {

  ViewGroup root;
  TextView uweight, uname, ugender, uactivity_lvl, ugoal;
  int weight = 0;
  ListView activity_level_list;

  ConstraintLayout uname_change, weight_change, activity_level_change, gender_change;

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public Profile_Fragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment Profile_Fragment.
   */
  // TODO: Rename and change types and number of parameters
  public static Profile_Fragment newInstance(String param1, String param2) {
    Profile_Fragment fragment = new Profile_Fragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    root = (ViewGroup) inflater.inflate(R.layout.profile_fragment, container, false);
    uname_change = root.findViewById(R.id.uname_change);
    gender_change = root.findViewById(R.id.gender_change);
    weight_change = root.findViewById(R.id.weight_change);
    activity_level_change = root.findViewById(R.id.activity_level_change);
    uweight = root.findViewById(R.id.profile_user_weight);
    uname = root.findViewById(R.id.profile_username);
    uactivity_lvl = root.findViewById(R.id.profile_user_activity_level);
    ugender = root.findViewById(R.id.profile_user_gender);
    ugoal = root.findViewById(R.id.profile_Goal);

    Refresh_Data();

    final String[] activity_level_arrayList, gender_arrayList;

    new Boom(uname_change);
    new Boom(gender_change);
    new Boom(weight_change);
    new Boom(activity_level_change);

    activity_level_arrayList = getResources().getStringArray(R.array.activity_level);
    gender_arrayList = getResources().getStringArray(R.array.gender);
    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, activity_level_arrayList);
    final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, gender_arrayList);


    change_Username(uname_change);
    change_Weight(weight_change);
    change_Activity_Level(activity_level_change, adapter, activity_level_arrayList);
    change_Gender(gender_change, adapter1, gender_arrayList);

    return root;
  }

  private void change_Gender(ConstraintLayout gender_change, ArrayAdapter<String> adapter1, String[] gender_arrayList) {

    gender_change.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        final Dialog d = new Dialog(getActivity());
        d.setTitle("NumberPicker");
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.activity_level_dialog);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.getWindow().setGravity(Gravity.BOTTOM);
        activity_level_list = d.findViewById(R.id.activity_level_list);
        activity_level_list.setAdapter(adapter1);

        activity_level_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String activity = gender_arrayList[i];
//            Toast.makeText(Screen_2.this, activity, Toast.LENGTH_SHORT).show();
            ugender.setText(activity);
            ugender.setError(null);

            Realm_Constants.realm.beginTransaction();
            Realm_Constants.ONE_USER.setGender(activity);
            Realm_Constants.realm.commitTransaction();
            Calculate_Goal();
            Refresh_Data();
            d.dismiss();
          }
        });
        d.show();


      }
    });

  }

  private void change_Activity_Level(ConstraintLayout activity_level_change, ArrayAdapter<String> adapter, String[] activity_level_arrayList) {

    activity_level_change.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        final Dialog d = new Dialog(getActivity());
        d.setTitle("NumberPicker");
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.activity_level_dialog);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.getWindow().setGravity(Gravity.BOTTOM);
        activity_level_list = d.findViewById(R.id.activity_level_list);
        activity_level_list.setAdapter(adapter);

        activity_level_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String activity = activity_level_arrayList[i];
//            Toast.makeText(Screen_2.this, activity, Toast.LENGTH_SHORT).show();
            uactivity_lvl.setText(activity);
            uactivity_lvl.setError(null);

            Realm_Constants.realm.beginTransaction();
            Realm_Constants.ONE_USER.setActivity_lvl(activity);
            Realm_Constants.realm.commitTransaction();

            Calculate_Goal();
            Refresh_Data();

            d.dismiss();
          }
        });
        d.show();

      }
    });

  }

  public void change_Username(ConstraintLayout Change_Username_Object) {
    Change_Username_Object.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Refresh_Data();

      }
    });
  }

  public void change_Weight(ConstraintLayout weight_change) {
    weight_change.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        final Dialog d = new Dialog(getActivity());
        d.setTitle("NumberPicker");
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.weight_dialog);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.getWindow().setGravity(Gravity.BOTTOM);
        RulerValuePicker RVP = d.findViewById(R.id.ruler_picker);
        TextView Ruler_Title = d.findViewById(R.id.Ruler_Title);
        RVP.selectValue(Realm_Constants.ONE_USER.getWeight());
        RVP.setValuePickerListener(new RulerValuePickerListener() {
          @Override
          public void onValueChange(final int selectedValue) {
//            Toast.makeText(Screen_2.this,Integer.toString(selectedValue), Toast.LENGTH_SHORT).show();
//            user_weight.setText(Integer.toString(selectedValue)+" Kg");
          }

          @Override
          public void onIntermediateValueChange(final int selectedValue) {
            uweight.setText(selectedValue + " Kg");
            uweight.setError(null);
            weight = selectedValue;
            Ruler_Title.setText("Your Weight " + selectedValue + " Kg");

            Realm_Constants.realm.beginTransaction();
            Realm_Constants.ONE_USER.setWeight(selectedValue);
            Realm_Constants.realm.commitTransaction();
            Calculate_Goal();
            Refresh_Data();

          }
        });

        d.show();
      }
    });
  }



  public void Calculate_Goal(){
    int resultInMl;
    float genderMultiplier = (Realm_Constants.ONE_USER.getGender().equals("Male")) ? 20 : 10;
    float resultInOunces = Realm_Constants.ONE_USER.getWeight() * genderMultiplier / 28.3f;
    float activityAdder = 0;

    switch (Realm_Constants.ONE_USER.getActivity_lvl()){
      case "Steady":
        activityAdder = 100;
        break;
      case "Some-what Active":
        activityAdder = 150;
        break;
      case "Totally Active":
        activityAdder = 200;
        break;

    }

    DecimalFormat df = new DecimalFormat("#.##");


    resultInOunces += activityAdder;
    resultInMl = Math.round(resultInOunces * 500 / 40);
    double resultInL = (float) (resultInMl * 0.001);

    Realm_Constants.realm.beginTransaction();
    Realm_Constants.ONE_USER.setGoal(resultInMl);
    Realm_Constants.realm.commitTransaction();
  }

  public void Refresh_Data(){
    if (Realm_Constants.ONE_USER != null) {
      ugender.setText(String.valueOf(Realm_Constants.ONE_USER.getGender()));
      uweight.setText(String.valueOf(Realm_Constants.ONE_USER.getWeight()));
      uactivity_lvl.setText(String.valueOf(Realm_Constants.ONE_USER.getActivity_lvl()));
      uname.setText(String.valueOf(Realm_Constants.ONE_USER.getName()));
      ugoal.setText(Realm_Constants.ONE_USER.getGoal()+" Ml");
    }
  }

}