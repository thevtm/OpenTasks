package com.OpenTasks;

import com.runemate.game.api.hybrid.Environment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringJoiner;

/**
 * Created by VTM on 4/7/2016.
 */
public class Logger {

  /* ENUMS */
  public enum LEVEL {
    All(0), DEBUG(10), INFO(20), WARNING(30), ERROR(40), CRITICAL(50), DISABLED(60);

    public final int level;

    LEVEL(int level) {
      this.level = level;
    }
  }

  /* FIELDS */

  public LEVEL level;

  /* METHODS */

  public Logger(LEVEL level) {
    this.level = level;
  }

  public Logger() {
    this(LEVEL.INFO);
  }

  public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

  private static void _log(LEVEL level, String message, Exception exception) {
    if (!_shouldPrint(level)) return;

    _log(level, message, exception, 4);
  }

  private static void _log(LEVEL level, Exception exception, String format, Object... args) {
    if (!_shouldPrint(level)) return;

    String message = String.format(format, args);
    _log(level, message, exception, 4);
  }

  private static void _log(LEVEL level, String message, Exception exception, int stackTraceIndex) {
    // 1. Get current timestamp
    String timestamp = dateFormat.format(new Date());

    // 2. Get caller method name and class
    StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
    StackTraceElement e = stacktrace[stackTraceIndex];
    String className = e.getClassName();
    String methodName = e.getMethodName();

    // 3. Print out
    System.out.println(String.format("%s %s:%s", timestamp, className, methodName));
    System.out.println(String.format("[%s] %s", level.name(), message));
    if (exception != null) {
      exception.printStackTrace();
    }
  }

  private static boolean _shouldPrint(LEVEL level) {
    // Check if level is high enough to print
    return level.ordinal() > _getLevel().ordinal();
  }

  private static LEVEL _getLevel() {
    return TaskBot.GetInstance().getLogger().level;
  }

  /* LOG */
  public static void log(LEVEL level, String message) {
    _log(level, message, null);
  }

  public static void log(LEVEL level, String format, Object... args) {
    _log(level, null, format, args);
  }

  public static void log(LEVEL level, String message, Exception e) {
    _log(level, message, e);
  }

  public static void log(LEVEL level, Exception e, String format, Object... args) {
    _log(level, e, format, args);
  }

  /* DEBUG */
  public static void debug(String message) {
    _log(LEVEL.DEBUG, message, null);
  }

  public static void debug(String format, Object... args) {
    _log(LEVEL.DEBUG, null, format, args);
  }

  /* INFO */
  public static void info(String message) {
    _log(LEVEL.INFO, message, null);
  }

  public static void info(String format, Object... args) {
    _log(LEVEL.INFO, null, format, args);
  }

  /* WARNING */
  public static void warning(String message) {
    _log(LEVEL.WARNING, message, null);
  }

  public static void warning(String format, Object... args) {
    _log(LEVEL.WARNING, null, format, args);
  }

  /* ERROR */
  public static void error(String message) {
    _log(LEVEL.ERROR, message, null);
  }

  public static void error(String format, Object... args) {
    _log(LEVEL.ERROR, null, format, args);
  }

  public static void error(String message, Exception e) {
    _log(LEVEL.ERROR, message, e);
  }

  public static void error(Exception e, String format, Object... args) {
    _log(LEVEL.ERROR, e, format, args);
  }

  /* CRITICAL */
  public static void critical(String message) {
    _log(LEVEL.CRITICAL, message, null);
  }

  public static void critical(String format, Object... args) {
    _log(LEVEL.CRITICAL, null, format, args);
  }

  public static void critical(String message, Exception e) {
    _log(LEVEL.CRITICAL, message, e);
  }

  public static void critical(Exception e, String format, Object... args) {
    _log(LEVEL.CRITICAL, e, format, args);
  }
}
