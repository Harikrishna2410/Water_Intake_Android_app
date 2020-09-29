package com.example.waterintake;
import com.github.mikephil.charting.charts.BarChart;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;

import me.itangqi.waveloadingview.WaveLoadingView;

public class WaveVariable {

  private static BarChart Monthly_Barchart;
  private static BarChart Todays_Barchart;
  private static WaveLoadingView waveLoadingView;
  private static RulerValuePicker rulerValuePicker;

  public static RulerValuePicker getRulerValuePicker() {
    return rulerValuePicker;
  }

  public static void setRulerValuePicker(RulerValuePicker rulerValuePicker) {
    WaveVariable.rulerValuePicker = rulerValuePicker;
  }

  public static BarChart getTodays_Barchart() {
    return Todays_Barchart;
  }

  public static void setTodays_Barchart(BarChart todays_Barchart) {
    Todays_Barchart = todays_Barchart;
  }

  public static BarChart getMonthly_Barchart() {
    return Monthly_Barchart;
  }

  public static void setMonthly_Barchart(BarChart monthly_Barchart) {
    Monthly_Barchart = monthly_Barchart;
  }

  public static WaveLoadingView getWaveLoadingView() {
    return waveLoadingView;
  }

  public static void setWaveLoadingView(WaveLoadingView waveLoadingView) {
    WaveVariable.waveLoadingView = waveLoadingView;
  }
}
