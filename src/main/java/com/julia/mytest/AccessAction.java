package com.julia.mytest;

/**
 * File description
 * <p/>
 * Created by yunjie@wandoujia.com on 15/5/16.
 */
public class AccessAction {

  public enum KeyType {
    TEXT,
    VIEWID,
    CONTENT_DESCRIPTION;
  }

  public KeyType keyType;
  public String keyValue;
  public int actionType;
  public String actionValue;
  /* logic flag */
  public boolean performed = false;

  public AccessAction(KeyType keyType, String keyValue, int actionType, String actionValue) {
    this.keyType = keyType;
    this.keyValue = keyValue;
    this.actionType = actionType;
    this.actionValue = actionValue;
  }
}
