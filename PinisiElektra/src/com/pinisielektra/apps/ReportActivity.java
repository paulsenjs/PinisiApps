package com.pinisielektra.apps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class ReportActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean itemSelected = false;
		switch (item.getItemId()) {
		case R.id.action_more_menu:
			showActionBarPopUpMenu();
			itemSelected = true;
			break;
		case android.R.id.home:
			this.finish();
			itemSelected = true;
			break;	
		default:
			break;
		}
		return itemSelected;
	}
	
	private void showActionBarPopUpMenu() {

		View menuItemView = findViewById(R.id.action_more_menu);
		PopupMenu popupMenu = new PopupMenu(this, menuItemView);
		popupMenu.getMenuInflater().inflate(R.menu.dropdown,
				popupMenu.getMenu());
		
		popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			boolean itemSelected = false;
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				switch (item.getItemId()) {
				case R.id.action_pie_chart:
					startActivity(new Intent().setClass(ReportActivity.this, PieChartActivity.class));
					itemSelected = true;
					break;

				case R.id.action_about:
					itemSelected = true;
					break;

				case R.id.action_logout:
					itemSelected = true;
					break;
					
				default:
					break;
				}

				return itemSelected;
			}
		});

		popupMenu.show();
	}

}
