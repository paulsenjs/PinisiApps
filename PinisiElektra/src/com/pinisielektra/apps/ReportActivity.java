package com.pinisielektra.apps;

import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

import com.pinisielektra.apps.adapter.ReportAdapter;
import com.pinisielektra.apps.connection.HttpConnectionTask;
import com.pinisielektra.apps.connection.IHttpResponseListener;
import com.pinisielektra.apps.object.InventoryObj;
import com.pinisielektra.apps.object.PembelianObj;
import com.pinisielektra.apps.utils.Constants;
import com.pinisielektra.apps.utils.JsonObjConstant;

public class ReportActivity extends ActionBarActivity implements IHttpResponseListener, JsonObjConstant, OnItemClickListener {

	private String menuIntent;
	private String passMenuIntent;
	private String totalSatuan;
	
	private LinearLayout linearHeaderPembelian;
	private LinearLayout linearHeaderInventory;
	
	private ProgressDialog mProgress;
	private ListView lstDataReport;
	private ReportAdapter reportAdapter;

	private PembelianObj pembelianObj;
	private InventoryObj inventoryObj;

	private ArrayList<Object> arrObj;

	private Hashtable<String, String> hashPost;

	private static final int ID_PENJUALAN = 1;
	private static final int ID_PEMBELIAN = 2;
	private static final int ID_INVENTORY = 3;
	private static final int ID_PELANGGAN = 4;
	private static final int ID_DISTRIBUTOR = 5;
	
	private String[] strPassingData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);

		menuIntent = getIntent().getExtras().getString("menu");
		lstDataReport = (ListView) findViewById(R.id.lstListData);

		linearHeaderPembelian = (LinearLayout) findViewById(R.id.linearPembelianHeader);
		linearHeaderInventory = (LinearLayout) findViewById(R.id.linearInventoryHeader);
		
		mProgress = new ProgressDialog(this);
		mProgress.show();
		
		if (menuIntent != null) {
			if (menuIntent.equalsIgnoreCase("menu_pembelian")) {
				new HttpConnectionTask(this, this, 0, "GET").execute(Constants.API_LIST_PEMBELIAN);
			} else if (menuIntent.equalsIgnoreCase("menu_penjualan")) {

			} else if (menuIntent.equalsIgnoreCase("menu_pelanggan")) {

			} else if (menuIntent.equalsIgnoreCase("menu_distributor")) {

			} else if (menuIntent.equalsIgnoreCase("menu_inventory")) {
				new HttpConnectionTask(this, this, 0, "GET").execute(Constants.API_LIST_INVENTORY);
			}
		}

		setPassMenuIntent(menuIntent);
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
		popupMenu.getMenuInflater().inflate(R.menu.dropdown, popupMenu.getMenu());

		popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			boolean itemSelected = false;

			@Override
			public boolean onMenuItemClick(MenuItem item) {

				switch (item.getItemId()) {
				case R.id.action_pie_chart:
					Intent intent = new Intent(ReportActivity.this, PieChartActivity.class);					
					
					if (menuIntent.equalsIgnoreCase("menu_pembelian")) {
						intent.putExtra("id_menu", ID_PEMBELIAN);
					} else if (menuIntent.equalsIgnoreCase("menu_penjualan")) {
						intent.putExtra("id_menu", ID_PENJUALAN);
					} else if (menuIntent.equalsIgnoreCase("menu_pelanggan")) {
						intent.putExtra("id_menu", ID_PELANGGAN);
					} else if (menuIntent.equalsIgnoreCase("menu_distributor")) {
						intent.putExtra("id_menu", ID_DISTRIBUTOR);
					} else if (menuIntent.equalsIgnoreCase("menu_inventory")) {
						intent.putExtra("id_menu", ID_INVENTORY);
					}
					
					intent.putExtra("pie_chart_data", getArrObj());
					intent.putExtra("total_satuan", getTotalSatuan());
					startActivity(intent);
					
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

	private void processPembelianResult(JSONArray jArray) {
		linearHeaderPembelian.setVisibility(View.VISIBLE);
		try {
			arrObj = new ArrayList<Object>();
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jObjArr = jArray.getJSONObject(i);
				pembelianObj = new PembelianObj();
				pembelianObj.setIdTrans(jObjArr.optString(OBJ_ID_TRANS));
				pembelianObj.setKodeBarang(jObjArr.optString(OBJ_KODE_BARANG));
				pembelianObj.setTglTransaksi(jObjArr.optString(OBJ_TGL_TRANS));
				pembelianObj.setSatuan(jObjArr.optString(OBJ_SATUAN_BARANG));
				pembelianObj.setKodeDistributor(jObjArr.optString(OBJ_KODE_DISTRIBUTOR));
				pembelianObj.setCreator(jObjArr.optString(OBJ_CREATOR));
				pembelianObj.setDateCreated(jObjArr.optString(OBJ_DATE_CREATED));
				pembelianObj.setEditor(jObjArr.optString(OBJ_EDITOR));
				pembelianObj.setDateEdited(jObjArr.optString(OBJ_DATE_EDITED));
				
				arrObj.add(pembelianObj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		setArrObj(arrObj);
		reportAdapter = new ReportAdapter(ID_PEMBELIAN, this, arrObj);
		lstDataReport.setAdapter(reportAdapter);
		lstDataReport.setOnItemClickListener(this);
	}

	private void processInventoryResult(JSONArray jArray) {
		linearHeaderInventory.setVisibility(View.VISIBLE);
		try {
			arrObj = new ArrayList<Object>();
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jObjArr = jArray.getJSONObject(i);
				inventoryObj = new InventoryObj();
				inventoryObj.setKodeBarang(jObjArr.optString(OBJ_KODE_BARANG));
				inventoryObj.setCatId(jObjArr.optString(OBJ_CATEGORY_ID));
				inventoryObj.setNamaBarang(jObjArr.optString(OBJ_NAMA_BARANG));
				inventoryObj.setSatuan(jObjArr.optString(OBJ_SATUAN_BARANG));
				inventoryObj.setHargaJual(jObjArr.optString(OBJ_HARGA_JUAL));
				inventoryObj.setHargaBeli(jObjArr.optString(OBJ_HARGA_BELI));
				inventoryObj.setExpDate(jObjArr.optString(OBJ_EXP_DATE));
				inventoryObj.setCreator(jObjArr.optString(OBJ_CREATOR));
				inventoryObj.setDateCreated(jObjArr.optString(OBJ_DATE_CREATED));
				inventoryObj.setDateEdited(jObjArr.optString(OBJ_DATE_EDITED));
				
				arrObj.add(i, inventoryObj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		setArrObj(arrObj);
		reportAdapter = new ReportAdapter(ID_INVENTORY, this, arrObj);
		lstDataReport.setAdapter(reportAdapter);
		lstDataReport.setOnItemClickListener(this);
	}

	@Override
	public void resultSuccess(int type, String result) {
		try {
			JSONObject jObj = new JSONObject(result);
			if (jObj.optString("result").equalsIgnoreCase("1")) {
				if (type == 0) {
					JSONArray jArray = jObj.getJSONArray("rows");
					setTotalSatuan(jObj.optString(OBJ_TOTAL_SATUAN));
					
					if (menuIntent.equalsIgnoreCase("menu_pembelian")) {
						processPembelianResult(jArray);
					} else if (menuIntent.equalsIgnoreCase("menu_penjualan")) {

					} else if (menuIntent.equalsIgnoreCase("menu_pelanggan")) {

					} else if (menuIntent.equalsIgnoreCase("menu_distributor")) {

					} else if (menuIntent.equalsIgnoreCase("menu_inventory")) {
						processInventoryResult(jArray);
					}					
				}else {
					Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
					finish();
				}
			} else {
				Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
			}

			if (mProgress != null)
				mProgress.dismiss();

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resultFailed(int type, String strError) {
		if (mProgress != null)
			mProgress.dismiss();

		Toast.makeText(this, strError, Toast.LENGTH_SHORT).show();
	}

	private void showChoicePopUp(final int position) {
		final CharSequence[] items = { "Ubah", "Hapus" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setItems(items, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int item) {
				switch (item) {
				case 0:

					if (getPassMenuIntent().equalsIgnoreCase("menu_pembelian")) {
						strPassingData = new String[]{"edit_pembelian",
								((PembelianObj) arrObj.get(position)).getKodeBarang(),
								((PembelianObj) arrObj.get(position)).getSatuan(),
								((PembelianObj) arrObj.get(position)).getKodeDistributor(),
								((PembelianObj) arrObj.get(position)).getIdTrans()};
					} else if (getPassMenuIntent().equalsIgnoreCase("menu_penjualan")) {

					} else if (getPassMenuIntent().equalsIgnoreCase("menu_pelanggan")) {

					} else if (getPassMenuIntent().equalsIgnoreCase("menu_distributor")) {

					} else if (getPassMenuIntent().equalsIgnoreCase("menu_inventory")) {
						strPassingData = new String[]{"edit_inventory",
								((InventoryObj) arrObj.get(position)).getKodeBarang(),
								((InventoryObj) arrObj.get(position)).getNamaBarang(),
								((InventoryObj) arrObj.get(position)).getCatId(),
								((InventoryObj) arrObj.get(position)).getSatuan(),
								((InventoryObj) arrObj.get(position)).getHargaJual(),
								((InventoryObj) arrObj.get(position)).getHargaBeli(),
								((InventoryObj) arrObj.get(position)).getExpDate()};
					}
					startActivity(new Intent().setClass(ReportActivity.this, EditInputActivity.class).putExtra("edit", strPassingData));					
					break;
				case 1:
					if (getPassMenuIntent().equalsIgnoreCase("menu_pembelian")) {
						hashPost = new Hashtable<String, String>();
						hashPost.put("cmd", "del");
						hashPost.put(OBJ_ID_TRANS, ((PembelianObj) arrObj.get(position)).getIdTrans());
						new HttpConnectionTask(hashPost, ReportActivity.this, 1).execute(Constants.API_POST_PEMBELIAN);
					} else if (getPassMenuIntent().equalsIgnoreCase("menu_penjualan")) {

					} else if (getPassMenuIntent().equalsIgnoreCase("menu_pelanggan")) {

					} else if (getPassMenuIntent().equalsIgnoreCase("menu_distributor")) {

					} else if (getPassMenuIntent().equalsIgnoreCase("menu_inventory")) {
						hashPost = new Hashtable<String, String>();
						hashPost.put("cmd", "del");
						hashPost.put(OBJ_KODE_BARANG, ((InventoryObj) arrObj.get(position)).getKodeBarang());
						new HttpConnectionTask(hashPost, ReportActivity.this, 1).execute(Constants.API_POST_INVENTORY);
					}
					break;
				default:
					break;
				}
				Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
			}

		});

		AlertDialog alert = builder.create();

		alert.show();
	}

	private String getPassMenuIntent() {
		return passMenuIntent;
	}

	private void setPassMenuIntent(String passMenuIntent) {
		this.passMenuIntent = passMenuIntent;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		showChoicePopUp(pos);
	}
	
	private String getTotalSatuan() {
		return totalSatuan;
	}

	private void setTotalSatuan(String totalSatuan) {
		this.totalSatuan = totalSatuan;
	}
	
	private ArrayList<Object> getArrObj() {
		return arrObj;
	}

	private void setArrObj(ArrayList<Object> arrObj) {
		this.arrObj = arrObj;
	}
}
