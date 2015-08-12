package com.pinisielektra.apps;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.pinisielektra.apps.adapter.SpinnerAdapter;
import com.pinisielektra.apps.object.MenuObj;
import com.pinisielektra.apps.utils.Constants;

public class FilteringActivity extends MenuObj implements OnClickListener, OnItemSelectedListener {

	private EditText edtFromDate;
	private EditText edtToDate;
	private int lYear;
	private int lMonth;
	private int lDay;
	private static final int FROM_DATE_DIALOG_ID = 990;
	private static final int TO_DATE_DIALOG_ID = 999;
	private Button btnSearch;
	private String menuIntent;
	private Spinner spnKodeMerchantPenjualan;
	private TextView txtKodeMerchantPenjualan;
	private SpinnerAdapter spinnerAdapterKodeMerchant;
	private List<String> lstKodeMerchant;
	private Set<String> setKodeMerchant;
	private String selectedKodeMerchant;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filtering);
		
		Calendar c = Calendar.getInstance();
		lYear = c.get(Calendar.YEAR);
		lMonth = c.get(Calendar.MONTH);
		lDay = c.get(Calendar.DAY_OF_MONTH);
		
		menuIntent = getIntent().getExtras().getString("menu");
		btnSearch = (Button) findViewById(R.id.btnSearch);

		SharedPreferences prefsKodeMerchant = getSharedPreferences(Constants.PREF_KODE_MERCHANT, MODE_PRIVATE);
		setKodeMerchant = prefsKodeMerchant.getStringSet("kodemerch", null);
		lstKodeMerchant = new ArrayList<String>(setKodeMerchant);
		
		Log.d("####-list", ""+lstKodeMerchant);
		Log.d("####-set", ""+setKodeMerchant);
		
		edtFromDate = (EditText) findViewById(R.id.edtFrom);
		edtToDate = (EditText) findViewById(R.id.edtTo);
		
		edtFromDate.setOnClickListener(this);
		edtToDate.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		
		if ((menuIntent.equalsIgnoreCase("menu_penjualan"))) {
			spnKodeMerchantPenjualan = (Spinner) findViewById(R.id.edtKodeMerchantPenjualan);
			spnKodeMerchantPenjualan.setVisibility(View.VISIBLE);
		
			txtKodeMerchantPenjualan = (TextView) findViewById(R.id.txtKodeMerchantPenjualan);
			txtKodeMerchantPenjualan.setVisibility(View.VISIBLE);
		
			spinnerAdapterKodeMerchant = new SpinnerAdapter(this, 0, lstKodeMerchant);
			spnKodeMerchantPenjualan.setAdapter(spinnerAdapterKodeMerchant);
			spnKodeMerchantPenjualan.setOnItemSelectedListener(this);
		}
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Filter Search");
//		if (menuIntent.equalsIgnoreCase("menu_pembelian")){
//			getActionBar().setTitle("FilteringPembelian");
//		}else if (menuIntent.equalsIgnoreCase("menu_penjualan")) {
//			getActionBar().setTitle("Penjualan");
//		}else if (menuIntent.equalsIgnoreCase("menu_pelanggan")) {
//			getActionBar().setTitle("Pelanggan");
//		}else if (menuIntent.equalsIgnoreCase("menu_distributor")) {
//			getActionBar().setTitle("Distributor");
//		}else if (menuIntent.equalsIgnoreCase("menu_inventory")) {
//			getActionBar().setTitle("Inventory");
//		}
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
			if (menuIntent != null) {
				if (menuIntent.equalsIgnoreCase("menu_pembelian")){
					startActivity(new Intent().setClass(this, ReportActivity.class).putExtra("menu", "menu_pembelian").putExtra("startdate", edtFromDate.getText().toString()).putExtra("enddate", edtToDate.getText().toString()));
				}else if (menuIntent.equalsIgnoreCase("menu_penjualan") && getSelectedNameMerchant() != null) {
					startActivity(new Intent().setClass(this, ReportActivity.class).putExtra("menu", "menu_penjualan")
							.putExtra("startdate", edtFromDate.getText().toString())
							.putExtra("enddate", edtToDate.getText().toString())
							.putExtra("merchantid", getSelectedNameMerchant()));
				}else if (menuIntent.equalsIgnoreCase("menu_pelanggan")) {
					startActivity(new Intent().setClass(this, ReportActivity.class).putExtra("menu", "menu_pelanggan"));
				}else if (menuIntent.equalsIgnoreCase("menu_distributor")) {
					startActivity(new Intent().setClass(this, ReportActivity.class).putExtra("menu", "menu_distributor"));
				}else if (menuIntent.equalsIgnoreCase("menu_inventory")) {
					startActivity(new Intent().setClass(this, ReportActivity.class).putExtra("menu", "menu_inventory"));
				}else if (menuIntent.equalsIgnoreCase("menu_merchant")) {
					startActivity(new Intent().setClass(this, ReportActivity.class).putExtra("menu", "menu_merchant"));
				}
			}

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
			
			edtFromDate.setText(new StringBuilder()
					.append(lYear)
					.append("-")
					.append(lMonth+1)
					.append("-")
					.append(lDay));
		}
	};

	private DatePickerDialog.OnDateSetListener toDatePickerListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			lYear = year;
			lMonth = monthOfYear;
			lDay = dayOfMonth;
			
			edtToDate.setText(new StringBuilder()
					.append(lYear)
					.append("-")
					.append(lMonth+1)
					.append("-")
					.append(lDay));
		}
	};

	private String getSelectedNameMerchant() {
		return selectedKodeMerchant;
	}

	private void setSelectedNameMerchant(String selectedKodeMerchant) {
		this.selectedKodeMerchant = selectedKodeMerchant;
	}
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		switch (arg0.getId()) {
		case R.id.edtKodeMerchantPenjualan:
			setSelectedNameMerchant(spinnerAdapterKodeMerchant.getItem(arg2));
			break;
		}
	}	


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
