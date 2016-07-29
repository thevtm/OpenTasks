package com.OpenTasks.Statistics;

import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.util.StopWatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by VTM on 10/7/2016.
 */
public class SkillTracker {

  /* ENUM */
  public enum State { INITIAL, RUNNING, PAUSED }

  /* FIELDS */
  private Skill skill;

  private int initialExperience;
  private int initialLevel;

  private StopWatch stopWatch;

  /* METHODS */

  public SkillTracker(Skill skill) {
    this.skill = skill;

    stopWatch = new StopWatch();
  }

  public void start() {
    if (stopWatch.getRuntime() == 0) {
      initialExperience = skill.getExperience();
      initialLevel = skill.getBaseLevel();
    }

    stopWatch.start();
  }

  public void stop() {
    stopWatch.stop();
  }

  public void reset() {
    stopWatch.reset();
    initialExperience = skill.getExperience();
    initialLevel = skill.getBaseLevel();
  }

  public long getRunningTime() {
    return stopWatch.getRuntime();
  }

  public String getRunningTimeAsString() {
    return stopWatch.getRuntimeAsString();
  }

  public int getLevelsGained() {
    return skill.getBaseLevel() - initialLevel;
  }

  public int getExperienceGained() {
    return skill.getExperience() - initialExperience;
  }

  public double getExpPerMillisecond() {
    return  ((double) getExperienceGained()) / getRunningTime();
  }

  public int getExpPerHour() {
    return (int) ((getExperienceGained() * 3600000D) / getRunningTime());
  }

  public long timeToNextLevel() {
    double expToNextLevel = (double) skill.getExperienceToNextLevel();
    double expPerMillisecond = ((double) getExperienceGained()) / ((double) getRunningTime());
    double timeTNL = expToNextLevel / expPerMillisecond;

    // Return 0 if infinity
    if (Double.isInfinite(timeTNL))
      return 0;

    return Math.round(timeTNL);
  }

  public String timeToNextLevelAsString() {
    long TNL = timeToNextLevel();
    DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    return formatter.format(TNL);
  }

}
