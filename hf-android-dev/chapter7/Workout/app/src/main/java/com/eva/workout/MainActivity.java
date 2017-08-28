package com.eva.workout;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements
        WorkoutListFragment.WorkoutListListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  // When an item is clicked in the fragment list, the itemClick()
  // method in the activity will be called.
  @Override
  public void itemClicked(long id) {
    // if the frame layut isn't there, the app must be running on a
    // device with a smaller screen.
    View fragmentContainer = findViewById(R.id.fragment_container);
    if (fragmentContainer != null) {
      WorkoutDetailFragment details = new WorkoutDetailFragment();
      // Start the fragment transaction
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      details.setWorkoutId(id);

      // replace the fragment and add it to the back stack
      ft.replace(R.id.fragment_container, details);
      ft.addToBackStack(null);

      // get the new and old fragments to fade in and out
      ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
      // commit the transaction
      ft.commit();
    } else {
      Intent intent = new Intent(this, DetailActivity.class);
      intent.putExtra(DetailActivity.EXTRA_WORKOUT_ID, (int)id);
      startActivity(intent);
    }
  }
}
