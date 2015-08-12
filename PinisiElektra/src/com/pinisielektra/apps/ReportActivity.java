package com.pinisielektra.apps;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.color;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.pinisielektra.apps.adapter.ReportAdapter;
import com.pinisielektra.apps.connection.HttpConnectionTask;
import com.pinisielektra.apps.connection.IHttpResponseListener;
import com.pinisielektra.apps.object.DistributorObj;
import com.pinisielektra.apps.object.InventoryObj;
import com.pinisielektra.apps.object.MenuObj;
import com.pinisielektra.apps.object.MerchantObj;
import com.pinisielektra.apps.object.PelangganObj;
import com.pinisielektra.apps.object.PembelianObj;
import com.pinisielektra.apps.object.PenjualanObj;
import com.pinisielektra.apps.utils.Constants;
import com.pinisielektra.apps.utils.JsonObjConstant;

public class ReportActivity extends MenuObj implements IHttpResponseListener, JsonObjConstant, OnItemClickListener {

	private String menuIntent;
	private String passMenuIntent;
	private String totalSatuan;
	
	private LinearLayout linearHeaderPenjualan;
	private LinearLayout linearHeaderPembelian;
	private LinearLayout linearHeaderInventory;
	private LinearLayout linearHeaderPelanggan;
	private LinearLayout linearHeaderDistributor;
	private LinearLayout linearHeaderMerchant;
	
	private ProgressDialog mProgress;
	private ListView lstDataReport;
	private ReportAdapter reportAdapter;

	private PenjualanObj penjualanObj;
	private PembelianObj pembelianObj;
	private InventoryObj inventoryObj;
	private PelangganObj pelangganObj;
	private MerchantObj merchantObj;
	private DistributorObj distributorObj;
	private String startDate;
	private String endDate;
	private String merchantId;
	private String savedId;
	private ArrayList<Object> arrObj;
	private View footerView;
	private Hashtable<String, String> hashPost;
	private String[] strPassingData;
	private String roleId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);

		menuIntent = getIntent().getExtras().getString("menu");
		lstDataReport = (ListView) findViewById(R.id.lstListData);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		lstDataReport.getLayoutParams().height=2*metrics.heightPixels/3;
		
		linearHeaderPenjualan = (LinearLayout) findViewById(R.id.linearPenjualanHeader);
		linearHeaderPembelian = (LinearLayout) findViewById(R.id.linearPembelianHeader);
		linearHeaderInventory = (LinearLayout) findViewById(R.id.linearInventoryHeader);
		linearHeaderPelanggan = (LinearLayout) findViewById(R.id.linearPelangganHeader);
		linearHeaderDistributor = (LinearLayout) findViewById(R.id.linearDistributorHeader);
		linearHeaderMerchant = (LinearLayout) findViewById(R.id.linearMerchantHeader); 
		
		mProgress = new ProgressDialog(this);
		mProgress.setMessage("Load data ...");
		mProgress.setTitle("Loading");
		mProgress.show();
		
		SharedPreferences prefs = getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE);
		savedId = prefs.getString("uId", null);
		roleId = prefs.getString("uRole", null);
		
		init(menuIntent);
		
		footerView =  ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_footer, null, false);
		
		setPassMenuIntent(menuIntent);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private void init(String strPassingIntent) {
		if (strPassingIntent != null) {
			if (strPassingIntent.equalsIgnoreCase("menu_pembelian")) {
				getActionBar().setTitle("Laporan Pembelian");
				setMenuPembelian(true);
				/*startDate = getIntent().getExtras().getString("startdate").trim();
				endDate = getIntent().getExtras().getString("enddate").trim();
				if ((startDate.length()==0) || (endDate.length()==0)) {
					new HttpConnectionTask(this, this, 0, "GET").execute(Constants.API_LIST_PEMBELIAN+"&orderby=satuan%20desc");
				}else {
					new HttpConnectionTask(this, this, 0, "GET").execute(Constants.API_LIST_PEMBELIAN+"&startdate="+startDate+"&enddate="+endDate);					
				}*/
				new HttpConnectionTask(this, this, 0, "GET").execute(Constants.API_LIST_PEMBELIAN+"&creator="+savedId+"&orderby=satuan%20desc");
			} else if (strPassingIntent.equalsIgnoreCase("menu_penjualan")) {
				getActionBar().setTitle("Laporan Penjualan");
				setMenuPenjualan(true);
				if ((getIntent().getExtras().getString("startdate") != null) && (getIntent().getExtras().getString("enddate") != null)) {
					startDate = getIntent().getExtras().getString("startdate").trim();
					endDate = getIntent().getExtras().getString("enddate").trim();
					merchantId = getIntent().getExtras().getString("merchantid").trim();
					if ((startDate.length()!=0) || (endDate.length()!=0)) {
						new HttpConnectionTask(this, this, 0, "GET").execute(Constants.API_LIST_PENJUALAN+"&startdate="+startDate+"&enddate="+endDate+"&creator="+savedId);
					}else if (merchantId.length() != 0) {
						new HttpConnectionTask(this, this, 0, "GET").execute(Constants.API_LIST_PENJUALAN+"&startdate="+startDate+"&enddate="+endDate+"&merchantid="+merchantId+"&creator="+savedId);
					}	
				}else {
					new HttpConnectionTask(this, this, 0, "GET").execute(Constants.API_LIST_PENJUALAN+"&creator="+savedId+"&orderby=satuan%20desc");
				}
			} else if (strPassingIntent.equalsIgnoreCase("menu_pelanggan")) {
				getActionBar().setTitle("Laporan Pelanggan");
				setMenuPelanggan(true);
				new HttpConnectionTask(this, this, 0, "GET").execute(Constants.API_LIST_PELANGGAN+"&creator="+savedId);
			} else if (strPassingIntent.equalsIgnoreCase("menu_distributor")) {
				getActionBar().setTitle("Laporan Distributor");
				setMenuDistributor(true);
				new HttpConnectionTask(this, this, 0, "GET").execute(Constants.API_LIST_DISTRIBUTOR+"&creator="+savedId);
			} else if (strPassingIntent.equalsIgnoreCase("menu_inventory")) {
				getActionBar().setTitle("Laporan Inventory");
				setMenuInventory(true);
				new HttpConnectionTask(this, this, 0, "GET").execute(Constants.API_LIST_INVENTORY+"&creator="+savedId);
			} else if (strPassingIntent.equalsIgnoreCase("menu_merchant")) {
				getActionBar().setTitle("Laporan Merchant");
				setMenuMerchant(true);
				new HttpConnectionTask(this, this, 0, "GET").execute(Constants.API_LIST_MERCHANT+"&creator="+savedId);
			}
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

		if (menuIntent.equalsIgnoreCase("menu_pembelian") || (menuIntent.equalsIgnoreCase("menu_penjualan"))) {
			popupMenu.getMenu().findItem(R.id.action_filtering).setVisible(true);
			popupMenu.getMenu().findItem(R.id.action_pie_chart).setVisible(true);
			popupMenu.getMenu().findItem(R.id.action_logout).setVisible(false);
		}else{
			popupMenu.getMenu().findItem(R.id.action_filtering).setVisible(false);
			popupMenu.getMenu().findItem(R.id.action_pie_chart).setVisible(false);
		}
		
		popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			boolean itemSelected = false;

			@Override
			public boolean onMenuItemClick(MenuItem item) {

				switch (item.getItemId()) {
				
				case R.id.action_filtering:
					Intent filterIntent = new Intent(ReportActivity.this, FilteringActivity.class);					
					
//					if (menuIntent.equalsIgnoreCase("menu_pembelian")) {
//						filterIntent.putExtra("id_menu", ID_PEMBELIAN);
//					} else if (menuIntent.equalsIgnoreCase("menu_penjualan")) {
//						filterIntent.putExtra("id_menu", ID_PENJUALAN);
//					} else if (menuIntent.equalsIgnoreCase("menu_pelanggan")) {
//						filterIntent.putExtra("id_menu", ID_PELANGGAN);
//					} else if (menuIntent.equalsIgnoreCase("menu_distributor")) {
//						filterIntent.putExtra("id_menu", ID_DISTRIBUTOR);
//					} else if (menuIntent.equalsIgnoreCase("menu_inventory")) {
//						filterIntent.putExtra("id_menu", ID_INVENTORY);
//					}
//					
//					filterIntent.putExtra("pie_chart_data", getArrObj());
					filterIntent.putExtra("menu", menuIntent);
					startActivity(filterIntent);
					ReportActivity.this.finish();
					
					itemSelected = true;
					break;
				
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

//				case R.id.action_logout:
//					itemSelected = true;
//					break;

				default:
					break;
				}

				return itemSelected;
			}
		});

		popupMenu.show();
	}

	private void processMerchantResult(JSONArray jArray) {
		linearHeaderMerchant.setVisibility(View.VISIBLE);
		try {
			arrObj = new ArrayList<Object>();
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jObjArr = jArray.getJSONObject(i);
				merchantObj = new MerchantObj(this);
				merchantObj.setAddress(jObjArr.optString(OBJ_ADDRESS));
				merchantObj.setCreator(jObjArr.optString(OBJ_CREATOR));
				merchantObj.setDateCreated(jObjArr.optString(OBJ_DATE_CREATED));
				merchantObj.setMerchantId(jObjArr.optString(OBJ_MERCHANT_ID));
				merchantObj.setMerchantName(jObjArr.optString(OBJ_MERCHANT_NAME));
				arrObj.add(merchantObj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		setArrObj(arrObj);
		reportAdapter = new ReportAdapter(ID_MERCHANT, this, arrObj);
		lstDataReport.setAdapter(reportAdapter);
		for (int i = 0; i < reportAdapter.getCount(); i++) {
			if (i%2 != 0) {
				lstDataReport.setBackgroundColor(color.holo_blue_light);
			}
		}
		lstDataReport.setOnItemClickListener(this);
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
				pembelianObj.setNamaBarang(jObjArr.optString(OBJ_NAMA_BARANG));
				pembelianObj.setTglTransaksi(jObjArr.optString(OBJ_TGL_TRANS));
				pembelianObj.setSatuan(jObjArr.optString(OBJ_SATUAN_BARANG));
				pembelianObj.setKodeDistributor(jObjArr.optString(OBJ_KODE_DISTRIBUTOR));
				pembelianObj.setCreator(jObjArr.optString(OBJ_CREATOR));
				pembelianObj.setDateCreated(jObjArr.optString(OBJ_DATE_CREATED));
				pembelianObj.setEditor(jObjArr.optString(OBJ_EDITOR));
				pembelianObj.setDateEdited(jObjArr.optString(OBJ_DATE_EDITED));
				pembelianObj.setHargaPembelian(jObjArr.optString(OBJ_REPORT_HARGA));
				
				
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
				inventoryObj = new InventoryObj(this);
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

	private void processPenjualanResult(String total, JSONArray jArray) {
		linearHeaderPenjualan.setVisibility(View.VISIBLE);
		try {
			arrObj = new ArrayList<Object>();
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jObjArr = jArray.getJSONObject(i);
				penjualanObj = new PenjualanObj();
				penjualanObj.setIdJual(jObjArr.optString(OBJ_ID_PENJUALAN));
				penjualanObj.setKodeBarang(jObjArr.optString(OBJ_KODE_BARANG));
				penjualanObj.setNamaBarang(jObjArr.optString(OBJ_NAMA_BARANG));
				penjualanObj.setSatuan(jObjArr.optString(OBJ_SATUAN_BARANG));
				penjualanObj.setCreator(jObjArr.optString(OBJ_CREATOR));
				penjualanObj.setDateCreated(jObjArr.optString(OBJ_DATE_CREATED));
				penjualanObj.setEditor(jObjArr.optString(OBJ_EDITOR));
				penjualanObj.setDateCreated(jObjArr.optString(OBJ_DATE_CREATED));
				penjualanObj.setTglTransaksi(jObjArr.optString(OBJ_TGL_TRANS));
				penjualanObj.setHargaBarang(jObjArr.optString(OBJ_REPORT_HARGA));
				penjualanObj.setMerchantName(jObjArr.optString(OBJ_MERCHANT_NAME));
				
				arrObj.add(i, penjualanObj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		setArrObj(arrObj);
		reportAdapter = new ReportAdapter(ID_PENJUALAN, this, arrObj);
		lstDataReport.setAdapter(reportAdapter);
		lstDataReport.setOnItemClickListener(this);
		TextView txtTotal = (TextView) footerView.findViewById(R.id.txtTotal);
		DecimalFormat df = new DecimalFormat("###,###.###");
		txtTotal.setText("Rp "+df.format(Double.parseDouble(total)));
		lstDataReport.addFooterView(footerView);
	}
	
	private void processDistributorResult(JSONArray jArray) {
		linearHeaderDistributor.setVisibility(View.VISIBLE);
		try {
			arrObj = new ArrayList<Object>();
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jObjArr = jArray.getJSONObject(i);
				distributorObj = new DistributorObj(this);
				distributorObj.setKodeDistributor(jObjArr.optString(OBJ_KODE_DISTRIBUTOR));
				distributorObj.setNama(jObjArr.optString(OBJ_NAMA));
				distributorObj.setCreator(jObjArr.optString(OBJ_CREATOR));
				distributorObj.setDateCreated(jObjArr.optString(OBJ_DATE_CREATED));
				distributorObj.setEditor(jObjArr.optString(OBJ_EDITOR));
				distributorObj.setDateCreated(jObjArr.optString(OBJ_DATE_CREATED));
				
				arrObj.add(i, distributorObj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		setArrObj(arrObj);
		reportAdapter = new ReportAdapter(ID_DISTRIBUTOR, this, arrObj);
		lstDataReport.setAdapter(reportAdapter);
		lstDataReport.setOnItemClickListener(this);
	}
	
	private void processPelangganResult(JSONArray jArray) {
		linearHeaderPelanggan.setVisibility(View.VISIBLE);
		try {
			arrObj = new ArrayList<Object>();
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jObjArr = jArray.getJSONObject(i);
				pelangganObj = new PelangganObj();
				pelangganObj.setIdPel(jObjArr.optString(OBJ_ID_PELANGGAN));
				pelangganObj.setNama(jObjArr.optString(OBJ_NAMA));
				pelangganObj.setAlamat(jObjArr.optString(OBJ_ALAMAT));
				pelangganObj.setPhone(jObjArr.optString(OBJ_PHONE));
				pelangganObj.setCreator(jObjArr.optString(OBJ_CREATOR));
				pelangganObj.setDateCreated(jObjArr.optString(OBJ_DATE_CREATED));
				
				arrObj.add(i, pelangganObj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		setArrObj(arrObj);
		reportAdapter = new ReportAdapter(ID_PELANGGAN, this, arrObj);
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
					
					if (isMenuPembelian()) {
						processPembelianResult(jArray);
					} else if (isMenuPenjualan()) {
						processPenjualanResult(jObj.optString("total_satuan"), jArray);
					} else if (isMenuPelanggan()) {
						processPelangganResult(jArray);
					} else if (isMenuDistributor()) {
						processDistributorResult(jArray);
					} else if (isMenuInventory()) {
						processInventoryResult(jArray);
					} else if (isMenuMerchant()) {
						processMerchantResult(jArray);
					}
				}else {
					Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
					finish();
				}
			}else if (jObj.optString("result").equalsIgnoreCase("0")) {
				Toast.makeText(this, "No Records", Toast.LENGTH_SHORT).show();
//				finish();		
			}else {
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
		final CharSequence[] items = { "Ubah", "Hapus", "Detail" };

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
						strPassingData = new String[]{"edit_penjualan",
								((PenjualanObj) arrObj.get(position)).getKodeBarang(),
								((PenjualanObj) arrObj.get(position)).getTglTransaksi(),
								((PenjualanObj) arrObj.get(position)).getSatuan(),
								((PenjualanObj) arrObj.get(position)).getIdJual()};
					} else if (getPassMenuIntent().equalsIgnoreCase("menu_pelanggan")) {
						strPassingData = new String[]{"edit_pelanggan",
								((PelangganObj) arrObj.get(position)).getNama(),
								((PelangganObj) arrObj.get(position)).getAlamat(),
								((PelangganObj) arrObj.get(position)).getPhone(),
								((PelangganObj) arrObj.get(position)).getIdPel()};
					} else if (getPassMenuIntent().equalsIgnoreCase("menu_distributor")) {
						strPassingData = new String[]{"edit_distributor",
								((DistributorObj) arrObj.get(position)).getKodeDistributor(),
								((DistributorObj) arrObj.get(position)).getNama()};
					} else if (getPassMenuIntent().equalsIgnoreCase("menu_inventory")) {
						strPassingData = new String[]{"edit_inventory",
								((InventoryObj) arrObj.get(position)).getKodeBarang(),
								((InventoryObj) arrObj.get(position)).getNamaBarang(),
								((InventoryObj) arrObj.get(position)).getCatId(),
								((InventoryObj) arrObj.get(position)).getSatuan(),
								((InventoryObj) arrObj.get(position)).getHargaJual(),
								((InventoryObj) arrObj.get(position)).getHargaBeli(),
								((InventoryObj) arrObj.get(position)).getExpDate()};
					} else if (getPassMenuIntent().equalsIgnoreCase("menu_merchant")) {
						strPassingData = new String[]{"edit_merchant",
								((MerchantObj) arrObj.get(position)).getMerchantId(),
								((MerchantObj) arrObj.get(position)).getMerchantName(),
								((MerchantObj) arrObj.get(position)).getAddress(),
								((MerchantObj) arrObj.get(position)).getMerchantUserId()};
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
						hashPost = new Hashtable<String, String>();
						hashPost.put("cmd", "del");
						hashPost.put(OBJ_ID_PENJUALAN, ((PenjualanObj) arrObj.get(position)).getIdJual());
						new HttpConnectionTask(hashPost, ReportActivity.this, 1).execute(Constants.API_POST_PENJUALAN);
					} else if (getPassMenuIntent().equalsIgnoreCase("menu_pelanggan")) {
						hashPost = new Hashtable<String, String>();
						hashPost.put("cmd", "del");
						hashPost.put(OBJ_ID_PELANGGAN, ((PelangganObj) arrObj.get(position)).getIdPel());
						new HttpConnectionTask(hashPost, ReportActivity.this, 1).execute(Constants.API_POST_PELANGGAN);
					} else if (getPassMenuIntent().equalsIgnoreCase("menu_distributor")) {
						hashPost = new Hashtable<String, String>();
						hashPost.put("cmd", "del");
						hashPost.put(OBJ_KODE_DISTRIBUTOR, ((DistributorObj) arrObj.get(position)).getKodeDistributor());
						new HttpConnectionTask(hashPost, ReportActivity.this, 1).execute(Constants.API_POST_DISTRIBUTOR);
					} else if (getPassMenuIntent().equalsIgnoreCase("menu_inventory")) {
						hashPost = new Hashtable<String, String>();
						hashPost.put("cmd", "del");
						hashPost.put(OBJ_KODE_BARANG, ((InventoryObj) arrObj.get(position)).getKodeBarang());
						new HttpConnectionTask(hashPost, ReportActivity.this, 1).execute(Constants.API_POST_INVENTORY);
					} else if (getPassMenuIntent().equalsIgnoreCase("menu_merchant")) {
						hashPost = new Hashtable<String, String>();
						hashPost.put("cmd", "del");
						hashPost.put(OBJ_MERCHANT_ID, ((MerchantObj) arrObj.get(position)).getMerchantId());
						new HttpConnectionTask(hashPost, ReportActivity.this, 1).execute(Constants.API_POST_MERCHANT);
					}
					break;
				case 2:
					if (getPassMenuIntent().equalsIgnoreCase("menu_pembelian")) {
						strPassingData = new String[]{"detail_pembelian",
								((PembelianObj) arrObj.get(position)).getIdTrans(),
								((PembelianObj) arrObj.get(position)).getKodeDistributor(),
								((PembelianObj) arrObj.get(position)).getKodeBarang(),
								((PembelianObj) arrObj.get(position)).getTglTransaksi(),
								((PembelianObj) arrObj.get(position)).getSatuan(),
								((PembelianObj) arrObj.get(position)).getCreator(),
								((PembelianObj) arrObj.get(position)).getDateCreated(),
								((PembelianObj) arrObj.get(position)).getEditor(),
								((PembelianObj) arrObj.get(position)).getDateEdited()};
					} else if (getPassMenuIntent().equalsIgnoreCase("menu_penjualan")) {
						strPassingData = new String[]{"detail_penjualan",
								((PenjualanObj) arrObj.get(position)).getIdJual(),
								((PenjualanObj) arrObj.get(position)).getKodeBarang(),
								((PenjualanObj) arrObj.get(position)).getTglTransaksi(),
								((PenjualanObj) arrObj.get(position)).getSatuan(),
								((PenjualanObj) arrObj.get(position)).getCreator(),
								((PenjualanObj) arrObj.get(position)).getDateCreated(),
								((PenjualanObj) arrObj.get(position)).getEditor(),
								((PenjualanObj) arrObj.get(position)).getDateEdited()};
					} else if (getPassMenuIntent().equalsIgnoreCase("menu_pelanggan")) {
						strPassingData = new String[]{"detail_pelanggan",
								((PelangganObj) arrObj.get(position)).getIdPel(),
								((PelangganObj) arrObj.get(position)).getNama(),
								((PelangganObj) arrObj.get(position)).getAlamat(),
								((PelangganObj) arrObj.get(position)).getPhone(),
								((PelangganObj) arrObj.get(position)).getCreator(),
								((PelangganObj) arrObj.get(position)).getDateCreated()};
					} else if (getPassMenuIntent().equalsIgnoreCase("menu_distributor")) {
						strPassingData = new String[]{"detail_distributor",
								((DistributorObj) arrObj.get(position)).getKodeDistributor(),
								((DistributorObj) arrObj.get(position)).getNama(),
								((DistributorObj) arrObj.get(position)).getCreator(),
								((DistributorObj) arrObj.get(position)).getDateCreated(),
								((DistributorObj) arrObj.get(position)).getEditor(),
								((DistributorObj) arrObj.get(position)).getDateEdited()};
					} else if (getPassMenuIntent().equalsIgnoreCase("menu_inventory")) {
						strPassingData = new String[]{"detail_inventory",
								((InventoryObj) arrObj.get(position)).getKodeBarang(),
								((InventoryObj) arrObj.get(position)).getCatId(),
								((InventoryObj) arrObj.get(position)).getNamaBarang(),
								((InventoryObj) arrObj.get(position)).getSatuan(),
								((InventoryObj) arrObj.get(position)).getHargaJual(),
								((InventoryObj) arrObj.get(position)).getHargaBeli(),
								((InventoryObj) arrObj.get(position)).getExpDate(),
								((InventoryObj) arrObj.get(position)).getCreator(),
								((InventoryObj) arrObj.get(position)).getDateCreated(),
								((InventoryObj) arrObj.get(position)).getEditor(),
								((InventoryObj) arrObj.get(position)).getDateEdited()};
					} else if (getPassMenuIntent().equalsIgnoreCase("menu_merchant")) {
						strPassingData = new String[]{"detail_merchant",
								((MerchantObj) arrObj.get(position)).getMerchantId(),
								((MerchantObj) arrObj.get(position)).getMerchantName(),
								((MerchantObj) arrObj.get(position)).getAddress(),
								((MerchantObj) arrObj.get(position)).getMerchantUserId(),
								((MerchantObj) arrObj.get(position)).getCreator(),
								((MerchantObj) arrObj.get(position)).getDateCreated()};
					}
					startActivity(new Intent().setClass(ReportActivity.this, DetailActivity.class).putExtra("detail", strPassingData));
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
	
	@Override
	protected void onResume() {
		super.onResume();
		init(getPassMenuIntent());
//		Toast.makeText(getApplicationContext(), "resuming.. "+ getIntent().getExtras().getString("menu"), Toast.LENGTH_SHORT).show();
	}
	
	public void goToFormInput(View v) {
		if (roleId.equals("0")) {
			if (menuIntent != null) {
				if (menuIntent.equalsIgnoreCase("menu_pembelian")){
					startActivity(new Intent().setClass(ReportActivity.this, InputActivity.class).putExtra("menu", "menu_pembelian"));		
				}else if (menuIntent.equalsIgnoreCase("menu_penjualan")) {
					startActivity(new Intent().setClass(ReportActivity.this, InputActivity.class).putExtra("menu", "menu_penjualan"));
				}else if (menuIntent.equalsIgnoreCase("menu_pelanggan")) {
					startActivity(new Intent().setClass(ReportActivity.this, InputActivity.class).putExtra("menu", "menu_pelanggan"));
				}else if (menuIntent.equalsIgnoreCase("menu_distributor")) {
					startActivity(new Intent().setClass(ReportActivity.this, InputActivity.class).putExtra("menu", "menu_distributor"));
				}else if (menuIntent.equalsIgnoreCase("menu_inventory")) {
					startActivity(new Intent().setClass(ReportActivity.this, InputActivity.class).putExtra("menu", "menu_inventory"));
				}else if (menuIntent.equalsIgnoreCase("menu_merchant")) {
					startActivity(new Intent().setClass(ReportActivity.this, InputActivity.class).putExtra("menu", "menu_merchant"));
				}
			}	
		}else {
			Toast.makeText(this, "Anda login sebagai Staff", Toast.LENGTH_SHORT).show();
		}
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
