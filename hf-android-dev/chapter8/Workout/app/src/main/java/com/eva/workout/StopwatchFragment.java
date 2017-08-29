package com.eva.workout;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

// the activity will be destroyed and re-created when the device was rotated
public class StopwatchFragment extends Fragment implements View.OnClickListener {
  final static private String TAG = "StopwatchFragment";

  private int seconds = 0; // record the number of seconds passed
  private boolean running; // whether the stopwatch is running
  // record whether the stopwatch was running before the onStop() method
  // was called so that we know whether to set it running again when
  // the activity becomes visible again.
  private boolean wasRunning;

  // the activity gets launched, and the onCreate() method runs.
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Log.i(TAG, "onCreate");

    // retrieve the values of the seconds and running variables from the Bundle
    if (savedInstanceState != null) {
      Log.i(TAG, "restore state");
      seconds = savedInstanceState.getInt("seconds");
      running = savedInstanceState.getBoolean("running");
      wasRunning = savedInstanceState.getBoolean("wasRunning");
      if (wasRunning)
        running = true;
    }

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);
    runTimer(layout);

    // attach the OnClickListener to the buttons
    Button startButton = (Button) layout.findViewById(R.id.start_button);
    startButton.setOnClickListener(this);
    Button stopButton = (Button) layout.findViewById(R.id.stop_button);
    stopButton.setOnClickListener(this);
    Button resetButton = (Button) layout.findViewById(R.id.reset_button);
    resetButton.setOnClickListener(this);

    return layout;
  }

  // the onStart() method runs after the onCreate() method. It gets
  // called when the activity is about to become visible.
  // gets called when your activity becomes visible to the user
  // after the onStart() method has run, the user can see the activity
  // on the screen.
  // At this point, the activity is visible, but it doesn't have the focus.
  @Override
  public void onStart() {
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
  public void onResume() {
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
  public void onPause() {
    super.onPause();

    wasRunning = running;
    running = false;

    Log.i(TAG, "onPause");
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
  public void onStop() {
    super.onStop();

    // record whether the stopwatch was running when the onStop() called.
    wasRunning = running;
    running = false;

    Log.i(TAG, "onStop");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    Log.i(TAG, "onDestroy");
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.start_button:
        onClickStart(v);
        break;

      case R.id.stop_button:
        onClickStop(v);
        break;

      case R.id.reset_button:
        onClickReset(v);
        break;
    }
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
  private void runTimer(View view) {
    final TextView timeView = (TextView) view.findViewById(R.id.time_view);
    // create a new handler
    final Handler handler = new Handler();

    // call the post(), so the code in the Runnable will run almost immediately
    handler.post(new Runnable() {
      @Override
      public void run() {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String time = String.format(Locale.US, "%d:%02d:%02d",
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
