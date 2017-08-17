package com.eva.messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateMessageActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_message);
  }

  // called when the SendMessage button clicked
  public void onSendMessage(View view) {
    EditText msgEditText = (EditText) findViewById(R.id.smessage);
    String messageText = msgEditText.getText().toString();

    if (messageText.equals("hh")) {
      // to send a message
      Intent intent = new Intent(Intent.ACTION_SEND);
      // handle data with a MIME data-type
      intent.setType("text/plain");
      intent.putExtra(Intent.EXTRA_SUBJECT, "test");
      intent.putExtra(Intent.EXTRA_TEXT, messageText);

      // createChooser() allows you to specify a title for the chooser dialog
      // and doesn't give the user the option of selecting an activity to use
      // by default
      String chooserTitle = getString(R.string.chooser);
      Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
      startActivity(chosenIntent);

    } else {
      // start ReceiveMessageActivity
      Intent intent = new Intent(this, ReceiveMessageActivity.class);
      intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE, messageText);
      intent.putExtra(ReceiveMessageActivity.EXTRA_INT, 16);
      startActivity(intent);
    }
  }
}
