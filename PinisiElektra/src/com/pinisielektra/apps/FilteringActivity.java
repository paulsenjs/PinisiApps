package com.pinisielektra.apps;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class FilteringActivity extends ActionBarActivity implements OnClickListener {

	private EditText edtFromDate;
	private EditText edtToDate;
	private int lYear;
	private int lMonth;
	private int lDay;
	private static final int FROM_DATE_DIALOG_ID = 990;
	private static final int TO_DATE_DIALOG_ID = 999;
	private Button btnSearch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filtering);
		
		btnSearch = (Button) findViewById(R.id.btnSearch);
		
		edtFromDate = (EditText) findViewById(R.id.edtFrom);
		edtToDate = (EditText) findViewById(R.id.edtTo);
		
		edtFromDate.setOnClickListener(this);
		edtToDate.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.filtering, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean itemSelected = false;
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			itemSelected = true;
			break;
		default:
			break;
		}
		return itemSelected;
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
		case R.id.btnSearch:
			startActivity(new Intent().setClass(this, ReportActivity.class));
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
