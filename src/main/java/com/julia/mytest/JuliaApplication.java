package com.julia.mytest;

import android.app.Application;
import android.content.Context;

/**
 * File description
 * <p/>
 * Created by yunjie@wandoujia.com on 15/5/20.
 */
public class JuliaApplication extends Application {

  private static Context context;

  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
  }

  public static Context getAppContext() {
    return context;
  }

}
