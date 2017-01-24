package com.OpenTasks.LoggerLevelGraph;

import com.OpenTasks.Logger;
import javafx.util.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * Created by TheVTM on 23/1/2017.
 */
public class LoggerLevelNode {

  /* CLASS */
  public static class LoggerLevelInfo {
    public final String canonicalPath;
    public final Logger.LEVEL level;

    public LoggerLevelInfo(String canonicalPath, Logger.LEVEL level) {
      this.canonicalPath = canonicalPath;
      this.level = level;
    }
  }

  /* FIELDS */

  private LoggerLevelNode childs[];

  private String path;
  private Logger.LEVEL level;


  /* METHODS */

  public LoggerLevelNode(String path, LoggerLevelInfo[] levelInfos) {
    this.path = path;

    // 1. If received a LoggerLevelInfo with path == null, that means it is for this node
    Arrays.stream(levelInfos).filter(info -> info.canonicalPath == null).findFirst()
        .ifPresent(loggerLevelInfo -> this.level = loggerLevelInfo.level);

    // 2. Create the children
    this.childs = Arrays.stream(levelInfos)

        // 2.1 Ignore the ones with path == null (they represent the rules for this node)
        .filter(info -> info.canonicalPath != null)

        // 2.2 Split the first canonical and create new LoggerLevelInfo
        // LoggerLevelInfo { canonicalPath: "Foo.Bar.Baz", level: DEBUG } =>
        // Pair<String(firstCanonicalName) { "Foo" }, LoggerLevelInfo(newLoggerLevelInfo) { canonicalPath: "Bar.Baz", level: DEBUG }>
        .map(LoggerLevelNode::splitCanonical)

        // 2.3 Group nodes by first canonical into a Map<String, List<Pair<String, LoggerLevelInfo>>>
        .collect(Collectors.groupingBy(Pair::getKey))

        // 2.4 Transforms Map<String(firstCanonicalName), List<Pair<String(firstCanonicalName), LoggerLevelInfo(newLoggerLevelInfo)>>> to LoggerLevelNode[]
        // Create using the firstCanonicalName and transforming the list to an array of the newLoggerLevelInfo
        .entrySet().stream().map(ssc -> {
          String firstCanonicalName = ssc.getKey();
          LoggerLevelInfo[] newLoggerLevelInfos = ssc.getValue().stream().map(Pair::getValue).toArray(LoggerLevelInfo[]::new);

          return new LoggerLevelNode(firstCanonicalName, newLoggerLevelInfos);
        })
        .toArray(LoggerLevelNode[]::new);
  }

  public Logger.LEVEL getLevel(String canonicalPath) {
    return null;
  }

  public static Pair<String, LoggerLevelInfo> splitCanonical(LoggerLevelInfo info) {
    // 1. Split first canonical path
    Pair<String, String> canonical = splitCanonical(info.canonicalPath);

    return new Pair<>(canonical.getKey(), new LoggerLevelInfo(canonical.getValue(), info.level));
  }

  public static Pair<String, String> splitCanonical(String canonicalPath) {
    // Examples:
    // - "Foo.Bar.Baz" => ("Foo", "Bar.Baz")
    // - "java.util" => ("java", "util")
    // - "Zoz" => ("Zoz", null)

    // 1. Find first split character
    final Pattern splitRegex = Pattern.compile("\\.|\\$|:");
    Matcher matcher = splitRegex.matcher(canonicalPath);

    // 1.1 If didn't find any return a pair with null as second member
    if (!matcher.find()) {
      return new Pair<>(canonicalPath, null);
    }

    // 2. Split string into two in the first split character and return them as a pair
    String head = canonicalPath.substring(0, matcher.start(0));
    String tail = canonicalPath.substring(matcher.end(0));

    return new Pair<>(head, tail);
  }

  @Override
  public String toString() {
    return "LoggerLevelNode{" +
        "path='" + path + '\'' +
        ", level=" + level +
        ", childs=" + Arrays.toString(childs) +
        '}';
  }
}
