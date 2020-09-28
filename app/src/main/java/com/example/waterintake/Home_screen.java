package com.example.waterintake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.astritveliu.boom.Boom;
import com.example.waterintake.realm_db.Custom_water_intake;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;

import io.realm.Realm;


public class Home_screen extends AppCompatActivity {

  ChipNavigationBar bottomBar;
  FragmentManager fragmentManager;
  Realm realm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_screen);
    bottomBar = findViewById(R.id.bottomBar);
    realm = Realm.getDefaultInstance();


    default_fragment(savedInstanceState);

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
          case R.id.profile:
            fragment = new Profile_Fragment();
            break;
        }

        if (fragment != null) {
          fragmentManager = getSupportFragmentManager();
          fragmentManager.beginTransaction()
            .replace(R.id.fragment_comtainer, fragment)
            .commit();
        } else {
          Log.e("Error", "Error in Creating Fragment");
        }
      }
    });

  }

  public void default_fragment(Bundle savedInstanceState){
    if (savedInstanceState == null) {
      bottomBar.setItemSelected(R.id.home, true);
      fragmentManager = getSupportFragmentManager();
      home_fregment hfreg = new home_fregment();
      fragmentManager.beginTransaction()
        .replace(R.id.fragment_comtainer, hfreg)
        .commit();
    }
  }

}