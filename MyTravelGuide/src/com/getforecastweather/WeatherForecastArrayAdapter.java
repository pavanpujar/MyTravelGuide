package com.getforecastweather;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherForecastArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> values;

	public WeatherForecastArrayAdapter(Context context, ArrayList<String> values) {
		super(context, R.layout.weatherforecastrow, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.weatherforecastrow, parent,
				false);
		TextView textView = (TextView) rowView.findViewById(R.id.weather_date);
		ImageView imageView = (ImageView) rowView
				.findViewById(R.id.weather_image);
		textView.setText(values.get(position).split(",")[0]);
		int resourceId = Integer.parseInt(values.get(position).split(",")[1]);
		imageView.setImageResource(resourceId);
		return rowView;
	}

}
