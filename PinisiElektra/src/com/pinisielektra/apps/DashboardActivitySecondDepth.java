package com.pinisielektra.apps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pinisielektra.apps.object.MenuObj;
import com.pinisielektra.apps.utils.JsonObjConstant;

public class DashboardActivitySecondDepth extends MenuObj implements JsonObjConstant{

	private String menuIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard_second_depth);
		menuIntent = getIntent().getExtras().getString("menu");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		if (menuIntent.equalsIgnoreCase("menu_pembelian")){
			getActionBar().setTitle("Pembelian");
		}else if (menuIntent.equalsIgnoreCase("menu_penjualan")) {
			getActionBar().setTitle("Penjualan");
		}else if (menuIntent.equalsIgnoreCase("menu_pelanggan")) {
			getActionBar().setTitle("Pelanggan");
		}else if (menuIntent.equalsIgnoreCase("menu_distributor")) {
			getActionBar().setTitle("Distributor");
		}else if (menuIntent.equalsIgnoreCase("menu_inventory")) {
			getActionBar().setTitle("Inventory");
		}
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
