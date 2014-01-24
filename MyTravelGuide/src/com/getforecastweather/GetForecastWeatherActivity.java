package com.getforecastweather;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.databasehelper.SuggestionHelper;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class GetForecastWeatherActivity extends ListActivity {
	ProgressDialog dialog;
	String woeid;
	String latitude;
	String longitude;
	ArrayList<String> weatherForecastResult = new ArrayList<String>();
	ArrayList<String> imageDateList = new ArrayList<String>();
	ListView list;
	ViewFlipper flipper;
	Button back_Button;
	TextView weatherdate;
	ImageView weatherimage;
	TextView weather_condition_title;
	TextView weather_condition_description;
	TextView weather_highest_temperature;
	TextView weather_lowest_temperature;
	TextView on_top_suggestion;
	TextView on_bottom_suggestion;
	TextView accessories;
	ImageView fahrenheit_button;
	ImageView celsius_button;
	SuggestionHelper suggestionHelper;
	Suggestion suggest;
	HashMap<String, Integer> conditionId;
	int index;
	String temperatureUnit = "F";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_weather);
		// Populates the database table
		suggestionHelper = new SuggestionHelper(getApplicationContext());
		List<Suggestion> suggestList = suggestionHelper.getAllSuggestion();
		for (int i = 0; i < suggestList.size(); i++) {
			suggestionHelper
					.deleteSuggestion(suggestList.get(i).weatherCondition);
		}
		loadDatabaseTable();
		suggest = new Suggestion();
		QueryYahooWeatherAPI queryYahooWeatherAPI = new QueryYahooWeatherAPI();
		queryYahooWeatherAPI.execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class MyWeather {
		String weatherForecastDay;
		String weatherForecastDate;
		String lowestTemperature;
		String highestTemperature;
		String weatherCondition;
	}

	public class QueryYahooWeatherAPI extends AsyncTask<String, String, String> {

		HttpClient httpClient = null;
		HttpContext localContext = null;
		HttpGet httpGetWeather = null;
		HttpGet httpGetWoeid = null;
		HttpResponse httpResponse = null;
		HttpEntity httpEntity = null;
		String conditionDescription;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(GetForecastWeatherActivity.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage("LOADING");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String queryResult = "";
			/*To do :I need latitude and longitude at this point
			Pass them as params to this method in execute method above at line 91*/
			latitude = "36.12";
			longitude = "-115.17";
			StringBuffer queryUrl = new StringBuffer();
			queryUrl.append("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20geo.placefinder%20where%20text%3D")
					.append("\'").append(latitude).append("%2C")
					.append(longitude).append("\'")
					.append("%20and%20gflags%3D").append("\'").append("R")
					.append("\'");
			httpGetWoeid = new HttpGet(queryUrl.toString());
			queryResult = getQueryResult(httpGetWoeid);
			Document woeidDocument = parseDocument(queryResult);
			woeid = woeidDocument.getElementsByTagName("woeid").item(0)
					.getTextContent();
			httpGetWeather = new HttpGet("http://weather.yahooapis.com/forecastrss?w=" + woeid+"&d=10");
			queryResult = getQueryResult(httpGetWeather);

			Document weatherDocument = parseDocument(queryResult);
			NodeList itemNode = weatherDocument.getElementsByTagName("item")
					.item(0).getChildNodes();
			weather_condition_title = (TextView) findViewById(R.id.weather_condition_title);
			weather_condition_title.setText(itemNode.item(1).getTextContent().substring(0,itemNode.item(1).getTextContent().indexOf("at")));

			NodeList forecastNode = weatherDocument.getElementsByTagName("yweather:forecast");
			for (int i = 0; i < 10; i++) {
				weatherForecastResult.add(forecastNode.item(i).getAttributes().getNamedItem("day").getNodeValue().toString()
						             .concat(",").concat(forecastNode.item(i).getAttributes().getNamedItem("date").getNodeValue().toString())
								     .concat(",").concat(forecastNode.item(i).getAttributes().getNamedItem("low").getNodeValue().toString())
								     .concat(",").concat(forecastNode.item(i).getAttributes().getNamedItem("high").getNodeValue().toString())
								     .concat(",").concat(forecastNode.item(i).getAttributes().getNamedItem("text").getNodeValue().toString())
				                     .concat(",").concat(forecastNode.item(i).getAttributes().getNamedItem("code").getNodeValue().toString()));

				conditionDescription = "image"+forecastNode.item(i).getAttributes().getNamedItem("code").getNodeValue().toString();
				int resID = getResources().getIdentifier(conditionDescription,"drawable", getApplication().getPackageName());
				String conditionText = Integer.toString(resID);

				imageDateList.add(forecastNode.item(i).getAttributes().getNamedItem("date").getNodeValue().toString().concat(",").concat(conditionText));

			}

			return queryResult;
		}

		@Override
		protected void onPostExecute(String result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			flipper = (ViewFlipper) findViewById(R.id.flipper);
			back_Button = (Button) findViewById(R.id.back_Button);

			getListView().setAdapter(new WeatherForecastArrayAdapter(getApplicationContext(),imageDateList));
			getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> listView,View view, int arg2, long arg3) {
							int position = listView.getPositionForView(view);
							index = position;
							weatherdate = (TextView) findViewById(R.id.weatherdate);
							weatherdate.setText(weatherForecastResult.get(position).split(",")[1]);
							int resourceId = Integer.parseInt(imageDateList.get(position).split(",")[1]);
							weatherimage = (ImageView) findViewById(R.id.weatherimage);
							weatherimage.setImageResource(resourceId);
							weather_lowest_temperature = (TextView) findViewById(R.id.weather_lowest_temperature);
							weather_lowest_temperature.setText(weatherForecastResult.get(position).split(",")[2]+" "+ temperatureUnit);
							weather_highest_temperature = (TextView) findViewById(R.id.weather_highest_temperature);
							weather_highest_temperature.setText(weatherForecastResult.get(position).split(",")[3]+" "+ temperatureUnit);
							weather_condition_description = (TextView) findViewById(R.id.weather_condition_description);
							weather_condition_description.setText(weatherForecastResult.get(position).split(",")[4]);
							suggest = suggestionHelper.getSuggestion(conditionId.get("image"+weatherForecastResult.get(position).split(",")[5]));
							on_top_suggestion = (TextView) findViewById(R.id.on_top_suggestion);
							on_top_suggestion.setText(suggest.getOnTopSuggestion());
							on_bottom_suggestion = (TextView) findViewById(R.id.on_bottom_suggestion);
							on_bottom_suggestion.setText(suggest.getOnBottomSuggestion());
							accessories = (TextView) findViewById(R.id.accessories);
							accessories.setText(suggest.getAccessories());
							fahrenheit_button = (ImageView) findViewById(R.id.fahrenheit_button);
							celsius_button = (ImageView) findViewById(R.id.celsius_button);
							fahrenheit_button.setOnClickListener(new OnClickListener() {
							  @Override
							  public void onClick(View v) {
							  String temperatureInCelsius;
							  fahrenheit_button.setVisibility(View.GONE);
							  celsius_button.setVisibility(View.VISIBLE);
							  temperatureUnit = "C";
							  Integer highest_temperature = Integer.parseInt(weatherForecastResult.get(index).split(",")[3]);
							  highest_temperature = ((highest_temperature - 32) * 5) / 9;
							  temperatureInCelsius = highest_temperature.toString();
							  weather_highest_temperature.setText(temperatureInCelsius+" "+ temperatureUnit);
							  Integer lowest_temperature = Integer.parseInt(weatherForecastResult.get(index).split(",")[2]);
							  lowest_temperature = ((lowest_temperature - 32) * 5) / 9;
							  temperatureInCelsius = lowest_temperature.toString();
							  weather_lowest_temperature.setText(temperatureInCelsius+" "+ temperatureUnit);
										}
							  });
							celsius_button.setOnClickListener(new OnClickListener() {
							  @Override
							  public void onClick(View v) {
							  celsius_button.setVisibility(View.GONE);
							  fahrenheit_button.setVisibility(View.VISIBLE);
							  temperatureUnit = "F";
							  weather_highest_temperature.setText(weatherForecastResult.get(index).split(",")[3]+" "+ temperatureUnit);
							  weather_lowest_temperature.setText(weatherForecastResult.get(index).split(",")[2]+" "+ temperatureUnit);
										}
							  });
							flipper.setInAnimation(inFromRightAnimation());
							flipper.setOutAnimation(outToLeftAnimation());
							flipper.showNext();
						}
					});
			back_Button.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					flipper.setInAnimation(inFromLeftAnimation());
					flipper.setOutAnimation(outToRightAnimation());
					flipper.showPrevious();
				}
			});

		}

		private Document parseDocument(String query) {
			Document document = null;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser;

			try {
				parser = dbFactory.newDocumentBuilder();
				document = parser.parse(new ByteArrayInputStream(query.getBytes()));
			} catch (ParserConfigurationException e1) {
				e1.printStackTrace();
				Toast.makeText(GetForecastWeatherActivity.this, e1.toString(),Toast.LENGTH_LONG).show();
			} catch (SAXException e) {
				e.printStackTrace();
				Toast.makeText(GetForecastWeatherActivity.this, e.toString(),Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(GetForecastWeatherActivity.this, e.toString(),Toast.LENGTH_LONG).show();
			}
			return document;
		}

		public String getQueryResult(HttpGet httpGet) {
			String result = "";
			httpClient = new DefaultHttpClient();
			localContext = new BasicHttpContext();
			try {
				httpResponse = httpClient.execute(httpGet, localContext);
				httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					InputStream inputStream = httpEntity.getContent();
					Reader in = new InputStreamReader(inputStream);
					BufferedReader bufferedreader = new BufferedReader(in);
					StringBuilder stringBuilder = new StringBuilder();

					String stringReadLine = null;

					while ((stringReadLine = bufferedreader.readLine()) != null) {
						stringBuilder.append(stringReadLine + "\n");
					}

					result = stringBuilder.toString();
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Toast.makeText(GetForecastWeatherActivity.this, e.toString(),Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(GetForecastWeatherActivity.this, e.toString(),Toast.LENGTH_LONG).show();
			}
			return result;
		}

	}

	public void loadDatabaseTable() {
		suggestionHelper.createAllSuggestions();
		conditionId = new HashMap<String, Integer>();
		for (int j = 0; j < 49; j++) {
			conditionId.put( "image"+j, j + 1);
		}
	}

	private Animation inFromRightAnimation() {
		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(500);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}

	private Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(500);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}

	private Animation inFromLeftAnimation() {

		Animation inFromLeft = new TranslateAnimation(

		Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT,
				0.0f,

				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f

		);
		inFromLeft.setDuration(500);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}

	private Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(500);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}

}
