package com.example.waterintake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.astritveliu.boom.Boom;
import com.example.waterintake.realm_db.Custom_water_intake;
import com.example.waterintake.realm_db.Daily_history;

//import cn.pedant.SweetAlert.SweetAlertDialog;
import java.text.DateFormat;
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
//  SweetAlertDialog SAD;

  public todays_history_rv_adapter() {

  }

  public todays_history_rv_adapter(RealmResults<Daily_history> list,FragmentActivity fragmentActivity) {
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

    String d = daily_history.getDate();

    String[] cj = d.split(" ");

    holder.tv_time.setText(String.valueOf(cj[1]));
    holder.tv_ml.setText(daily_history.getWater_intake_level()+"\nml");
    realm = Realm.getDefaultInstance();

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
              RealmResults<Daily_history> results = realm.where(Daily_history.class).equalTo("id",id).findAll();
              results.deleteAllFromRealm();
              realm.commitTransaction();
              realm.refresh();
              notifyDataSetChanged();

              home_fregment hf = new home_fregment();
              hf.waveloadingprogress(ctx);

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

    TextView tv_ml,tv_time;
    ImageView delete_btn;

    public myViewHolder(@NonNull View itemView) {
      super(itemView);

      tv_ml = itemView.findViewById(R.id.tv_ml);
      tv_time = itemView.findViewById(R.id.tv_time);
      delete_btn = itemView.findViewById(R.id.delete_btn);

    }
  }
}
