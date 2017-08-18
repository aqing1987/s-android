package com.eva.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

// the activity will be destroyed and re-created when the device was rotated
public class StopwatchActivity extends AppCompatActivity {
  final static private String TAG = StopwatchActivity.class.getSimpleName();

  private int seconds = 0; // record the number of seconds passed
  private boolean running; // whether the stopwatch is running
  // record whether the stopwatch was running before the onStop() method
  // was called so that we know whether to set it running again when
  // the activity becomes visible again.
  private boolean wasRunning;

  // the activity gets launched, and the onCreate() method runs.
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_stopwatch);

    Log.i(TAG, "onCreate");

    // retrieve the values of the seconds and running variables from the Bundle
    if (savedInstanceState != null) {
      Log.i(TAG, "restore state");
      seconds = savedInstanceState.getInt("seconds");
      running = savedInstanceState.getBoolean("running");
      wasRunning = savedInstanceState.getBoolean("wasRunning");
    }

    runTimer();
  }

  // the onStart() method runs after the onCreate() method. It gets
  // called when the activity is about to become visible.
  // gets called when your activity becomes visible to the user
  // after the onStart() method has run, the user can see the activity
  // on the screen.
  // At this point, the activity is visible, but it doesn't have the focus.
  @Override
  protected void onStart() {
    super.onStart();

    if (wasRunning)
      running = true;

    Log.i(TAG, "onStart");
  }

  // the onResume() method runs after the onStart() method. It gets called
  // when the activity is about to move into the foreground.
  // After the onResume() method has run, the activity has the focus and
  // the user can interact with it.
  // If the activity moves into the foreground again, the onResume() method
  // gets called.
  // If you have an activity that's visible, but never in the foreground and
  // never has the focus, the onPause() and onResume() methods never get called.
  @Override
  protected void onResume() {
    super.onResume();

    if (wasRunning)
      running = true;

    Log.i(TAG, "onResume");
  }

  // the onPause() method runs when the activity stops being in the foreground
  // After the onPause() method has run, the activity is still visible but
  // doesn't have the focus.
  // As the activity moves from running to destroyed, the onPause() method gets
  // called before onStop()
  // If an activity stops or gets destroyed before it appears in the foreground,
  // the onStart() method is followed by the onStop() method. onResume() and
  // onPause() are bypassed.
  @Override
  protected void onPause() {
    super.onPause();

    wasRunning = running;
    running = false;

    Log.i(TAG, "onPause");
  }


  // onRestart() is used when you only want code to run when an app becomes
  // visible after having previously been invisible. It doesn't run when the
  // activity becomes visible for the first time.
  // When you rotate the device, the activity is destoyed and a new one is created
  // in its place. If we'd put code in the onRestart() method instead, it would not
  // have run when the activity was re-created. The onStart() method gets called in
  // both situations.

  // gets called after your activity has been made invisible, before it gets
  // made visible again
  // If the activity becomes visible to the user again, the onRestart() method
  // gets called followed by onStart() and onResume()
  @Override
  protected void onRestart() {
    super.onRestart();

    Log.i(TAG, "onRestart");
  }

  // the onSaveInstanceState() method gets called before onDestroy()
  // it gives you a chance to save your activity's state before onStop()
  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    savedInstanceState.putInt("seconds", seconds);
    savedInstanceState.putBoolean("running", running);
    savedInstanceState.putBoolean("wasRunning", wasRunning);
    Log.i(TAG, "onSaveInstanceState");
  }

  // gets called when your activity has stopped being visible to the user
  // this might be because it's completely hidden by another activity that's
  // appeared on top of it, or because the activity is going to be destroyed
  // After the onStop() method has run, the activity is no longer visible.
  // The onStop() method will usually get called before onDestroy(), but it may
  // get bypassed if the device is extremely low on memory.
  @Override
  protected void onStop() {
    super.onStop();

    // record whether the stopwatch was running when the onStop() called.
    wasRunning = running;
    running = false;

    Log.i(TAG, "onStop");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    Log.i(TAG, "onDestroy");
  }

  // Start the stopwatch when the Start button clicked
  public void onClickStart(View view) {
    running = true;
  }

  // Stop the stopwatch when the Stop button clicked
  public void onClickStop(View view) {
    running = false;
  }

  // Reset the stopwatch when the Reset button clicked
  public void onClickReset(View view) {
    running = false;
    seconds = 0;
  }

  // sets the number of seconds on the timer
  private void runTimer() {
    final TextView timeView = (TextView) findViewById(R.id.time_view);
    // create a new handler
    final Handler handler = new Handler();

    // call the post(), so the code in the Runnable will run almost immediately
    handler.post(new Runnable() {
      @Override
      public void run() {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String time = String.format("%d:%02d:%02d",
                hours, minutes, secs);
        timeView.setText(time);

        if (running)
          seconds++;

        // post the code in the Runnable to be run again after a delay of
        // 1000 milliseconds
        handler.postDelayed(this, 1000);
      }
    });
  }
}
