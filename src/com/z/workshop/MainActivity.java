package com.z.workshop;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.splash);
		this.setAction();

		Log.d("workshop", "created");

		this.CheckNetwork();
	}

	protected void setAction() {
		Button button2 = (Button) this
				.findViewById(R.id.refresh_connection_button_2);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("workshop", "refresh 2 click");
				MainActivity.this.CheckNetwork();
			}
		});
	}

	public void refreshConnection(View button) {
		Log.d("workshop", "refresh 1 click");
		CheckNetwork();
	}

	private void CheckNetwork() {
		ConnectivityManager connectivityManager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			Log.d("workshop", "network connected");
			this.showConnectionMessage(R.string.splash_connection_ok);
			this.loadData();
		} else {
			Log.d("workshop", "no connection");
			this.showConnectionMessage(R.string.splash_connection_error);
		}
	}

	protected void loadData() {
		String url = "http://query.yahooapis.com/v1/public/yql?q=select%20item%20from%20weather.forecast%20where%20location%3D%2248907%22&format=json";

		RequestQueue requestQueue = Volley.newRequestQueue(this);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("workshop", response.toString());

						Intent intent = new Intent(MainActivity.this,
								Meteo.class);
						intent.putExtra("weather", response.toString());
						MainActivity.this.startActivity(intent);
						MainActivity.this.finish();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("workshop", error.toString());
					}
				}) {
			@Override
			public Priority getPriority() {
				return Priority.HIGH;
			}
		};

		requestQueue.add(jsonObjectRequest);
	}

	private void showConnectionMessage(int messageId) {
		TextView errorMessage = (TextView) this
				.findViewById(R.id.connection_check_message);
		errorMessage.setText(this.getResources().getString(messageId));

		int visibility = messageId == R.string.splash_connection_error ? View.VISIBLE
				: View.INVISIBLE;
		// setVisibility(invisible) -> will still render the view's space in the
		// layout
		((Button) this.findViewById(R.id.refresh_connection_button))
				.setVisibility(visibility);
		((Button) this.findViewById(R.id.refresh_connection_button_2))
				.setVisibility(visibility);

		// can also use this shorthand
		// errorMessage.setText(R.string.splash_connection_error);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d("workshop", "start");
	}

	@Override
	protected void onResume() {
		super.onRestart();
		Log.d("workshop", "resume");
		this.CheckNetwork();
	}

	@Override
	protected void onStop() {
		super.onStart();
		Log.d("workshop", "stop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("workshop", "destroy");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
