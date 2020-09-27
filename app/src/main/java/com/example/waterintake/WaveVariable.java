package com.example.waterintake;
import com.github.mikephil.charting.charts.BarChart;

import me.itangqi.waveloadingview.WaveLoadingView;

public class WaveVariable {

  private static BarChart Monthly_Barchart;

  public static BarChart getMonthly_Barchart() {
    return Monthly_Barchart;
  }

  public static void setMonthly_Barchart(BarChart monthly_Barchart) {
    Monthly_Barchart = monthly_Barchart;
  }

  private static WaveLoadingView waveLoadingView;

  public static WaveLoadingView getWaveLoadingView() {
    return waveLoadingView;
  }

  public static void setWaveLoadingView(WaveLoadingView waveLoadingView) {
    WaveVariable.waveLoadingView = waveLoadingView;
  }
}
