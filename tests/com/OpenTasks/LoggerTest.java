package com.OpenTasks;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by TheVTM on 24/1/2017.
 */
public class LoggerTest {

  @Test
  public void customRulesLogger() throws Exception {
    /* SET UP */

    Logger.LoggerCustomRule[] rules = {
        new Logger.LoggerCustomRule("com.OpenTasks.Statistics.*", Logger.LEVEL.DISABLED),
        new Logger.LoggerCustomRule("com.OpenTasks.Task", Logger.LEVEL.CRITICAL),
        new Logger.LoggerCustomRule("com.OpenTasks.EventBus.Broadcaster.*", Logger.LEVEL.WARNING),
        new Logger.LoggerCustomRule("com.OpenTasks.EventBus.*", Logger.LEVEL.DEBUG),
        new Logger.LoggerCustomRule("com.OpenTasks.EventBus.Hidden.*", Logger.LEVEL.CRITICAL)
    };

    /* TESTS */

    // 1. Default level
    assertTrue("Default level works",
        Logger.shouldPrint(Logger.LEVEL.INFO, Logger.LEVEL.INFO, rules, "org.junit.Test"));

    assertFalse("Default level works",
        Logger.shouldPrint(Logger.LEVEL.CRITICAL, Logger.LEVEL.DISABLED, rules, "org.java.String"));

    // 2. Custom level for specific class
    assertTrue("Custom level works for specific class",
        Logger.shouldPrint(Logger.LEVEL.CRITICAL, Logger.LEVEL.DISABLED, rules, "com.OpenTasks.Task"));

    assertFalse("Custom level works for specific class",
        Logger.shouldPrint(Logger.LEVEL.DEBUG, Logger.LEVEL.All, rules, "com.OpenTasks.Task"));

    assertTrue("Custom level works for specific class",
        Logger.shouldPrint(Logger.LEVEL.CRITICAL, Logger.LEVEL.DISABLED, rules, "com.OpenTasks.Task:Task"));

    assertFalse("Custom level works for specific class",
        Logger.shouldPrint(Logger.LEVEL.DEBUG, Logger.LEVEL.All, rules, "com.OpenTasks.Task:toString"));

    // 3. .* Works
    assertTrue("Custom level works for .* itself",
        Logger.shouldPrint(Logger.LEVEL.DEBUG, Logger.LEVEL.INFO, rules, "com.OpenTasks.EventBus"));

    assertTrue("Custom level works for .* classes",
        Logger.shouldPrint(Logger.LEVEL.DEBUG, Logger.LEVEL.ERROR, rules, "com.OpenTasks.EventBus.Event"));

    assertTrue("Custom level works for .* classes and methods",
        Logger.shouldPrint(Logger.LEVEL.WARNING, Logger.LEVEL.CRITICAL, rules, "com.OpenTasks.EventBus.Event:Foo"));

    assertFalse("Custom level works for .*",
        Logger.shouldPrint(Logger.LEVEL.CRITICAL, Logger.LEVEL.INFO, rules, "com.OpenTasks.Statistics.Skills"));

    // 4. Rules are evaluated by their order
    assertFalse("Rules are evaluated by order.",
        Logger.shouldPrint(Logger.LEVEL.DEBUG, Logger.LEVEL.INFO, rules, "com.OpenTasks.EventBus.Broadcaster.Exp"));

    assertTrue("Rules are evaluated by order.",
        Logger.shouldPrint(Logger.LEVEL.DEBUG, Logger.LEVEL.INFO, rules, "com.OpenTasks.EventBus.Hidden.Exp"));

  }
}