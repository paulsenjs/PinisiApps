package com.pinisielektra.apps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DashboardActivitySecondDepth extends Activity {

	private Button btnInputData;
	private Button btnReport;
	private String menuIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard_second_depth);
	
		menuIntent = getIntent().getExtras().getString("menu"); 
		
		btnInputData = (Button) findViewById(R.id.btnInput);
		btnReport = (Button) findViewById(R.id.btnReport);
	}

	public void goToFormInput(View v) {
		if (menuIntent != null) {
			if (menuIntent.equalsIgnoreCase("menu_pembelian")){
				startActivity(new Intent().setClass(DashboardActivitySecondDepth.this, InputActivity.class).putExtra("menu", "menu_pembelian"));		
			}else if (menuIntent.equalsIgnoreCase("menu_penjualan")) {
				startActivity(new Intent().setClass(DashboardActivitySecondDepth.this, InputActivity.class).putExtra("menu", "menu_penjualan"));
			}else if (menuIntent.equalsIgnoreCase("menu_pelanggan")) {
				startActivity(new Intent().setClass(DashboardActivitySecondDepth.this, InputActivity.class).putExtra("menu", "menu_pelanggan"));
			}else if (menuIntent.equalsIgnoreCase("menu_distributor")) {
				startActivity(new Intent().setClass(DashboardActivitySecondDepth.this, InputActivity.class).putExtra("menu", "menu_distributor"));
			}else if (menuIntent.equalsIgnoreCase("menu_inventory")) {
				startActivity(new Intent().setClass(DashboardActivitySecondDepth.this, InputActivity.class).putExtra("menu", "menu_inventory"));
			}
		}
	}
	
	public void goToFormReport(View v) {
		if (menuIntent != null) {
			if (menuIntent.equalsIgnoreCase("menu_pembelian")){
				startActivity(new Intent().setClass(DashboardActivitySecondDepth.this, FilteringActivity.class).putExtra("menu", "menu_pembelian"));		
			}else if (menuIntent.equalsIgnoreCase("menu_penjualan")) {
				startActivity(new Intent().setClass(DashboardActivitySecondDepth.this, FilteringActivity.class).putExtra("menu", "menu_penjualan"));
			}else if (menuIntent.equalsIgnoreCase("menu_pelanggan")) {
				startActivity(new Intent().setClass(DashboardActivitySecondDepth.this, FilteringActivity.class).putExtra("menu", "menu_pelanggan"));
			}else if (menuIntent.equalsIgnoreCase("menu_distributor")) {
				startActivity(new Intent().setClass(DashboardActivitySecondDepth.this, FilteringActivity.class).putExtra("menu", "menu_distributor"));
			}else if (menuIntent.equalsIgnoreCase("menu_inventory")) {
				startActivity(new Intent().setClass(DashboardActivitySecondDepth.this, FilteringActivity.class).putExtra("menu", "menu_inventory"));
			}
		}
	}
}
