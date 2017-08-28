package com.eva.workout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutDetailFragment extends Fragment {

  // This is the ID of the workout the user choose.
  private long workoutId;


  public WorkoutDetailFragment() {
    // Required empty public constructor
  }


  // it's called when Android needs the fragment's layout
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    if (savedInstanceState != null)
      workoutId = savedInstanceState.getLong("workoutId");

    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_workout_detail, container, false);
  }

  @Override
  public void onStart() {
    super.onStart();

    // the getView() method gets the fragment's root View.
    View view = getView();
    if (view != null) {
      TextView title = (TextView) view.findViewById(R.id.textTitle);
      Workout workout = Workout.workouts[(int)workoutId];
      title.setText(workout.getName());
      TextView descriptiono = (TextView) view.findViewById(R.id.textDescription);
      descriptiono.setText(workout.getDescription());
    }
  }

  // When we rotate the device, the fragment gets destroyed and
  // re-created along with the activity. This means that any local
  // variables used by the fragment can also lose their state.
  // so keep it.
  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    savedInstanceState.putLong("workoutId", workoutId);
  }

  public void setWorkoutId(long id) {
    this.workoutId = id;
  }

}
