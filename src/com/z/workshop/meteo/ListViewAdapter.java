package com.z.workshop.meteo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.z.workshop.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListViewAdapter extends ArrayAdapter<JSONObject> {

	private Context _context;
	private ArrayList<JSONObject> _dataSource;

	public ListViewAdapter(Context context, int resource,
			int textViewResourceId, ArrayList<JSONObject> objects) {
		super(context, resource, textViewResourceId, objects);
		this._context = context;
		this._dataSource = objects;
	}

	@Override
	public View getView(int position, View recycledView, ViewGroup parent) {
		View view;
		LayoutInflater layoutInflater = LayoutInflater.from(this._context);

		if (recycledView == null) {
			view = layoutInflater.inflate(R.layout.meteo_list_view_item, null);
		} else {
			view = recycledView;
		}

		TextView textView = (TextView)view.findViewById(R.id.meteo_element);
		try {
			textView.setText(this._dataSource.get(position).getString("text"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return view;
	}

}
