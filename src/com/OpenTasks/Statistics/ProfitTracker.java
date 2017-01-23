package com.OpenTasks.Statistics;

import com.OpenTasks.EventBus.Broadcaster.Events.ItemAddedEvent;
import com.OpenTasks.EventBus.Broadcaster.Events.ItemRemovedEvent;
import com.OpenTasks.EventBus.EventDispatcher;
import com.OpenTasks.EventBus.EventHandler;
import com.OpenTasks.EventBus.Events.StartTasksEvent;
import com.OpenTasks.EventBus.Events.StopTasksEvent;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.osrs.net.OSBuddyExchange;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Created by VTM on 12/7/2016.
 */
public class ProfitTracker {

  /* FIELDS */

  private Predicate<ItemEvent> predicate;
  private int totalProfit;
  private StopWatch stopWatch;
  private Map<Integer, Integer> gePricesCache;


  /* METHODS */

  public ProfitTracker(Predicate<ItemEvent> predicate) {
    this.predicate = predicate;

    gePricesCache = new HashMap<>();
    totalProfit = 0;
    stopWatch = new StopWatch();
  }

  @EventHandler
  public void onItemAdded(ItemAddedEvent itemAddedEvent) {
    ItemEvent itemEvent = itemAddedEvent.event;

    if (!predicate.test(itemEvent)) return;

    // Get item price
    int itemID = itemEvent.getItem().getId();
    int itemPrice = gePricesCache.computeIfAbsent(itemID, i -> OSBuddyExchange.getGuidePrice(i).getOverall());

    // Add to profit
    totalProfit += itemPrice * itemEvent.getQuantityChange();

  }

  @EventHandler
  public void onItemRemoved(ItemRemovedEvent itemRemovedEvent) {
    ItemEvent itemEvent = itemRemovedEvent.event;

    if (!predicate.test(itemEvent)) return;

    // Get item price
    int itemID = itemEvent.getItem().getId();
    int itemPrice = gePricesCache.computeIfAbsent(itemID, i -> OSBuddyExchange.getGuidePrice(i).getOverall());

    // Subtract from profit
    totalProfit -= itemPrice * itemEvent.getQuantityChange();
  }

  public void start() {
    stopWatch.start();
  }

  public void stop() {
    stopWatch.stop();
  }

  public void reset() {
    stopWatch.reset();
    totalProfit = 0;
  }

  public long getRunningTime() {
    return stopWatch.getRuntime();
  }

  public int getTotalProfit() {
    return totalProfit;
  }

  public int getProfitPerHour() {
    return (int) ((getTotalProfit() * 3600000D) / getRunningTime());
  }

  @EventHandler
  public void StartTasksEventHandler(StartTasksEvent event) {
    start();
  }

  @EventHandler
  public void StopTasksEventHandler(StopTasksEvent event) { stop(); }
}
