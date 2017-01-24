package com.OpenTasks.LoggerLevelGraph;

import com.OpenTasks.Logger;
import javafx.util.Pair;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by TheVTM on 23/1/2017.
 */
public class LoggerLevelNodeTest {

  @Test
  public void LoggerLevelNode() throws Exception {
    LoggerLevelNode singleNode = new LoggerLevelNode("com", new LoggerLevelNode.LoggerLevelInfo[]{
        new LoggerLevelNode.LoggerLevelInfo(null, Logger.LEVEL.INFO)
    });

    System.out.println(String.format("singleNode: %s", singleNode.toString()));

    LoggerLevelNode multipleNode = new LoggerLevelNode("com", new LoggerLevelNode.LoggerLevelInfo[]{
//        new LoggerLevelNode.LoggerLevelInfo(null, Logger.LEVEL.DEBUG),
        new LoggerLevelNode.LoggerLevelInfo("OpenTasks", Logger.LEVEL.CRITICAL),
        new LoggerLevelNode.LoggerLevelInfo("OpenTasks.Logger", Logger.LEVEL.INFO),
    });

    System.out.println(String.format("multipleNode: %s", multipleNode.toString()));
  }

  @Test
  public void getLevel() throws Exception {

  }

  /* TESTS */

  @Test
  public void splitCanonical() throws Exception {

    assertEquals("Foo.Bar is split to (\"Foo\", \"Bar\").", new Pair<>("Foo", "Bar"),
        LoggerLevelNode.splitCanonical("Foo.Bar"));

    assertEquals("com.OpenTasks.LoggerLevelGraph is split to (\"com\", \"OpenTasks.LoggerLevelGraph\").",
        new Pair<>("com", "OpenTasks.LoggerLevelGraph"),
        LoggerLevelNode.splitCanonical("com.OpenTasks.LoggerLevelGraph"));

    assertEquals("LoggerLevelGraph is split to (\"LoggerLevelGraph\", \"null\").",
        new Pair<String, String>("LoggerLevelGraph", null),
        LoggerLevelNode.splitCanonical("LoggerLevelGraph"));

    assertEquals("javafx:util.Pair is split to (\"javafx\", \"util.Pair\").",
        new Pair<>("javafx", "util.Pair"),
        LoggerLevelNode.splitCanonical("javafx:util.Pair"));

    assertEquals("javafx$util is split to (\"javafx\", \"util\").",
        new Pair<>("javafx", "util"),
        LoggerLevelNode.splitCanonical("javafx:util"));

  }

}