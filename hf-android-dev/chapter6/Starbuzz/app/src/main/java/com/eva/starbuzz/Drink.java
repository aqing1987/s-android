package com.eva.starbuzz;

public class Drink {
  // each drink has a name, description, and image resource ID.
  private String name;
  private String description;
  private int imageResourceId;

  // drinks is an array of three Drinks
  public static final Drink[] drinks = {
          new Drink("Latte", "A couple of espresso shots with steamed milk",
                  R.drawable.latte),
          new Drink("Cappuccino", "Espresso, hot milk, and a steamed milk foam",
                  R.drawable.cappuccino),
          new Drink("Filter", "Highest quality beans roasted and brewed fresh",
                  R.drawable.filter)
  };

  private Drink(String name, String description, int imageResourceId) {
    this.name = name;
    this.description = description;
    this.imageResourceId = imageResourceId;
  }

  // These are getters for the private variables

  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }

  public int getImageResourceId() {
    return imageResourceId;
  }

  // The String representation of a Drink is its name.
  public String toString() {
    return this.name;
  }


}
