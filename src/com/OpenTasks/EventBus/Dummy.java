package com.OpenTasks.EventBus;

import java.lang.annotation.Annotation;

/**
 * Created by VTM on 14/4/2016.
 * Required for the RuneMate thingy to import EventHandler.
 */
public class Dummy implements EventHandler {

  @Override
  public Class<? extends Annotation> annotationType() {
    return null;
  }

}
