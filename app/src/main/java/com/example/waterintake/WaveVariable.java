package com.example.waterintake;
import me.itangqi.waveloadingview.WaveLoadingView;
public class WaveVariable {
  private static WaveLoadingView waveLoadingView;

  public static WaveLoadingView getWaveLoadingView() {
    return waveLoadingView;
  }

  public static void setWaveLoadingView(WaveLoadingView waveLoadingView) {
    WaveVariable.waveLoadingView = waveLoadingView;
  }
}
