package com.eva.messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReceiveMessageActivity extends AppCompatActivity {

  public static final String EXTRA_MESSAGE = "message";
  public static final String EXTRA_INT = "test_int";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_receive_message);

    Intent intent = getIntent();
    String messageText = intent.getStringExtra(EXTRA_MESSAGE);
    int val = intent.getIntExtra(EXTRA_INT, 0);
    messageText += val;
    TextView tv = (TextView) findViewById(R.id.rmessage);
    tv.setText(messageText);
  }
}
