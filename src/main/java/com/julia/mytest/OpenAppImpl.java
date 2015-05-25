package com.julia.mytest;

import java.util.ArrayList;
import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * File description
 * <p/>
 * Created by yunjie@wandoujia.com on 15/5/16.
 */
public class OpenAppImpl implements IAccessibilityServiceImpl {

  private String pkgName;
  private List<AccessAction> accessActionList;

  public OpenAppImpl(String pkgName, List<AccessAction> accessActionList) {
    this.pkgName = pkgName;
    this.accessActionList = accessActionList;
  }

  @Override
  public void onAccessibilityEvent(AccessibilityEvent event, AccessibilityService service) {
    if (!event.getPackageName().equals(pkgName)) {
      return;
    }
    AccessibilityNodeInfo rootNode = service.getRootInActiveWindow();
    if (rootNode == null) {
      return;
    }
    for (AccessAction accessAction : accessActionList) {
      List<AccessibilityNodeInfo> targetNodes = null;
      switch (accessAction.keyType) {
        case TEXT:
          targetNodes = AccessibilityServiceUtil.findNodesByText(rootNode, accessAction.keyValue);
          break;
        case VIEWID:
          targetNodes = AccessibilityServiceUtil.findNodesByViewId(rootNode, accessAction.keyValue);
          break;
        case CONTENT_DESCRIPTION:
          AccessibilityNodeInfo node =
              AccessibilityServiceUtil.findNodesByContentDesc(rootNode, accessAction.keyValue);
          if (node != null) {
            targetNodes = new ArrayList<AccessibilityNodeInfo>();
            targetNodes.add(node);
          }
          break;
      }
      if (targetNodes != null && !accessAction.performed) {
        for (AccessibilityNodeInfo targetNode : targetNodes) {
          AccessibilityServiceUtil.performAction(targetNode, accessAction);
          targetNode.recycle();
        }
      }
    }

    rootNode.recycle();
  }

  @Override
  public void onInterrupt() {

  }

  @Override
  public boolean isCompleted() {
    if (accessActionList != null) {
      for (AccessAction accessAction : accessActionList) {
        if (!accessAction.performed) {
          return false;
        }
      }
    }
    return true;
  }

}
