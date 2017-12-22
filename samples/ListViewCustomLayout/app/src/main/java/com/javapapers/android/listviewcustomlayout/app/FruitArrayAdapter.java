package com.javapapers.android.listviewcustomlayout.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FruitArrayAdapter extends ArrayAdapter<Fruit> {
    private static final String TAG = "FruitArrayAdapter";
	private List<Fruit> fruitList = new ArrayList<Fruit>();

    static class FruitViewHolder {
        ImageView fruitImg;
        TextView fruitName;
        TextView calories;
    }

    public FruitArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

	@Override
	public void add(Fruit object) {
		fruitList.add(object);
		super.add(object);
	}

    @Override
	public int getCount() {
		return this.fruitList.size();
	}

    @Override
	public Fruit getItem(int index) {
		return this.fruitList.get(index);
	}

    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
        FruitViewHolder viewHolder;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.listview_row_layout, parent, false);
            viewHolder = new FruitViewHolder();
            viewHolder.fruitImg = (ImageView) row.findViewById(R.id.fruitImg);
            viewHolder.fruitName = (TextView) row.findViewById(R.id.fruitName);
            viewHolder.calories = (TextView) row.findViewById(R.id.calories);
            row.setTag(viewHolder);
		} else {
            viewHolder = (FruitViewHolder)row.getTag();
        }
		Fruit fruit = getItem(position);
        viewHolder.fruitImg.setImageResource(fruit.getFruitImg());
        viewHolder.fruitName.setText(fruit.getFruitName());
        viewHolder.calories.setText(fruit.getCalories());
		return row;
	}

    public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
}
