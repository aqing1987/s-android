package com.eva.starbuzz;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

  private static final String DB_NAME = "starbuzz";
  private static final int DB_VERSION = 2;

  StarbuzzDatabaseHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);

  }

  // The onCreate() method is called when the database is created
  @Override
  public void onCreate(SQLiteDatabase db) {
    updateMyDatabase(db, 0, DB_VERSION);
  }

  // onUpgrade() gets called when the database needs to be upgraded
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    updateMyDatabase(db, oldVersion, newVersion);
  }

  private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion < 1) {
      String sql_cmd = "CREATE TABLE DRINK ("
              + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
              + "NAME TEXT, "
              + "DESCRIPTION TEXT, "
              + "IMAGE_RESOURCE_ID INTEGER);";
      db.execSQL(sql_cmd);

      insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
      insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam",
              R.drawable.cappuccino);
      insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);
    }

    if (oldVersion < 2) {
      String sql_cmd = "ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;";
      db.execSQL(sql_cmd);
    }
  }

  private static void insertDrink(SQLiteDatabase db, String name,
                                  String description, int resourceId) {
    ContentValues drinkValues = new ContentValues();
    drinkValues.put("NAME", name);
    drinkValues.put("DESCRIPTION", description);
    drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
    db.insert("DRINK", null, drinkValues);
  }
}
