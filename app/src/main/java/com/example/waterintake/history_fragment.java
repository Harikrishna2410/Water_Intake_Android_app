package com.example.waterintake;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.waterintake.History.Monthly_History_Framgment;
import com.example.waterintake.History.Todays_History_Fragment;
import com.example.waterintake.History.Weekly_History_Fragment;
import com.example.waterintake.History.Yearly_History_Fragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link history_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class history_fragment extends Fragment {
  ViewGroup root;
  RadioRealButton today_btn, weekly_btn, monthly_btn, yearly_btn;
  FrameLayout include_layout;
  TabLayout group;
  public FragmentManager fragmentManager;
  Fragment fragment;

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public history_fragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment history_fragment.
   */
  // TODO: Rename and change types and number of parameters
  public static history_fragment newInstance(String param1, String param2) {
    history_fragment fragment = new history_fragment();
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
//        return inflater.inflate(R.layout.fragment_history_fragment, container, false);
    root = (ViewGroup) inflater.inflate(R.layout.fragment_history_fragment, container, false);
    today_btn = root.findViewById(R.id.today_btn);
    weekly_btn = root.findViewById(R.id.weekly_btn);
    monthly_btn = root.findViewById(R.id.monthly_btn);
    yearly_btn = root.findViewById(R.id.yearly_btn);
    include_layout = root.findViewById(R.id.include_layout);
    group = root.findViewById(R.id.tabLayout);


    fragmentManager = getChildFragmentManager();
    Todays_History_Fragment todays_history_fragment = new Todays_History_Fragment();
    fragmentManager.beginTransaction()
      .replace(R.id.include_layout, todays_history_fragment)
      .commit();

    group.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        fragment = null;
        switch (tab.getPosition()){
          case 0:
            fragment = new Todays_History_Fragment();
            break;
          case 1:
            fragment = new Weekly_History_Fragment();
            break;
          case 2:
            fragment = new Monthly_History_Framgment();
            break;
          case 3:
            fragment = new Yearly_History_Fragment();
            break;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.include_layout,fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });

    return root;
  }

}