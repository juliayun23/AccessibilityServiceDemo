package com.julia.mytest;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public interface IAccessibilityServiceImpl {
  void onAccessibilityEvent(AccessibilityEvent event, AccessibilityService service);

  void onInterrupt();

  boolean isCompleted();
}
