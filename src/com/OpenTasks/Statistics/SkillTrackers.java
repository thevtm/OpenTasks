package com.OpenTasks.Statistics;

import com.runemate.game.api.hybrid.local.Skill;

import java.util.HashMap;

/**
 * Created by VTM on 10/7/2016.
 */
public class SkillTrackers {

  private HashMap<Skill, SkillTracker> skillsTracked = new HashMap<>();

  public SkillTracker addSkill(Skill skill) {
    if (skillsTracked.containsKey(skill)) {
      throw new RuntimeException(String.format("Skill %s is already being tracked.", skill.name()));
    }

    return skillsTracked.put(skill, new SkillTracker(skill));
  }

  public SkillTracker removeSkill(Skill skill) {
    SkillTracker st = skillsTracked.remove(skill);

    if (st == null) {
      throw new RuntimeException(String.format("Skill %s is not being tracked.", skill.name()));
    }

    return st;
  }

  public SkillTracker getSkill(Skill skill) {
    SkillTracker st = skillsTracked.get(skill);

    if (st == null) {
      throw new RuntimeException(String.format("Skill %s is not being tracked.", skill.name()));
    }

    return st;
  }

  public void start() {
    skillsTracked.values().forEach(SkillTracker::start);
  }

  public void stop() {
    skillsTracked.values().forEach(SkillTracker::stop);
  }

  public void reset() {
    skillsTracked.values().forEach(SkillTracker::reset);
  }

}
