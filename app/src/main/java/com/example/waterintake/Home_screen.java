package com.example.waterintake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class Home_screen extends AppCompatActivity {

  ChipNavigationBar bottomBar;
  FragmentManager fragmentManager ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_screen);
    bottomBar = findViewById(R.id.bottomBar);

    if (savedInstanceState == null){
      bottomBar.setItemSelected(R.id.home,true);
      fragmentManager = getSupportFragmentManager();
      home_fregment hfreg = new home_fregment();
      fragmentManager.beginTransaction()
        .replace(R.id.fragment_comtainer,hfreg)
        .commit();
    }

    bottomBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
      @Override
      public void onItemSelected(int i) {

        Fragment fragment = null;

        switch (i) {
          case R.id.home:
            fragment = new home_fregment();
            break;
          case R.id.history:
            fragment = new history_fragment();
            break;
          case R.id.settings:
            fragment = new settings_fragment();
            break;
        }

        if (fragment!=null){
          fragmentManager= getSupportFragmentManager();
          fragmentManager.beginTransaction()
            .replace(R.id.fragment_comtainer,fragment)
            .commit();
        }
        else
        {
          Log.e("Error","Error in Creating Fragment");
        }
      }
    });

  }
}