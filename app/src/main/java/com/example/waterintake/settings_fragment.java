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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.astritveliu.boom.Boom;
import com.example.waterintake.Constant_Classes.Realm_Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link settings_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settings_fragment extends Fragment {

  ConstraintLayout unit_btn, weight_btn, set_alarm_btn, notification_ringtone_btn;
  TextView unit_tv, weight_tv, alarm_ring_tv, notification_ring_tv;
  Switch alarm_switch, notification_switch;
  ViewGroup root;
  sharedPreference sp;

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public settings_fragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment settings_fragment.
   */
  // TODO: Rename and change types and number of parameters
  public static settings_fragment newInstance(String param1, String param2) {
    settings_fragment fragment = new settings_fragment();
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
    root = (ViewGroup) inflater.inflate(R.layout.fragment_settings_fragment, container, false);
    sp = new sharedPreference(getActivity());
    alarm_switch = root.findViewById(R.id.setting_alarm_switch);
    notification_switch = root.findViewById(R.id.setting_notification_switch);
    notification_ringtone_btn = root.findViewById(R.id.setting_notification_ringtone_btn);
    set_alarm_btn = root.findViewById(R.id.setting_set_alarm_btn);

    unit_btn = root.findViewById(R.id.setting_units_btn);
    weight_btn= root.findViewById(R.id.setting_weight_btn);
    unit_tv = root.findViewById(R.id.setting_unit_tv);
    weight_tv = root.findViewById(R.id.setting_weight_tv);

    new Boom(unit_btn);
    new Boom(weight_btn);

    unit_tv.setText(sp.client_pref.getString("default_unit",null).toString());
    weight_tv.setText(sp.client_pref.getString("default_weight",null).toString());

    final String[] unit_arraylist,weight_arraylist;
    unit_arraylist = getResources().getStringArray(R.array.units);
    weight_arraylist = getResources().getStringArray(R.array.weight);
    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, unit_arraylist);
    final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, weight_arraylist);

    alarm_switch.setChecked(sp.client_pref.getBoolean("alarm",false));
    notification_switch.setChecked(sp.client_pref.getBoolean("notification",false));

    unit_btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        final Dialog d = new Dialog(getActivity());
        d.setTitle("NumberPicker");
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.setting_unit_dialog);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.getWindow().setGravity(Gravity.BOTTOM);
        ListView unit_list = d.findViewById(R.id.unit_list);
        TextView Dialog_title = d.findViewById(R.id.setting_dialog_title);
        Dialog_title.setText("Select Default Unit");
        unit_list.setAdapter(adapter);
        unit_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String activity = unit_arraylist[i];
            unit_tv.setText(activity);

            sp.editor_client_pref.putString("default_unit",activity);
            sp.editor_client_pref.commit();

            unit_tv.setError(null);
            d.dismiss();
          }
        });
        d.show();
      }
    });

    weight_btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        final Dialog d = new Dialog(getActivity());
        d.setTitle("NumberPicker");
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.setting_unit_dialog);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.getWindow().setGravity(Gravity.BOTTOM);
        ListView unit_list = d.findViewById(R.id.unit_list);
        TextView Dialog_title = d.findViewById(R.id.setting_dialog_title);
        Dialog_title.setText("Select Default Weight");
        unit_list.setAdapter(adapter1);
        unit_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String activity = weight_arraylist[i];
            weight_tv.setText(activity);

            sp.editor_client_pref.putString("default_weight",activity);
            sp.editor_client_pref.commit();

            weight_tv.setError(null);
            d.dismiss();
          }
        });
        d.show();
      }
    });

    alarm_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        alarm_Switch();

      }
    });

    notification_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        notification_Switch();
      }
    });

    alarm_Switch();
    notification_Switch();

    return root;
  }



  public void alarm_Switch(){

    if (alarm_switch.isChecked() == false)
    {
      sp.editor_client_pref.putBoolean("alarm",false);
      sp.editor_client_pref.commit();
      set_alarm_btn.setVisibility(View.GONE);
    }
    else {

      sp.editor_client_pref.putBoolean("alarm",true);
      sp.editor_client_pref.commit();
      set_alarm_btn.setVisibility(View.VISIBLE);
    }
  }
  public void notification_Switch(){

    if (notification_switch.isChecked() == false)
    {

      sp.editor_client_pref.putBoolean("notification",false);
      sp.editor_client_pref.commit();
      notification_ringtone_btn.setVisibility(View.GONE);
    }
    else {
      sp.editor_client_pref.putBoolean("notification",true);
      sp.editor_client_pref.commit();
      notification_ringtone_btn.setVisibility(View.VISIBLE);
    }
  }

}