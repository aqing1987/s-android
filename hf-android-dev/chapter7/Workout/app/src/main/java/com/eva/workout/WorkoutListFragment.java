package com.eva.workout;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


// list fragment
public class WorkoutListFragment extends ListFragment {

  static interface WorkoutListListener {
    void itemClicked(long id);
  };

  // add the listener to the fragment
  private WorkoutListListener listener;


  public WorkoutListFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    String[] names = new String[Workout.workouts.length];
    for (int i = 0; i < names.length; i++) {
      names[i] = Workout.workouts[i].getName();
    }

    // caate an array adapter
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            inflater.getContext(), android.R.layout.simple_list_item_1, names
    );

    // bind the array adapter to the list view
    setListAdapter(adapter);

    // calling the superclass onCreateView() method gives you the default
    // layout for the ListFragment
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  // This is called when the fragment gets attached to the activity
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    this.listener = (WorkoutListListener) activity;
  }


  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    // tell the listener when an item in the ListView clicked
    if (listener != null)
      listener.itemClicked(id);
  }

}
