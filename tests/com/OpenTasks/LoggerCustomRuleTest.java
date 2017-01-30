package com.OpenTasks;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by TheVTM on 24/1/2017.
 */
public class LoggerCustomRuleTest {


  @Test
  public void match() throws Exception {

    /* 1. "com.OpenTasks" */
    Logger.LoggerCustomRule loggerCustomRule1 = new Logger.LoggerCustomRule("com.OpenTasks", Logger.LEVEL.All);

    assertTrue("\"com.OpenTasks\" Matches itself", loggerCustomRule1.match("com.OpenTasks"));

    assertFalse("\"com.OpenTasks\" Doesn't match its children", loggerCustomRule1.match("com.OpenTasks.Task"));

    assertTrue("\"com.OpenTasks\" Match its methods", loggerCustomRule1.match("com.OpenTasks:OpenTasks"));

    assertFalse("\"com.OpenTasks\" Doesn't match parent package", loggerCustomRule1.match("com"));

    assertFalse("\"com.OpenTasks\" Doesn't match brother", loggerCustomRule1.match("com.ClosedTasks"));

    assertFalse("\"com.OpenTasks\" Doesn't match a different canonical path", loggerCustomRule1.match("Foo.Bar.Baz"));


    /* 2. "java.util.*" */
    Logger.LoggerCustomRule loggerCustomRule2 = new Logger.LoggerCustomRule("java.util.*", Logger.LEVEL.DEBUG);

    assertTrue("\"java.util.*\" Matches itself", loggerCustomRule2.match("java.util"));

    assertTrue(" \"java.util.*\" Matches its children", loggerCustomRule2.match("java.util.String"));

    assertTrue("\"java.util.*\" Match its methods", loggerCustomRule2.match("java.util:foo"));

    assertFalse("\"java.util.*\" Doesn't match parent package", loggerCustomRule2.match("java"));

    assertFalse("\"java.util.*\" Doesn't match brother", loggerCustomRule2.match("java.useless"));

    assertFalse("\"java.util.*\" Doesn't match a different canonical path", loggerCustomRule2.match("Foo.Bar.*"));


    /* 3. "com.TheVTM.RainMaker.RainMaker:onLoop" */
    Logger.LoggerCustomRule loggerCustomRule3 = new Logger.LoggerCustomRule("com.TheVTM.RainMaker.RainMaker:onLoop", Logger.LEVEL.WARNING);

    assertTrue("\"com.TheVTM.RainMaker.RainMaker:onLoop\" Matches itself", loggerCustomRule3.match("com.TheVTM.RainMaker.RainMaker:onLoop"));

    assertFalse(" \"com.TheVTM.RainMaker.RainMaker:onLoop\" Doesn't matches its brothers", loggerCustomRule3.match("com.TheVTM.RainMaker.RainMaker:onStart"));

    assertFalse("\"com.TheVTM.RainMaker.RainMaker:onLoop\" Doesn't match parent class", loggerCustomRule3.match("com.TheVTM.RainMaker.RainMaker"));

    assertFalse("\"com.TheVTM.RainMaker.RainMaker:onLoop\" Doesn't match similar", loggerCustomRule3.match("com.TheVTM.TrainMaker.TrainMaker:onLoop"));

    assertFalse("\"com.TheVTM.RainMaker.RainMaker:onLoop\" Doesn't match a different canonical path", loggerCustomRule3.match("Foo.Bar.+"));

  }

  @Test
  public void prepareCustomRuleString() throws Exception {

    // - "com.OpenTasks" => "com\.OpenTasks(?>\z|:.*)"
    assertEquals("\"com.OpenTasks\" => \"com\\.OpenTasks(?>\\z|:.*)\"", "com\\.OpenTasks(?>\\z|:.*)",
        Logger.LoggerCustomRule.prepareCustomRuleString("com.OpenTasks"));

    // - "java.util.*" => "java\.util.*"
    assertEquals("\"java.util.*\" => \"java\\.util.*\"", "java\\.util.*",
        Logger.LoggerCustomRule.prepareCustomRuleString("java.util.*"));

    // - "com.TheVTM.RainMaker.RainMaker:onLoop" => "com\.TheVTM\.RainMaker\.RainMaker:onLoop"
    assertEquals("\"com.TheVTM.RainMaker.RainMaker:onLoop\" => \"com\\.TheVTM\\.RainMaker\\.RainMaker:onLoop\"",
        "com\\.TheVTM\\.RainMaker\\.RainMaker:onLoop",
        Logger.LoggerCustomRule.prepareCustomRuleString("com.TheVTM.RainMaker.RainMaker:onLoop"));

  }
}