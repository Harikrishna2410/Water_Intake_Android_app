package com.example.waterintake;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astritveliu.boom.Boom;
import com.example.waterintake.realm_db.Custom_water_intake;
import com.example.waterintake.realm_db.Daily_history;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import me.itangqi.waveloadingview.WaveLoadingView;

public class Custom_intakes_recycler_view_adapter extends RecyclerView.Adapter<Custom_intakes_recycler_view_adapter.MyViewHolder> {

  int id[], img[];
  int ml;
  Context context;
  RealmResults<Custom_water_intake> list;
  FragmentActivity fragmentActivity;
  Realm realm;
  sharedPreference sp;
  Fragment frag = null;

  //  public Custom_intakes_recycler_view_adapter(ImageView icon, TextView tv, ConstraintLayout card) {
//    this.icon = icon;
//    this.tv = tv;
//    this.card = card;
//  }


  public Custom_intakes_recycler_view_adapter(RealmResults<Custom_water_intake> list, FragmentActivity fragmentActivity) {
    this.list = list;
    this.fragmentActivity = fragmentActivity;
    realm = Realm.getDefaultInstance();
  }

  public Custom_intakes_recycler_view_adapter(Context context, int id[], int ml, int img[]) {
    this.context = context;
    this.id = id;
    this.ml = ml;
    this.img = img;
  }

  @NonNull
  @Override
  public Custom_intakes_recycler_view_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(fragmentActivity);
    View view = inflater.inflate(R.layout.custom_intake_recycler_view_layout, parent, false);
    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull Custom_intakes_recycler_view_adapter.MyViewHolder holder, int position) {

    Custom_water_intake c = list.get(position);

//    Toast.makeText(fragmentActivity, c.getCustom_intake(), Toast.LENGTH_SHORT).show();

    holder.tv.setText(String.valueOf(c.getCustom_intake()));

    int a = c.getCustom_intake();

    if (a <= 250) {
      holder.icon.setImageResource(R.drawable.drink);
    } else if (a > 250 && a <= 500) {
      holder.icon.setImageResource(R.drawable.small_waterbottle_1);
    } else if (a > 500 && a <= 750) {
      holder.icon.setImageResource(R.drawable.water_bottle);
    } else if (a > 750 ) {
      holder.icon.setImageResource(R.drawable.gig_water_jug);
    } else {
      holder.icon.setImageResource(R.drawable.water_bottle);
    }

    holder.card.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        insertData(Integer.parseInt(holder.tv.getText().toString()));
        notifyDataSetChanged();
        home_fregment hf = new home_fregment();
        hf.waveloadingprogress(context);
//        Toast.makeText(fragmentActivity, holder.tv.getText().toString(), Toast.LENGTH_SHORT).show();
      }
    });



//    holder.icon.setImageResource(img[position]);

  }

  public void insertData(int glass_w) {


    long date = Calendar.getInstance().getTimeInMillis();
    SimpleDateFormat dateee = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

//        Toast.makeText(getActivity(), String.valueOf(total), Toast.LENGTH_SHORT).show();
    Number current_id = realm.where(Daily_history.class).max("id");

    int nextId;
    if (current_id == null) {
      nextId = 1;
    } else {
      nextId = current_id.intValue() + 1;
    }

    String StringDate = dateee.format(date);

    realm.beginTransaction();
    Daily_history d = new Daily_history(nextId, StringDate, glass_w);
    realm.copyToRealm(d);
    realm.commitTransaction();

//    home_fregment h = new home_fregment();
//    h.waveloadingprogress(fragmentActivity);

    Toast.makeText(fragmentActivity, "Data Inserted", Toast.LENGTH_SHORT).show();

  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView icon;
    TextView tv;
    ConstraintLayout card;
    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      icon = itemView.findViewById(R.id.custom_intake_icon);
      tv = itemView.findViewById(R.id.water_level_in_ml);
      card = itemView.findViewById(R.id.card_glass_water_drink_rv);


      new Boom(card);
    }
  }
}
