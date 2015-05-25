package com.julia.mytest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button button = (Button) findViewById(R.id.button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // first step: open app
        try {
          Intent intent =
              Intent
                  .parseUri(
                      "#Intent;component=com.sdu.didi.psnger/com.didi.frame.SplashActivity;end",
                      0);
          startActivity(intent);
        } catch (URISyntaxException e) {
          e.printStackTrace();
        }

        String pkgName = "com.sdu.didi.psnger";
        List<AccessAction> accessActionList = new ArrayList<AccessAction>();

        accessActionList.add(new AccessAction(AccessAction.KeyType.TEXT, "现在您要去哪？",
            AccessibilityNodeInfo.ACTION_CLICK, null));
        accessActionList.add(new AccessAction(AccessAction.KeyType.VIEWID,
            "com.sdu.didi.psnger:id/input_search_box", AccessibilityNodeInfo.ACTION_SET_TEXT,
            "西坝河"));
        accessActionList.add(new AccessAction(AccessAction.KeyType.VIEWID,
            "com.sdu.didi.psnger:id/custom_confirm", AccessibilityNodeInfo.ACTION_CLICK, null));

        MyTestAccessibilityService.addImpl(new OpenAppImpl(pkgName, accessActionList));
      }
    });

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    // noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
