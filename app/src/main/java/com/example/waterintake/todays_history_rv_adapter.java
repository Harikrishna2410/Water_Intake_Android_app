package com.example.waterintake;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.astritveliu.boom.Boom;
import com.example.waterintake.History.Todays_History_Fragment;
import com.example.waterintake.realm_db.Custom_water_intake;
import com.example.waterintake.realm_db.Daily_history;

//import cn.pedant.SweetAlert.SweetAlertDialog;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import umairayub.madialog.MaDialog;
import umairayub.madialog.MaDialogListener;

public class todays_history_rv_adapter extends RecyclerView.Adapter<todays_history_rv_adapter.myViewHolder> {

  FragmentActivity fragmentActivity;
  RealmResults<Daily_history> list;
  Realm realm;
  Context ctx;

  public todays_history_rv_adapter() {

  }

  public todays_history_rv_adapter(RealmResults<Daily_history> list, FragmentActivity fragmentActivity) {
    this.fragmentActivity = fragmentActivity;
    this.list = list;

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

    Daily_history daily_history = list.get(position);

    holder.tv_ml.setText(daily_history.getWater_intake_level() + "\nml");
    realm = Realm.getDefaultInstance();

//    try {
//      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
////      Date time = sdf.parse(String.valueOf(daily_history.getTime()));
////      Log.d("time",new SimpleDateFormat("K:mm:ss a").format(time));
////      holder.tv_time.setText(new SimpleDateFormat("K:mm:ss a").format(time));
////
//    } catch (ParseException e) {
//      e.printStackTrace();
//    }


    new Boom(holder.delete_btn);

    holder.delete_btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        int id = daily_history.getId();


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
              RealmResults<Daily_history> results = realm.where(Daily_history.class).equalTo("id", id).findAll();
              results.deleteAllFromRealm();
              realm.commitTransaction();
              realm.refresh();
              notifyDataSetChanged();
              home_fregment hf = new home_fregment();
              hf.waveloadingprogress(ctx);
              Todays_History_Fragment.MpChartDisplay();

            }
          })
          .build();

      }
    });

  }

  @Override
  public int getItemCount() {
    return list.size();
  }


  public class myViewHolder extends RecyclerView.ViewHolder {

    TextView tv_ml, tv_time;
    ImageView delete_btn;

    public myViewHolder(@NonNull View itemView) {
      super(itemView);

      tv_ml = itemView.findViewById(R.id.tv_ml);
      tv_time = itemView.findViewById(R.id.tv_time);
      delete_btn = itemView.findViewById(R.id.delete_btn);

    }
  }
}
