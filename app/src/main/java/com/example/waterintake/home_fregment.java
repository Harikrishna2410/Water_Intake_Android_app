package com.example.waterintake;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.astritveliu.boom.Boom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_fregment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class home_fregment extends Fragment implements View.OnClickListener{

    ImageView back_press;
    TextView full_date;
    ConstraintLayout date_change,glass_water,bottle_water,custom_water;
    WaveLoadingView waveLoadingView;
    Context ctx;
    int year;
    int month;
    int day;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PersianDatePickerDialog persianDatePickerDialog = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_fregment.
     */


    // TODO: Rename and change types and number of parameters
    public static home_fregment newInstance(String param1, String param2) {
        home_fregment fragment = new home_fregment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public home_fregment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

//        controllBinding();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home_fregment, container, false);
        date_change = root.findViewById(R.id.date_change_home_frag);
        glass_water = root.findViewById(R.id.glass_water_drink_home_fragment);
        bottle_water = root.findViewById(R.id.bottle_water_drink_home_fragment);
        custom_water = root.findViewById(R.id.custom_water_drink_home_fragment);
        waveLoadingView = root.findViewById(R.id.waveLoadingView);
        back_press = root.findViewById(R.id.back_press_home_screen_fragment);
        full_date = root.findViewById(R.id.full_date);

        date_change.setOnClickListener(this);

        new Boom(date_change);
        new Boom(glass_water);
        new Boom(bottle_water);
        new Boom(custom_water);
        new Boom(waveLoadingView);
        new Boom(back_press);

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat f = new SimpleDateFormat("LLLL", Locale.getDefault() );
        String mon = f.format(c.getTime());
        full_date.setText(day+"th "+mon+" "+year);

        return  root;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.date_change_home_frag:

                DatePickerDialog dp = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR,year);
                        c.set(Calendar.MONTH,month);
                        c.set(Calendar.DAY_OF_MONTH,day);

                        String date = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
                        String d[] = date.split(" ");
                        String m = d[2];
//                        Toast.makeText(getActivity(), m, Toast.LENGTH_SHORT).show();
                        full_date.setText(day+"th "+m+" "+year);
                    }
                }, year,month,day);
                dp.show();

                break;

        }

    }

}