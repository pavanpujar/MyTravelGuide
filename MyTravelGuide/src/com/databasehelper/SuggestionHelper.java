package com.databasehelper;

import java.util.ArrayList;
import java.util.List;

import com.getforecastweather.Suggestion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SuggestionHelper extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "suggestionDatabase";

	// Table Name
	private static final String TABLE_SUGGESTION = "suggestions";

	// Column names of TABLE_SUGGESTION
	private static final String KEY_ID = "suggestionID";
	private static final String KEY_WEATHER_CONDITION = "weatherCondition";
	private static final String KEY_ON_TOP_SUGGESTION = "onTopSuggestion";
	private static final String KEY_ON_BOTTOM_SUGGESTION = "onBottomSuggestion";
	private static final String KEY_ACCESORIES = "accessories";

	// Table Create Statement
	private static final String CREATE_TABLE_SUGGESTION = "CREATE TABLE "
			+ TABLE_SUGGESTION + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_WEATHER_CONDITION + " TEXT," + KEY_ON_TOP_SUGGESTION
			+ " TEXT," + KEY_ON_BOTTOM_SUGGESTION + " TEXT," + KEY_ACCESORIES
			+ " TEXT" + ")";

	public SuggestionHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Creating Suggestion Table
		db.execSQL(CREATE_TABLE_SUGGESTION);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// On Upgrade drop the Older table
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUGGESTION);

		// Create New table
		onCreate(db);
	}

	public void createAllSuggestions() {
		ArrayList<Suggestion> suggestionList = new ArrayList<Suggestion>();
		SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
		String[] weather_Condition = { "image0", "image1", "image2", "image3",
				"image4", "image5", "image6", "image7", "image8", "image9",
				"image10", "image11", "image12", "image13", "image14",
				"image15", "image16", "image17", "image18", "image19",
				"image20", "image21", "image22", "image23", "image24",
				"image25", "image26", "image27", "image28", "image29",
				"image30", "image31", "image32", "image33", "image34",
				"image35", "image36", "image37", "image38", "image39",
				"image40", "image41", "image42", "image43", "image44",
				"image45", "image46", "image47" };
		String[] top_Suggestion = { "ALERT: Tornado Warning!",
				"ALERT: Tropical Storm Warning!", "ALERT: Hurricane Warning!",
				"Long Sleeve Shirt and Rain Coat",
				"Long Sleeve Shirt and Rain Coat",
				"Long Sleeve Shirt,Rain Coat and Light Jacket",
				"Long Sleeve Shirt and Rain Coat",
				"Long Sleeve Shirt,Rain Coat and Light Jacket",
				"Long Sleeve Shirt,Rain Coat and Light Jacket",
				"Long Sleeve Shirt and Rain Coat",
				"Long Sleeve Shirt,Rain Coat and Light Jacket",
				"Long Sleeve Shirt and Rain Coat",
				"Long Sleeve Shirt and Rain Coat",
				"Long sleeve shirt,Winter Coat or Sweater",
				"Long sleeve shirt and Winter Coat",
				"Long sleeve shirt and Winter Coat",
				"Long sleeve shirt,Winter Coat and Sweater",
				"Long sleeve shirt and Winter Coat",
				"Long sleeve shirt,Light Jacket and Rain Coat",
				"Long sleeve shirt,Light Jacket and Rain Coat",
				"Short sleeve shirt and Light Jacket", "Long sleeve shirt",
				"Short sleeve shirt and Light Jacket",
				"Short sleeve shirt and Light Jacket",
				"Short sleeve shirt and Light Jacket",
				"Short sleeve shirt and Light Jacket",
				"Short sleeve shirt,Light Jacket and Sweater",
				"Long sleeve shirt and Light Jacket",
				"Long sleeve shirt and Light Jacket",
				"Long sleeve shirt and Light Jacket",
				"Long sleeve shirt and Light Jacket",
				"Long sleeve shirt and Light Jacket",
				"Short sleeve shirt and Light Jacket",
				"Short sleeve shirt and Light Jacket",
				"Short sleeve shirt and Light Jacket",
				"Short sleeve shirt and Light Jacket",
				"Long Sleeve Shirt,Rain Coat and Light Jacket",
				"Short sleeve shirt", "Long sleeve shirt and Light Jacket",
				"Long sleeve shirt and Light Jacket",
				"Long sleeve shirt and Light Jacket",
				"Long sleeve shirt ,Rain Coat and Light Jacket",
				"Long sleeve shirt ,Sweaters and Winter Coat",
				"Long sleeve shirt and Winter Coat",
				"Long sleeve shirt ,Sweaters and Winter Coat",
				"Long sleeve shirt and Light Jacket",
				"Long sleeve shirt and Rain Coat",
				"Long sleeve shirt ,Sweaters and Winter Coat",
				"Long sleeve shirt and Rain Coat", "No Suggestion" };
		String[] bottom_Suggestion = { "ALERT: Tornado Warning!",
				"ALERT: Tropical Storm Warning!", "ALERT: Hurricane Warning!",
				"Pants or Jeans", "Pants or Jeans", "Pants or Jeans",
				"Pants or Jeans", "Pants or Jeans", "Pants or Jeans",
				"Pants or Jeans", "Pants or Jeans", "Pants or Jeans",
				"Pants or Jeans", "Pants or Jeans", "Pants or Jeans",
				"Pants or Jeans", "Pants or Jeans", "Pants or Jeans",
				"Pants or Jeans", "Shorts or Jeans", "Shorts or Jeans",
				"Shorts or Jeans", "Shorts or Jeans", "Pants or Jeans",
				"Pants or Jeans", "Pants or Jeans", "Pants or Jeans",
				"Pants or Jeans", "Pants or Jeans", "Pants or Jeans",
				"Pants or Jeans", "Shorts or Jeans", "Shorts or Jeans",
				"Shorts or Jeans", "Shorts or Jeans", "Pants or Jeans",
				"Shorts", "Pants or Jeans", "Pants or Jeans", "Pants or Jeans",
				"Pants or Jeans", "Pants or Jeans", "Pants or Jeans",
				"Pants or Jeans", "Pants or Jeans", "Pants or Jeans",
				"Pants or Jeans", "Pants or Jeans", "Not Available" };
		String[] suggestion_accessories = { "ALERT: Tornado Warning!",
				"ALERT: Tropical Storm Warning!", "ALERT: Hurricane Warning!",
				"Umbrella and Cap", "Umbrella and Cap",
				"Umbrella,Gloves and Scarf", "Umbrella and Cap",
				"Umbrella,Gloves and Scarf", "Umbrella,Gloves and Cap",
				"Umbrella", "Umbrella,Gloves and Cap", "Umbrella", "Umbrella",
				"Umbrella,Gloves and Scarf", "Umbrella,Gloves and Scarf",
				"Umbrella,Gloves and Scarf", "Umbrella,Gloves and Scarf",
				"Umbrella and Cap", "Umbrella and Cap", "Sunglasses and Hat",
				"Hat", "Sunglasses and Hat", "Hat", "Scarf", "Scarf",
				"Golves and Scarf", "Umbrella", "Umbrella", "Umbrella",
				"Umbrella", "Umbrella", "Scarf", "Sunglasses and Hat", "Scarf",
				"Sunglasses and Hat", "Umbrella", "Sunglasses and Hat",
				"Umbrella", "Umbrella", "Umbrella", "Umbrella",
				"Scarf and Gloves", "Scarf and Gloves", "Scarf and Gloves",
				"Umbrella", "Umbrella", "Scarf and Gloves", "Umbrella",
				"Not Available" };

		for (int i = 0; i < weather_Condition.length; i++) {
			Suggestion s = new Suggestion();
			s.setSuggestionID(i + 1);
			s.setWeatherCondition(weather_Condition[i]);
			s.setOnTopSuggestion(top_Suggestion[i]);
			s.setOnBottomSuggestion(bottom_Suggestion[i]);
			s.setAccessories(suggestion_accessories[i]);
			suggestionList.add(s);
		}

		for (int j = 0; j < weather_Condition.length; j++) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(KEY_ID, suggestionList.get(j).getSuggestionID());
			contentValues.put(KEY_WEATHER_CONDITION, suggestionList.get(j)
					.getWeatherCondition());
			contentValues.put(KEY_ON_TOP_SUGGESTION, suggestionList.get(j)
					.getOnTopSuggestion());
			contentValues.put(KEY_ON_BOTTOM_SUGGESTION, suggestionList.get(j)
					.getOnBottomSuggestion());
			contentValues.put(KEY_ACCESORIES, suggestionList.get(j)
					.getAccessories());
			sqLiteDatabase.insert(TABLE_SUGGESTION, null, contentValues);
		}

	}

	public Suggestion getSuggestion(long id) {
		SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
		String select_Query = "SELECT *FROM " + TABLE_SUGGESTION + " WHERE "
				+ KEY_ID + " = " + id;

		Cursor cursor = sqLiteDatabase.rawQuery(select_Query, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		Suggestion sug = new Suggestion();
		sug.setSuggestionID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		sug.setWeatherCondition(cursor.getString(cursor
				.getColumnIndex(KEY_WEATHER_CONDITION)));
		sug.setOnTopSuggestion(cursor.getString(cursor
				.getColumnIndex(KEY_ON_TOP_SUGGESTION)));
		sug.setOnBottomSuggestion(cursor.getString(cursor
				.getColumnIndex(KEY_ON_BOTTOM_SUGGESTION)));
		sug.setAccessories(cursor.getString(cursor
				.getColumnIndex(KEY_ACCESORIES)));
		return sug;

	}

	public List<Suggestion> getAllSuggestion() {

		List<Suggestion> suggestionList = new ArrayList<Suggestion>();
		SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
		String select_Query = "SELECT *FROM " + TABLE_SUGGESTION;
		Cursor cursor = sqLiteDatabase.rawQuery(select_Query, null);
		if (cursor.moveToFirst()) {
			do {
				Suggestion sug = new Suggestion();
				sug.setSuggestionID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				sug.setWeatherCondition(cursor.getString(cursor
						.getColumnIndex(KEY_WEATHER_CONDITION)));
				sug.setOnTopSuggestion(cursor.getString(cursor
						.getColumnIndex(KEY_ON_TOP_SUGGESTION)));
				sug.setOnBottomSuggestion(cursor.getString(cursor
						.getColumnIndex(KEY_ON_BOTTOM_SUGGESTION)));
				sug.setAccessories(cursor.getString(cursor
						.getColumnIndex(KEY_ACCESORIES)));
				suggestionList.add(sug);
			} while (cursor.moveToNext());
		}
		return suggestionList;
	}

	public void deleteSuggestion(String condition) {
		SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
		sqLiteDatabase.delete(TABLE_SUGGESTION, KEY_WEATHER_CONDITION + " = ?",
				new String[] { String.valueOf(condition) });
	}

	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

}
