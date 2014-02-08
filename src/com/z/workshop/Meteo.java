package com.z.workshop;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.z.workshop.meteo.ListViewAdapter;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Meteo extends Activity {

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		
		this.setContentView(R.layout.meteo);

		String weatherString = this.getIntent().getStringExtra("weather");
		ArrayList<JSONObject> weatherList = new ArrayList<JSONObject>();

		try {
			JSONObject weather = new JSONObject(weatherString);
			JSONArray results = weather.getJSONObject("query")
					.getJSONObject("results").getJSONObject("channel")
					.getJSONObject("item").getJSONArray("forecast");

			for (int i = 0; i < results.length(); i++) {
				weatherList.add(results.getJSONObject(i));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ListViewAdapter listAdapter = new ListViewAdapter(this,
				R.layout.meteo_list_view_item, R.id.meteo_element, weatherList);

		ListView listView = (ListView) this.findViewById(R.id.weather_list);
		listView.setAdapter(listAdapter);
	}
}
