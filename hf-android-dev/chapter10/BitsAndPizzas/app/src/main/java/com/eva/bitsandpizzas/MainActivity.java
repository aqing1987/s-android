package com.eva.bitsandpizzas;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.ShareActionProvider;
import android.support.v7.widget.ShareActionProvider;

public class MainActivity extends AppCompatActivity {

  private ShareActionProvider shareActionProvider;
  private String[] titles;
  private ListView drawerList;
  private DrawerLayout drawerLayout;
  private ActionBarDrawerToggle drawerToggle;
  private int currentPosition = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);



    titles = getResources().getStringArray(R.array.titles);
    drawerList = (ListView) findViewById(R.id.drawer);
    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    // Populate the ListView
    // use an ArrayAdapter to populate the ListView
    // Using simple_list_item_activated_1 means that the item the
    // user clicks on is highlighted
    drawerList.setAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_activated_1, titles));
    drawerList.setOnItemClickListener(new DrawerItemClickListener());

    // if the MainActivity is newly created, use the selectItem() method
    // to display TopFragment
    // display the correct fragment
    if (savedInstanceState != null) {
      currentPosition = savedInstanceState.getInt("position");
      setActionBarTitle(currentPosition);
    } else {
      selectItem(0);
    }

    // Create the ActionBarDrawerToggle
    drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
            R.string.open_drawer, R.string.close_drawer) {
      // called when a drawer has settled in a completely closed state
      public void onDrawerClosed(View view) {
        super.onDrawerClosed(view);
        supportInvalidateOptionsMenu();
      }

      // called when a drawer has settled in a completely opened state
      public void onDrawerOpened(View view) {
        super.onDrawerOpened(view);
        supportInvalidateOptionsMenu();
      }
    };
    drawerLayout.setDrawerListener(drawerToggle);

    // enable the activity's UP button, the Up button will
    // be used to activate the drawer instead of navigating up the
    // app's hierarchy.
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeButtonEnabled(true);
    }


    getSupportFragmentManager().addOnBackStackChangedListener(
            new FragmentManager.OnBackStackChangedListener() {
              // This gets called when the back stack changes
              public void onBackStackChanged() {
                FragmentManager fragMan = getSupportFragmentManager();
                Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
                if (fragment instanceof TopFragment) {
                  currentPosition = 0;
                } else if (fragment instanceof PizzaFragment) {
                  currentPosition = 1;
                } else if (fragment instanceof PastaFragment) {
                  currentPosition = 2;
                } else if (fragment instanceof StoresFragment) {
                  currentPosition = 3;
                }

                // set the action bar title and highlight the correct
                // item in the drawer ListView
                setActionBarTitle(currentPosition);
                drawerList.setItemChecked(currentPosition, true);
              }
            });

  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    // You need to add this method to your activity so that the
    // state of the ActionBarDrawerToggle is in sync with the
    // state of the drawer.
    // Sync the toggle state after onRestoreInstanceState has occurred
    drawerToggle.syncState();
  }

  // You need to add this method to your activity so that any
  // configuration changes get passwd to the ActionBarDrawerToggle.
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }

  // called whenever we call invalidateOptionsMenu()
  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    // if the drawer is open, hide action items related to the content view
    boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
    menu.findItem(R.id.action_share).setVisible(!drawerOpen);
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // inflate the menu; this adds items to the action bar if it is present
    // menu: represents the action bar
    getMenuInflater().inflate(R.menu.menu_main, menu);

    MenuItem menuItem = menu.findItem(R.id.action_share);
    shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
    setIntent("This is example text");

    return super.onCreateOptionsMenu(menu);
  }

  // the MenuItem object is the item on the action bar that was clicked
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // ActionBarDrawerToggle handle click
    if (drawerToggle.onOptionsItemSelected(item)) {
      return true;
    }

    switch (item.getItemId()) {
      case R.id.action_create_order:
        // This intent is used to start OrderActivity when the
        // Create Order action item is clicked
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
        return true;
      case R.id.action_settings:
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt("position", currentPosition);
  }

  private void setIntent(String text) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_TEXT, text);
    shareActionProvider.setShareIntent(intent);
  }

  private void setActionBarTitle(int position) {
    String title;

    // if the user clicks the "HOME" option, use the app name for the title
    if (position == 0)
      title = getResources().getString(R.string.app_name);
    else
      title = titles[position];

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null)
      actionBar.setTitle(title);
  }

  private void selectItem(int position) {
    currentPosition = position;
    Fragment fragment;

    switch (position) {
      case 1:
        fragment = new PizzaFragment();
        break;

      case 2:
        fragment = new PastaFragment();
        break;

      case 3:
        fragment = new StoresFragment();
        break;

      default:
        fragment = new TopFragment();
    }

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.content_frame, fragment, "visible_fragment");
    ft.addToBackStack(null);
    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    ft.commit();

    setActionBarTitle(position);

    // close the drawer
    DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    // drawerList is the DrawerLayout's drawer. This tells the DrawerLayout
    // to close the drawerList drawer
    drawerLayout.closeDrawer(drawerList);
  }

  private class DrawerItemClickListener implements ListView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      // Code to run when the item gets clicked
      selectItem(position);
    }
  };
}
