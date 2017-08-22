package com.eva.starbuzz;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// because it's a list activity, it needs to extend the ListActivity
// class rather than Activity
public class DrinkCategoryActivity extends ListActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // You don't need to use the setContentView() method to say
    // what layout the list activity should use. This is because list
    // activities define their own layouts so you don't need to create
    // one yourself. Ths list activity handles this for you.

    // If you need to display data in a list view that comes from a nonstatic
    // source such as a Java array or database, you need to use an adapter.
    // An adapter acts as a bridge between the data source and the list view.

    // An arrayAdapter is a type of adapter that's used to bind arrays to views.
    ListView listDrinks = getListView();
    ArrayAdapter<Drink> listAdapter = new ArrayAdapter<Drink>(
            this,
            android.R.layout.simple_list_item_1,
            Drink.drinks);

    listDrinks.setAdapter(listAdapter);
  }

  @Override
  public void onListItemClick(ListView listView, View itemView, int position, long id) {
    Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
    intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int)id);
    startActivity(intent);
  }
}
