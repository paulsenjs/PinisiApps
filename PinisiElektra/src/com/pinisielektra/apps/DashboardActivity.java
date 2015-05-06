package com.pinisielektra.apps;

import com.pinisielektra.apps.utils.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class DashboardActivity extends ActionBarActivity {
	private String menuIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		menuIntent = getIntent().getExtras().getString("menu");
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
		popupMenu.getMenuInflater().inflate(R.menu.dropdown, popupMenu.getMenu());

		if (menuIntent.equalsIgnoreCase("dashboard")) {
			popupMenu.getMenu().findItem(R.id.action_filtering).setVisible(false);
			popupMenu.getMenu().findItem(R.id.action_pie_chart).setVisible(false);
		}
		
		popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			boolean itemSelected = false;

			@Override
			public boolean onMenuItemClick(MenuItem item) {

				switch (item.getItemId()) {
				case R.id.action_about:
					itemSelected = true;
					break;

				case R.id.action_logout:
					itemSelected = true;
					getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE).edit().clear().commit();
					startActivity(new Intent().setClass(DashboardActivity.this, MainActivity.class));
					DashboardActivity.this.finish();
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
