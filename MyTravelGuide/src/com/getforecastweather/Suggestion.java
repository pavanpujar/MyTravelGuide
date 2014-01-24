package com.getforecastweather;

public class Suggestion {
    int suggestionID;
    String weatherCondition;
	String onTopSuggestion;
	String onBottomSuggestion;
	String accessories;

	
	

	public int getSuggestionID() {
		return suggestionID;
	}

	public void setSuggestionID(int suggestionID) {
		this.suggestionID = suggestionID;
	}

	public String getWeatherCondition() {
		return weatherCondition;
	}

	public void setWeatherCondition(String weatherCondition) {
		this.weatherCondition = weatherCondition;
	}

	public String getOnTopSuggestion() {
		return onTopSuggestion;
	}

	public void setOnTopSuggestion(String onTopSuggestion) {
		this.onTopSuggestion = onTopSuggestion;
	}

	public String getOnBottomSuggestion() {
		return onBottomSuggestion;
	}

	public void setOnBottomSuggestion(String onBottomSuggestion) {
		this.onBottomSuggestion = onBottomSuggestion;
	}

	public String getAccessories() {
		return accessories;
	}

	public void setAccessories(String accessories) {
		this.accessories = accessories;
	}
}
