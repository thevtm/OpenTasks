package com.OpenTasks.Statistics;

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
public class ProfitTracker implements InventoryListener {

  private Predicate<ItemEvent> predicate;
  private int totalProfit;
  private StopWatch stopWatch;
  private Map<Integer, Integer> gePricesCache;

  public ProfitTracker(Predicate<ItemEvent> predicate) {
    this.predicate = predicate;

    gePricesCache = new HashMap<>();
    totalProfit = 0;
    stopWatch = new StopWatch();
  }

  @Override
  public void onItemAdded(ItemEvent itemEvent) {
    if (!predicate.test(itemEvent)) return;

    // Get item price
    int itemID = itemEvent.getItem().getId();
    int itemPrice = gePricesCache.computeIfAbsent(itemID, i -> OSBuddyExchange.getGuidePrice(i).getOverall());

    // Add to profit
    totalProfit += itemPrice * itemEvent.getQuantityChange();

  }

  @Override
  public void onItemRemoved(ItemEvent itemEvent) {
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


}
