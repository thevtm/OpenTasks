package com.OpenTasks.Statistics;

import com.OpenTasks.EventBus.Broadcaster.Events.ItemAddedEvent;
import com.OpenTasks.EventBus.Broadcaster.Events.ItemRemovedEvent;
import com.OpenTasks.EventBus.EventHandler;
import com.OpenTasks.EventBus.Events.StartTasksEvent;
import com.OpenTasks.EventBus.Events.StopTasksEvent;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;

import java.util.function.Predicate;

/**
 * Created by VTM on 12/7/2016.
 */
public class ItemTracker {

  /* FIELDS */

  private Predicate<ItemEvent> predicate;
  private int total;
  private StopWatch stopWatch;

  /* METHODS */

  public ItemTracker(Predicate<ItemEvent> predicate) {
    this.predicate = predicate;

    total = 0;
    stopWatch = new StopWatch();
  }

  @EventHandler
  public void onItemAdded(ItemAddedEvent itemAddedEvent) {
    ItemEvent itemEvent = itemAddedEvent.event;

    if (!predicate.test(itemEvent)) return;

    total += itemEvent.getQuantityChange();
  }

  @EventHandler
  public void onItemRemoved(ItemRemovedEvent itemRemovedEvent) {
    ItemEvent itemEvent = itemRemovedEvent.event;

    if (!predicate.test(itemEvent)) return;

    total -= itemEvent.getQuantityChange();
  }

  public void start() {
    stopWatch.start();
  }

  public void stop() {
    stopWatch.stop();
  }

  public void reset() {
    stopWatch.reset();
    total = 0;
  }

  public long getRunningTime() {
    return stopWatch.getRuntime();
  }

  public int getTotal() {
    return total;
  }

  public int getPerHour() {
    return (int) ((getTotal() * 3600000D) / getRunningTime());
  }

  @EventHandler
  public void StartTasksEventHandler(StartTasksEvent event) {
    start();
  }

  @EventHandler
  public void StopTasksEventHandler(StopTasksEvent event) { stop(); }
}
