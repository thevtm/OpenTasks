package com.OpenTasks.Tasks.AntibanTasks;

import com.OpenTasks.Task;

/**
 * Created by VTM on 21/7/2016.
 */
public class TakeBreak extends Task {

  private int minIntervalMilli;
  private int maxIntervalMilli;

  private int minDurationMilli;
  private int maxDurationMilli;

  public TakeBreak(int priority, int minIntervalMilli, int maxIntervalMilli, int minDurationMilli, int maxDurationMilli) {
    super("TakeBreak", priority);

    this.minIntervalMilli = minIntervalMilli;
    this.maxIntervalMilli = maxIntervalMilli;

    this.minDurationMilli = minDurationMilli;
    this.maxDurationMilli = maxDurationMilli;
  }

  @Override
  public boolean validate() {
    return false;
  }

  @Override
  public void execute() {

  }

  @Override
  public void configure(Object config) {

  }

  @Override
  public void begin() {

  }

  @Override
  public void end() {

  }
}
