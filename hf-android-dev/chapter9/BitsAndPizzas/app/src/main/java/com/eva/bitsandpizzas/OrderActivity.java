package com.eva.bitsandpizzas;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class OrderActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_order);

    ActionBar actionBar = getActionBar();
    if (actionBar != null)
      actionBar.setDisplayHomeAsUpEnabled(true);
  }
}
