package com.example.waterintake.History;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.astritveliu.boom.Boom;
import com.example.waterintake.R;
import com.example.waterintake.home_fregment;
import com.example.waterintake.realm_db.Daily_history;
import com.example.waterintake.todays_history_rv_adapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Weekly_History_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Weekly_History_Fragment extends Fragment {
    ViewGroup root;
    ConstraintLayout btn_change_date;
    TextView tv_date;
    BarChart barChart;
    BarEntry barEntry;
    BarData barData;
    BarDataSet barDataSet;
    RecyclerView recyclerView;
    int year, month, day,day1;
    static String spdate;
    public static BarChart weekly_barChart;
    public static ArrayList<BarEntry> weekly_total;
    public static BarData weekly_barData;
    public static BarDataSet weekly_barDataSet;
    public static RealmQuery<Daily_history> weekly_histroy;
    public static Realm realm;
    todays_history_rv_adapter todays_history_rv_adapter;
    RealmResults<Daily_history> Weekly_histories;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date finalenddate,currentDate,d1,d2;
    static long l1 = 0;
    static long l2 = 0;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Weekly_History_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Weekly_History_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Weekly_History_Fragment newInstance(String param1, String param2) {
        Weekly_History_Fragment fragment = new Weekly_History_Fragment();
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = (ViewGroup) inflater.inflate(R.layout.fragment_weekly__history_, container, false);
        btn_change_date = root.findViewById(R.id.date_change_weekly_history);
        tv_date = root.findViewById(R.id.tv_from_to_date);
        recyclerView = root.findViewById(R.id.rv_weekly_history);
        weekly_barChart = root.findViewById(R.id.weekly_barChart);
        realm = Realm.getDefaultInstance();

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        Date currentdate = new Date();
        Calendar b = Calendar.getInstance();
        b.setTime(currentdate);

        b.add(Calendar.DATE,6);

        currentDate = b.getTime();

        tv_date.setText(dateFormat.format(c.getTime())+" To "+dateFormat.format(currentDate));

        btn_change_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dp = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        Calendar startdate = Calendar.getInstance();
                        startdate.set(Calendar.YEAR, year);
                        startdate.set(Calendar.MONTH, month);
                        startdate.set(Calendar.DAY_OF_MONTH, day);

                        Date currentdate = startdate.getTime();
                        Calendar enddate = Calendar.getInstance();
                        enddate.setTime(currentdate);
                        enddate.add(Calendar.DATE,6);

                        finalenddate = enddate.getTime();

                        tv_date.setText(dateFormat.format(startdate.getTime())+" To "+dateFormat.format(finalenddate));

                    }
                }, year, month, day);
                dp.show();
            }
        });

        MpChartDisplay();

        Toast.makeText(getActivity(), String.valueOf(currentDate), Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), String.valueOf(finalenddate), Toast.LENGTH_SHORT).show();



//        Weekly_histories = realm.where(Daily_history.class).equalTo("date", dateFormat.format(currentDate)).sort("time", Sort.DESCENDING).findAll();
        Weekly_histories = realm.where(Daily_history.class).findAll();
//        todays_history_rv_adapter = new todays_history_rv_adapter(Weekly_histories, getActivity());
//        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager1);
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setAdapter(todays_history_rv_adapter);


        for (int i = 0; i< Weekly_histories.size();i++){


            Toast.makeText(getActivity(), Weekly_histories.get(i).getDate().toString(), Toast.LENGTH_SHORT).show();
            
//            Log.d("datas", "\nUnformated :- "+dateFormat.format(currentdate)+"\nformated :- "+d1+d2);
        }


        new Boom(btn_change_date);

        return root;
    }
    public static void MpChartDisplay(){
        weekly_histroy = realm.where(Daily_history.class).equalTo("date", spdate);
        int results = weekly_histroy.sum("water_intake_level").intValue();
        realm.setAutoRefresh(true);

        weekly_total = new ArrayList<>();
        weekly_total.add(new BarEntry(results,results));

        weekly_barChart.notifyDataSetChanged();
        weekly_barChart.invalidate();

        weekly_barDataSet = new BarDataSet(weekly_total,"Todays Total Intake");
        weekly_barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        weekly_barDataSet.setValueTextColor(Color.BLACK);
        weekly_barDataSet.setValueTextSize(16f);

        weekly_barData = new BarData(weekly_barDataSet);
        weekly_barChart.setFitBars(true);
        weekly_barChart.setData(weekly_barData);
        weekly_barChart.animate();
    }
}