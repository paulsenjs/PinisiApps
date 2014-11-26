package com.pinisielektra.apps;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.pinisielektra.apps.connection.HttpConnectionTask;
import com.pinisielektra.apps.connection.IHttpResponseListener;
import com.pinisielektra.apps.object.InventoryObj;
import com.pinisielektra.apps.utils.Constants;

public class ReportActivity extends ActionBarActivity implements IHttpResponseListener{

	private String menuIntent;
	private TableLayout tableData;
	private InventoryObj inventoryObj;
	private ArrayList<InventoryObj> arrInventory;
	private ArrayList<InventoryObj> collectArrInventory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		
		menuIntent = getIntent().getExtras().getString("menu");
		tableData = (TableLayout) findViewById(R.id.tableData);
		
		collectArrInventory = new ArrayList<InventoryObj>();
		
		if (menuIntent != null) {
			if (menuIntent.equalsIgnoreCase("menu_pembelian")){
				
			}else if (menuIntent.equalsIgnoreCase("menu_penjualan")) {

			}else if (menuIntent.equalsIgnoreCase("menu_pelanggan")) {

			}else if (menuIntent.equalsIgnoreCase("menu_distributor")) {

			}else if (menuIntent.equalsIgnoreCase("menu_inventory")) {
				new HttpConnectionTask(this, this, 0, "GET").execute(Constants.API_LIST_INVENTORY);
			}
		}
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private void initTableData(int arrSize){
		tableData.setStretchAllColumns(true);
		tableData.bringToFront();
	    for(int i = 0; i < arrSize; i++){
	        TableRow tr =  new TableRow(this);
	        TextView c1 = new TextView(this);
	        c1.setText(collectArrInventory.get(i).getNamaBarang());
	        
	        TextView c2 = new TextView(this);
	        c2.setText(collectArrInventory.get(i).getSatuan());
	        
	        TextView c3 = new TextView(this);
	        c3.setText(collectArrInventory.get(i).getHargaBeli());

	        TextView c4 = new TextView(this);
	        c4.setText(collectArrInventory.get(i).getHargaJual());
	        
	        tr.addView(c1);
	        tr.addView(c2);
	        tr.addView(c3);
	        tr.addView(c4);
	        tableData.addView(tr);
	    }
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

	@Override
	public void resultSuccess(int type, String result) {
		try {
			JSONObject jObj = new JSONObject(result);
			if (jObj.optString("result").equalsIgnoreCase("1")) {
				Toast.makeText(this, "Welcome ...", Toast.LENGTH_SHORT).show();
				JSONArray jArray = jObj.getJSONArray("rows");
				arrInventory = new ArrayList<InventoryObj>();
				for (int i=0; i<jArray.length(); i++) {
					JSONObject jObjArr = jArray.getJSONObject(i);
					inventoryObj = new InventoryObj();
					inventoryObj.setNamaBarang(jObjArr.optString("namabarang"));
					inventoryObj.setSatuan(jObjArr.optString("satuan"));
					inventoryObj.setHargaBeli(jObjArr.optString("harga_beli"));
					inventoryObj.setHargaJual(jObjArr.optString("harga_jual"));
					
					arrInventory.add(i, inventoryObj);
				}
				collectArrInventory.addAll(arrInventory);
				
				initTableData(collectArrInventory.size());
			}else {
				Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resultFailed(int type, String strError) {
		// TODO Auto-generated method stub
		
	}

}
