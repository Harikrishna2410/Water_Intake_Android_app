package com.example.waterintake;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.astritveliu.boom.Boom;
import com.example.waterintake.Constant_Classes.MPChart;
import com.example.waterintake.History.Todays_History_Fragment;
import com.example.waterintake.History.Weekly_History_Fragment;
import com.example.waterintake.Modal_Classis.DailyHistory;
import com.example.waterintake.realm_db.Daily_history;

//import cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import umairayub.madialog.MaDialog;
import umairayub.madialog.MaDialogListener;

public class todays_history_rv_adapter extends RecyclerView.Adapter<todays_history_rv_adapter.myViewHolder> {

  FragmentActivity fragmentActivity;
  Realm realm;
  ArrayList<DailyHistory> list = new ArrayList<>();
  sharedPreference sp;
  Context context;

  public todays_history_rv_adapter(FragmentActivity activity) {

  }

  public todays_history_rv_adapter(ArrayList<DailyHistory> today_history, FragmentActivity fragmentActivity) {
    this.fragmentActivity = fragmentActivity;
    list = today_history;

  }

  @NonNull
  @Override
  public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(fragmentActivity);
    View view = inflater.inflate(R.layout.todays_history_rv_adapter_layout, parent, false);
    return new myViewHolder(view);
  }


  @Override
  public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
    sp = new sharedPreference(fragmentActivity);

    String time_in_PMAM = Constants.TIME_FORMAT.format(list.get(position).getDatetime());


    if (sp.client_pref.getBoolean("weeklyreport",false)==true) {
      holder.tv_time.setText(Constants.FULL_DATE_FORMAT_FOR_REPORTS.format(list.get(position).getDatetime()));
    }
    else{
      holder.tv_time.setText(time_in_PMAM);
    }
    holder.tv_ml.setText(Converter.UNIT_CONVERTER(fragmentActivity,list.get(position).getWater_intake_level()) + "\n" + sp.client_pref.getString("default_unit",null));
    holder.tv_id.setText(String.valueOf(list.get(position).getId()));
    holder.tv_id.setVisibility(View.GONE);

    if (sp.client_pref.getBoolean("deleteBtnVisible", false) == true) {
      holder.delete_btn.setVisibility(View.GONE);
    } else {
      holder.delete_btn.setVisibility(View.VISIBLE);
    }

    realm = Realm.getDefaultInstance();

    new Boom(holder.delete_btn);

    holder.delete_btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        int id = list.get(position).getId();
        Log.e("dddddddd", String.valueOf(id));

        try {

          new MaDialog.Builder(fragmentActivity)
            .setTitle("Are You Sure?")
            .setMessage("Won't be able to recover this data")
            .setPositiveButtonText("Cancel")
            .setNegativeButtonText("Yes,delete it!")
            .setPositiveButtonListener(new MaDialogListener() {
              @Override
              public void onClick() {
                new MaDialog.Builder(fragmentActivity).setCancelableOnOutsideTouch(true);
              }
            })
            .setNegativeButtonListener(new MaDialogListener() {
              @Override
              public void onClick() {
                realm.beginTransaction();
                RealmResults<Daily_history> results = realm.where(Daily_history.class).equalTo("id", list.get(position).getId()).findAll();
                results.deleteAllFromRealm();
                realm.commitTransaction();
                realm.refresh();
                list.remove(position);
                Log.e("delete data", results.toString());
                notifyDataSetChanged();
                home_fregment.getInstace().waveloadingprogress();
                MPChart.Todays_MPChartDisplay(fragmentActivity);
                if (sp.client_pref.getBoolean("deleteBtnVisible", false) == false) {
                  Weekly_History_Fragment WHF = new Weekly_History_Fragment();
                  WHF.MpChartDisplay();
                }

              }
            })
            .build();

        } catch (Exception e) {
          Log.e("Exception Error", e.getMessage());
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    if (list.size() == 0) {
      return 0;
    } else {
      return list.size();
    }
  }


  public class myViewHolder extends RecyclerView.ViewHolder {

    TextView tv_ml, tv_time, tv_id;
    ImageView delete_btn;

    public myViewHolder(@NonNull View itemView) {
      super(itemView);

      tv_ml = itemView.findViewById(R.id.tv_ml);
      tv_time = itemView.findViewById(R.id.tv_time);
      delete_btn = itemView.findViewById(R.id.delete_btn);
      tv_id = itemView.findViewById(R.id.today_history_id);
    }

  }
}
