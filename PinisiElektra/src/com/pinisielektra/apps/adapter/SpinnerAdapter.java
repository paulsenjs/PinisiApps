package com.pinisielektra.apps.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<String> {

	private Context ctx;
	private List<String> objects;
	
	public SpinnerAdapter(Context context, int resource, List<String> lstKodeBarang) {
		super(context, resource, lstKodeBarang);
		this.ctx = context;
		this.objects = lstKodeBarang;
	}
	
	@Override
	public int getCount() {
		return objects.size();
	}
	
	public Object getIndexOf(Object obj) {
		return objects.indexOf(obj);
	}
	
	@Override
	public String getItem(int position) {
		return objects.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView label = new TextView(ctx);
        label.setTextColor(Color.BLACK);
        label.setText(objects.get(position));
        return label;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView label = new TextView(ctx);
        label.setTextColor(Color.BLACK);
        label.setText(getItem(position));
        return label;
	}
}
