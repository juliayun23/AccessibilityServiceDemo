package com.julia.mytest;

import java.util.List;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Utility class to help handle AccessibilityService related operations.
 * <p/>
 * Created by yunjie@wandoujia.com on 15/5/16.
 */
public class AccessibilityServiceUtil {

  private static final String TAG = "ASUtil";

  public static void performAction(AccessibilityNodeInfo info, AccessAction accessAction) {
    switch (accessAction.actionType) {
      case AccessibilityNodeInfo.ACTION_CLICK:
        click(info);
        break;
      case AccessibilityNodeInfo.ACTION_COPY:
        copy(info);
        break;
      case AccessibilityNodeInfo.ACTION_PASTE:
        paste(info);
        break;
      case AccessibilityNodeInfo.ACTION_SET_TEXT:
        setText(info, accessAction.actionValue);
        break;
    }
    accessAction.performed = true;
  }

  public static AccessibilityNodeInfo findClickableNode(AccessibilityNodeInfo info) {
    if (info == null) {
      return null;
    }
    if (info.isClickable()) {
      return info;
    } else {
      return findClickableNode(info.getParent());
    }
  }

  public static void click(AccessibilityNodeInfo info) {
    AccessibilityNodeInfo clickableNode = findClickableNode(info);
    if (clickableNode != null) {
      clickableNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }
  }

  public static void copy(AccessibilityNodeInfo info) {
    if (info != null) {
      info.performAction(AccessibilityNodeInfo.ACTION_COPY);
    }
  }

  public static void paste(AccessibilityNodeInfo info) {
    if (info != null) {
      info.performAction(AccessibilityNodeInfo.ACTION_PASTE);
    }
  }

  public static void setText(AccessibilityNodeInfo info, String text) {
    if (info != null && !TextUtils.isEmpty(text)) {
      Bundle arguments = new Bundle();
      arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
          text);
      info.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
    }
  }

  public static List<AccessibilityNodeInfo> findNodesByText(AccessibilityNodeInfo root, String text) {
    return root.findAccessibilityNodeInfosByText(text);
  }

  public static List<AccessibilityNodeInfo> findNodesByViewId(AccessibilityNodeInfo root,
      String viewId) {
    return root.findAccessibilityNodeInfosByViewId(viewId);
  }

  public static AccessibilityNodeInfo findNodesByContentDesc(AccessibilityNodeInfo root,
      String targetContentDesc) {
    if (root == null || TextUtils.isEmpty(targetContentDesc)) {
      return null;
    }

    CharSequence contentDesc = root.getContentDescription();
    Log.d(TAG, "content desc: " + contentDesc);

    if (!TextUtils.isEmpty(contentDesc) && contentDesc.equals(targetContentDesc)) {
      return root;
    } else {
      for (int i = 0; i < root.getChildCount(); i++) {
        AccessibilityNodeInfo childNode = root.getChild(i);
        AccessibilityNodeInfo result = findNodesByContentDesc(childNode, targetContentDesc);
        if (result != null) {
          return result;
        }
      }
      return null;
    }
  }

  @TargetApi(Build.VERSION_CODES.KITKAT)
  public static String getForegroundActivityBelowLollipop() {
    try {
      ActivityManager activityManager =
          (ActivityManager) JuliaApplication.getAppContext().getSystemService(
              Context.ACTIVITY_SERVICE);
      List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(1);
      ComponentName cn = taskInfoList.get(0).topActivity;
      return cn.getClassName();
    } catch (AssertionError e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    return null;
  }
}
