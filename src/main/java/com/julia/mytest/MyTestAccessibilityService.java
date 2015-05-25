package com.julia.mytest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * File description
 * <p/>
 * Created by yunjie@wandoujia.com on 15/5/11.
 */
public class MyTestAccessibilityService extends AccessibilityService {

  private static final String TAG = "AccessibilityService";

  private static List<IAccessibilityServiceImpl> impls = new ArrayList<IAccessibilityServiceImpl>();

  public static void addImpl(IAccessibilityServiceImpl serviceImpl) {
    impls.add(serviceImpl);
  }

  @Override
  protected void onServiceConnected() {
    super.onServiceConnected();
  }

  @Override
  public boolean onUnbind(Intent intent) {
    return super.onUnbind(intent);
  }

  @Override
  public void onAccessibilityEvent(AccessibilityEvent event) {
    Log.d(TAG, "event from:---------------" + event.getPackageName());
    Log.d(TAG, "windowId: " + event.getWindowId());
    Log.d(TAG, "current activity: " + AccessibilityServiceUtil.getForegroundActivityBelowLollipop());

    String eventType = "";
    switch (event.getEventType()) {
      case AccessibilityEvent.TYPE_VIEW_CLICKED:
        eventType = " TYPE_VIEW_CLICKED";
        break;
      case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
        eventType = " TYPE_VIEW_LONG_CLICKED";
        break;
      case AccessibilityEvent.TYPE_VIEW_SELECTED:
        eventType = " TYPE_VIEW_SELECTED";
        break;
      case AccessibilityEvent.TYPE_VIEW_FOCUSED:
        eventType = " TYPE_VIEW_FOCUSED";
        break;
      case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
        eventType = " TYPE_VIEW_TEXT_CHANGED";
        break;
      case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
        eventType = " TYPE_WINDOW_STATE_CHANGED";
        break;
      case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
        eventType = " TYPE_NOTIFICATION_STATE_CHANGED";
        break;
      case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
        eventType = " TYPE_VIEW_HOVER_ENTER";
        break;
      case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
        eventType = " TYPE_VIEW_HOVER_EXIT";
        break;
      case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
        eventType = " TYPE_TOUCH_EXPLORATION_GESTURE_START";
        break;
      case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
        eventType = " TYPE_TOUCH_EXPLORATION_GESTURE_END";
        break;
      case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
        eventType = " TYPE_WINDOW_CONTENT_CHANGED";
        break;
      case AccessibilityEvent.TYPE_VIEW_SCROLLED:
        eventType = " TYPE_VIEW_SCROLLED";
        break;
      case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
        eventType = " TYPE_VIEW_TEXT_SELECTION_CHANGED";
        break;
      case AccessibilityEvent.TYPE_ANNOUNCEMENT:
        eventType = " TYPE_ANNOUNCEMENT";
        break;
      case AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY:
        eventType = " TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY";
        break;
      case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
        eventType = " TYPE_GESTURE_DETECTION_START";
        break;
      case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
        eventType = " TYPE_GESTURE_DETECTION_END";
        break;
      case AccessibilityEvent.TYPE_TOUCH_INTERACTION_START:
        eventType = " TYPE_TOUCH_INTERACTION_START";
        break;
      case AccessibilityEvent.TYPE_TOUCH_INTERACTION_END:
        eventType = " TYPE_TOUCH_INTERACTION_END";
        break;
      case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
        eventType = " TYPE_WINDOWS_CHANGED";
        break;
    }
    Log.d(TAG, "event type: " + eventType);

    for (IAccessibilityServiceImpl impl : impls) {
      impl.onAccessibilityEvent(event, this);
    }

    Iterator<IAccessibilityServiceImpl> iterator = impls.iterator();
    while (iterator.hasNext()) {
      if (iterator.next().isCompleted()) {
        iterator.remove();
      }
    }
  }

  @Override
  public void onInterrupt() {

  }

}
