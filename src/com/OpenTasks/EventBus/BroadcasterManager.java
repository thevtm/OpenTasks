package com.OpenTasks.EventBus;

import com.OpenTasks.EventBus.Broadcaster.Events.ExperienceGainedEvent;
import com.OpenTasks.EventBus.Broadcaster.Events.ItemAddedEvent;
import com.OpenTasks.EventBus.Broadcaster.Events.ItemRemovedEvent;
import com.OpenTasks.EventBus.Broadcaster.Events.LevelUpEvent;
import com.OpenTasks.EventBus.Broadcaster.ItemEventBroadcaster;
import com.OpenTasks.EventBus.Broadcaster.SkillEventBroadcaster;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TheVTM on 22/1/2017.
 */
public class BroadcasterManager {

  private Map<String, String> eventToBroadcaster;

  public BroadcasterManager() {
    /* 1. Populate event -> broadcaster Map */
    eventToBroadcaster = new HashMap<>();

    // 1.1 ItemEventBroadcaster
    eventToBroadcaster.put(ItemAddedEvent.class.getCanonicalName(), ItemEventBroadcaster.class.getCanonicalName());
    eventToBroadcaster.put(ItemRemovedEvent.class.getCanonicalName(), ItemEventBroadcaster.class.getCanonicalName());

    // 1.2 SkillEventBroadcaster
    eventToBroadcaster.put(ExperienceGainedEvent.class.getCanonicalName(), SkillEventBroadcaster.class.getCanonicalName());
    eventToBroadcaster.put(LevelUpEvent.class.getCanonicalName(), SkillEventBroadcaster.class.getCanonicalName());
  }


}
