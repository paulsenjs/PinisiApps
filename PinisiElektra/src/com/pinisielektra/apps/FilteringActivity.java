package com.pinisielektra.apps;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;

public class FilteringActivity extends Activity implements OnClickListener {

	private EditText edtFromDate;
	private EditText edtToDate;
	private int lYear;
	private int lMonth;
	private int lDay;
	static final int FROM_DATE_DIALOG_ID = 990;
	static final int TO_DATE_DIALOG_ID = 999;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filtering);
		
		edtFromDate = (EditText) findViewById(R.id.edtFrom);
		edtToDate = (EditText) findViewById(R.id.edtTo);
		
		edtFromDate.setOnClickListener(this);
		edtToDate.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filtering, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edtFrom:
			showDialog(FROM_DATE_DIALOG_ID);
			break;
		case R.id.edtTo:
			showDialog(TO_DATE_DIALOG_ID);
			break;

		default:
			break;
		}
		
	}
	
	
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case FROM_DATE_DIALOG_ID:
			return new DatePickerDialog(this, fromDatePickerListener, lYear, lMonth, lDay);
		case TO_DATE_DIALOG_ID:
			return new DatePickerDialog(this, toDatePickerListener, lYear, lMonth, lDay);
		}
		return null;
	}
	
	private DatePickerDialog.OnDateSetListener fromDatePickerListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			lYear = year;
			lMonth = monthOfYear;
			lDay = dayOfMonth;
			
			edtFromDate.setText(new StringBuilder().append(lMonth + 1)
					   .append("-").append(lDay).append("-").append(lYear)
					   .append(" "));
		}
	};

	private DatePickerDialog.OnDateSetListener toDatePickerListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			lYear = year;
			lMonth = monthOfYear;
			lDay = dayOfMonth;
			
			edtToDate.setText(new StringBuilder().append(lMonth + 1)
					   .append("-").append(lDay).append("-").append(lYear)
					   .append(" "));
		}
	};
}
