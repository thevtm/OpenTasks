package com.OpenTasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VTM on 9/6/2016.
 */
public abstract class Task {

  private Task parent;
  private List<Task> children;

  public final String name;
  public final int priority;

  private Task lastTaskExecuted;

  public Task(String name, int priority) {
    this.name = name;
    this.priority = priority;

    children = new ArrayList<>();
  }

  public abstract boolean validate();

  public void execute() {
    // No child case
    if (children.isEmpty()) return;

    // First execution
    boolean isFirstExec = lastTaskExecuted == null;
    if (isFirstExec) {
      lastTaskExecuted = children.get(0);
    }

    // First try to run the last task executed
    if (lastTaskExecuted.validate()) {
      if (isFirstExec) lastTaskExecuted.begin();
      lastTaskExecuted.execute();
      return;
    } else if (!isFirstExec){
      lastTaskExecuted.end();
    }

    // Secondly try to execute the others tasks in order
    int indefOfLastTaskExecuted = children.indexOf(lastTaskExecuted);
    int nChildren = children.size();

    for (int i = (indefOfLastTaskExecuted + 1) % nChildren; i != indefOfLastTaskExecuted; i = (i + 1) % nChildren) {
      Task ti = children.get(i);
      if (ti.validate()) {
        lastTaskExecuted = ti;
        lastTaskExecuted.begin();
        ti.execute();
        return;
      }
    }
  }

  public void configure(Object config) {
    // Config children
    for (Task child : children) child.configure(config);
  }

  public void begin() {

  }

  public void end() {

  }

  public void OnStart() {
    // Start children
    for (Task child : children) child.OnStart();
  }

  public void OnStop() {
    // Stop children
    for (Task child : children) child.OnStop();
  }

  public void OnPause() {
    // Stop children
    for (Task child : children) child.OnPause();
  }

  public void OnResume() {
    // Stop children
    for (Task child : children) child.OnResume();
  }

  public void addChild(Task child) {
    if (child == this) throw new RuntimeException("A child can't be its own parent.");
    if (children.contains(child)) throw new RuntimeException("This child has already been added.");

    // Add child to the children list
    children.add(child);
    child.setParent(this);

    // Sort children based on priority
    children.sort((childA, childB) -> childA.priority - childB.priority);
  }

  public void removeChild(Task child) {
    if (!children.contains(child)) {
      throw new RuntimeException("I'm not your father Task. (The task you are trying to remove is not a child of this task)");
    }

    if (lastTaskExecuted == child) {
      lastTaskExecuted = null;
    }

    children.remove(child);
    child.setParent(null);
  }

  public void clearChildren() {
    for (Task child : children) {
      child.setParent(null);
    }

    lastTaskExecuted = null;
    children.clear();
  }

  public Task getParent() {
    return parent;
  }

  private void setParent(Task parent) {
    this.parent = parent;
  }

  public Task getLastTaskExecuted() {
    return lastTaskExecuted;
  }


}
