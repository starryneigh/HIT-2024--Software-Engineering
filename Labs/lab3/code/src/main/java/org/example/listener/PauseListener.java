package org.example.listener;

import java.io.IOException;

public class PauseListener implements Runnable {
  private volatile boolean paused = false;
  private final Runnable onPause;
  private final Runnable onResume;

  public PauseListener() {
    this.onPause = () -> {
    };
    this.onResume = () -> {
    };
  }

  public PauseListener(Runnable onPause, Runnable onResume) {
    this.onPause = onPause;
    this.onResume = onResume;
  }

  public boolean isPaused() {
    return paused;
  }

  @Override
  public void run() {
    try {
      int input = System.in.read();
      if (input == '\n') {
        paused = !paused;
        if (paused) {
          onPause.run();
        } else {
          onResume.run();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

