package com.OpenTasks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VTM on 4/7/2016.
 */
public class Logger {

  public enum LEVEL {
    DEBUG(10), INFO(20), WARNING(30), ERROR(40), CRITICAL(50);

    public final int level;

    LEVEL(int level) {
      this.level = level;
    }
  }

  public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

  private static void _log(LEVEL level, String message, Exception exception) {
    // Get current timestamp
    String timestamp = dateFormat.format(new Date());

    // Get caller method name
    StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
    StackTraceElement e = stacktrace[3];
    String className = e.getClassName();
    String methodName = e.getMethodName();

    // Print out
    System.out.println(String.format("%s %s %s", timestamp, className, methodName));
    System.out.println(String.format("[%s] %s", level.name(), message));
    if (exception != null) {
      exception.printStackTrace();
    }
  }

  /* LOG */
  public static void log(LEVEL level, String message) {
    _log(level, message, null);
  }

  public static void log(LEVEL level, String format, Object... args) {
    String message = String.format(format, args);
    _log(level, message, null);
  }

  public static void log(LEVEL level, String message, Exception e) {
    _log(level, message, e);
  }

  public static void log(LEVEL level, Exception e, String format, Object... args) {
    String message = String.format(format, args);
    _log(level, message, e);
  }

  /* DEBUG */
  public static void debug(String message) {
    _log(LEVEL.DEBUG, message, null);
  }

  public static void debug(String format, Object... args) {
    String message = String.format(format, args);
    _log(LEVEL.DEBUG, message, null);
  }

  /* INFO */
  public static void info(String message) {
    _log(LEVEL.INFO, message, null);
  }

  public static void info(String format, Object... args) {
    String message = String.format(format, args);
    _log(LEVEL.INFO, message, null);
  }

  /* WARNING */
  public static void warning(String message) {
    _log(LEVEL.WARNING, message, null);
  }

  public static void warning(String format, Object... args) {
    String message = String.format(format, args);
    _log(LEVEL.WARNING, message, null);
  }

  /* ERROR */
  public static void error(String message) {
    _log(LEVEL.ERROR, message, null);
  }

  public static void error(String format, Object... args) {
    String message = String.format(format, args);
    _log(LEVEL.ERROR, message, null);
  }

  public static void error(String message, Exception e) {
    _log(LEVEL.ERROR, message, e);
  }

  public static void error(Exception e, String format, Object... args) {
    String message = String.format(format, args);
    _log(LEVEL.ERROR, message, e);
  }

  /* CRITICAL */
  public static void critical(String message) {
    _log(LEVEL.CRITICAL, message, null);
  }

  public static void critical(String format, Object... args) {
    String message = String.format(format, args);
    _log(LEVEL.CRITICAL, message, null);
  }

  public static void critical(String message, Exception e) {
    _log(LEVEL.CRITICAL, message, e);
  }

  public static void critical(Exception e, String format, Object... args) {
    String message = String.format(format, args);
    _log(LEVEL.CRITICAL, message, e);
  }
}
