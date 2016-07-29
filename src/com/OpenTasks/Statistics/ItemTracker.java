package com.OpenTasks.Statistics;

import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;

import java.util.function.Predicate;

/**
 * Created by VTM on 12/7/2016.
 */
public class ItemTracker implements InventoryListener {

  private Predicate<ItemEvent> predicate;
  private int total;
  private StopWatch stopWatch;

  public ItemTracker(Predicate<ItemEvent> predicate) {
    this.predicate = predicate;

    total = 0;
    stopWatch = new StopWatch();
  }

  @Override
  public void onItemAdded(ItemEvent itemEvent) {
    if (!predicate.test(itemEvent)) return;

    total += itemEvent.getQuantityChange();
  }

  @Override
  public void onItemRemoved(ItemEvent itemEvent) {
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
}
