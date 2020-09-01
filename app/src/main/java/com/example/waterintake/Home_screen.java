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
  JPTabBar tabBar;

  @Titles
  private static final String[] mTitles = {"Home","Report","Setting","Profile"};

  @SeleIcons
  private static final int[] mSeleIcons = {R.drawable.water_drop_white,R.drawable.chart_white,R.drawable.settings_white,R.drawable.baseline_person_black_24dp};

  @NorIcons
  private static final int[] mNormalIcons = {R.drawable.water_drop_white,R.drawable.chart_white,R.drawable.settings_white,R.drawable.baseline_person_black_24dp};

  Realm realm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_screen);
    bottomBar = findViewById(R.id.bottomBar);
//    tabBar = findViewById(R.id.tabbar);
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
            break;
          case R.id.plus:

            default_fragment(savedInstanceState);

            final Dialog d = new Dialog(Home_screen.this);
            d.setTitle("NumberPicker");
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(R.layout.dialog_manual_intake_input_of_screen_3);
            d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            d.getWindow().setGravity(Gravity.CENTER);
            EditText custom_intake = d.findViewById(R.id.manual_intake);
            CardView btn_done_dialog = d.findViewById(R.id.card_button_leta_instake_screen_4);
            new Boom(btn_done_dialog);

            TextView dialog_title = d.findViewById(R.id.activity_level_title);
            dialog_title.setText("Enter the amount of water you drank in Leters");

            btn_done_dialog.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                int in = Integer.parseInt(custom_intake.getText().toString());

                Number current_id = realm.where(Custom_water_intake.class).max("id");

                int nextId;
                if (current_id == null) {
                  nextId = 1;
                } else {
                  nextId = current_id.intValue() + 1;
                }

                realm.beginTransaction();
                Custom_water_intake cs_intake = new Custom_water_intake(nextId, in);
                realm.copyToRealm(cs_intake);
                realm.commitTransaction();
//                waveloadingprogress(context);
//                todays_history_rv_adapter.notifyDataSetChanged();

                d.dismiss();
              }
            });
            d.show();
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