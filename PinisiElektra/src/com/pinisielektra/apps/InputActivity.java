package com.pinisielektra.apps;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.pinisielektra.apps.adapter.SpinnerAdapter;
import com.pinisielektra.apps.connection.HttpConnectionTask;
import com.pinisielektra.apps.connection.IHttpResponseListener;
import com.pinisielektra.apps.object.DistributorObj;
import com.pinisielektra.apps.object.InventoryObj;
import com.pinisielektra.apps.object.MenuObj;
import com.pinisielektra.apps.utils.Constants;
import com.pinisielektra.apps.utils.JsonObjConstant;

public class InputActivity extends MenuObj implements JsonObjConstant, IHttpResponseListener, OnItemSelectedListener {

	// pembelian
	private Spinner spnKodeBarangPembelian;
	private EditText edtSatuanPembelian;
	private Spinner spnKodeDistributorPembelian;
	private EditText edtTglTransaksiPembelian;

	// inventory
	private EditText edtKodeBarangInventory;
	private EditText edtNamaBarangInventory;
//	private Spinner edtKategoryIdInventory;
	private EditText edtSatuanInventory;
	private EditText edtHargaJualInventory;
	private EditText edtHargaBeliInventory;
	private EditText edtExpDateInventory;
	private InventoryObj inventoryObj;

	// pelanggan
	private EditText edtIdPelanggan;
	private EditText edtNamaPelanggan;
	private EditText edtAlamatPelanggan;
	private EditText edtPhonePelanggan;

	// distributor
	private EditText edtKodeDistributor;
	private EditText edtNamaDistributor;
	private DistributorObj distributorObj;
	
	// penjualan
	private Spinner spnKodeBarangPenjualan;
	private EditText edtTglTransaksiPenjualan;
	private EditText edtSatuanPenjualan;
	private SpinnerAdapter spinnerAdapterKodeBarang;
	private SpinnerAdapter spinnerAdapterKodeDistributor;
	
	private ProgressDialog mProgressDialog;
	private Hashtable<String, String> hashPost;
	private String currentDate;
	private String menuIntent;
	private String savedId;
	private String selectedKodeDistributor;
	private String selectedKodeBarang;
	
	private RelativeLayout relativePembelian;
	private RelativeLayout relativePenjualan;
	private RelativeLayout relativePelanggan;
	private RelativeLayout relativeDistributor;
	private RelativeLayout relativeInventory;
	
	private int lYear;
	private int lMonth;
	private int lDay;
	
	private Set<String> setKodeDistributor;
	private Set<String> setKodeBarang;
	
	private List<String> lstKodeDistributor;
	private List<String> lstKodeBarang;
	
	private static final int TGL_DIALOG_ID = 993;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mProgressDialog = new ProgressDialog(this);
		currentDate = (String) android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date());
		menuIntent = getIntent().getExtras().getString("menu");
		
		inventoryObj = new InventoryObj(this);
		distributorObj = new DistributorObj(this);
		
		SharedPreferences prefs = getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE);
		savedId = prefs.getString("uId", null);

		Calendar c = Calendar.getInstance();
		lYear = c.get(Calendar.YEAR);
		lMonth = c.get(Calendar.MONTH);
		lDay = c.get(Calendar.DAY_OF_MONTH);
		
		relativePembelian = (RelativeLayout) findViewById(R.id.relativePembelian);
		relativePenjualan = (RelativeLayout) findViewById(R.id.relativePenjualan);
		relativePelanggan = (RelativeLayout) findViewById(R.id.relativePelanggan);
		relativeDistributor = (RelativeLayout) findViewById(R.id.relativeDistributor);
		relativeInventory = (RelativeLayout) findViewById(R.id.relativeInventory);
		
		initLayout();
	
		SharedPreferences prefsKodeDistributor = getSharedPreferences(Constants.PREF_KODE_DISTRIBUTOR, MODE_PRIVATE);
		setKodeDistributor = prefsKodeDistributor.getStringSet("kodedist", null);
		lstKodeDistributor = new ArrayList<String>(setKodeDistributor);
		initSpinnerKodeDistributor();
		Log.d(">>>", setKodeDistributor.toString());
		
		SharedPreferences prefsKodeBarang = getSharedPreferences(Constants.PREF_KODE_BARANG, MODE_PRIVATE);
		setKodeBarang = prefsKodeBarang.getStringSet("kodebrg", null); 
		lstKodeBarang = new ArrayList<String>(setKodeBarang);
		initSpinnerKodeBarang();
		Log.d(">>>", setKodeBarang.toString());
	}

	private void initLayout() {
		if (menuIntent != null) {
			if (menuIntent.equalsIgnoreCase("menu_pembelian")) {
				setMenuPembelian(true);
				getActionBar().setTitle("Input Pembelian");
				relativePembelian.setVisibility(View.VISIBLE);
				relativePenjualan.setVisibility(View.GONE);
				relativePelanggan.setVisibility(View.GONE);
				relativeDistributor.setVisibility(View.GONE);
				relativeInventory.setVisibility(View.GONE);
			} else if (menuIntent.equalsIgnoreCase("menu_penjualan")) {
				setMenuPenjualan(true);
				getActionBar().setTitle("Input Penjualan");
				relativePembelian.setVisibility(View.GONE);
				relativePenjualan.setVisibility(View.VISIBLE);
				relativePelanggan.setVisibility(View.GONE);
				relativeDistributor.setVisibility(View.GONE);
				relativeInventory.setVisibility(View.GONE);
			} else if (menuIntent.equalsIgnoreCase("menu_pelanggan")) {
				setMenuPelanggan(true);
				getActionBar().setTitle("Input Pelanggan");
				relativePembelian.setVisibility(View.GONE);
				relativePenjualan.setVisibility(View.GONE);
				relativePelanggan.setVisibility(View.VISIBLE);
				relativeDistributor.setVisibility(View.GONE);
				relativeInventory.setVisibility(View.GONE);
			} else if (menuIntent.equalsIgnoreCase("menu_distributor")) {
				setMenuDistributor(true);
				getActionBar().setTitle("Input Distributor");
				relativePembelian.setVisibility(View.GONE);
				relativePenjualan.setVisibility(View.GONE);
				relativePelanggan.setVisibility(View.GONE);
				relativeDistributor.setVisibility(View.VISIBLE);
				relativeInventory.setVisibility(View.GONE);
			} else if (menuIntent.equalsIgnoreCase("menu_inventory")) {
				setMenuInventory(true);
				getActionBar().setTitle("Input Inventory");
				relativePembelian.setVisibility(View.GONE);
				relativePenjualan.setVisibility(View.GONE);
				relativePelanggan.setVisibility(View.GONE);
				relativeDistributor.setVisibility(View.GONE);
				relativeInventory.setVisibility(View.VISIBLE);
			}
		}

		spnKodeBarangPembelian = (Spinner) findViewById(R.id.edtKodeBarangPembelian);
		edtSatuanPembelian = (EditText) findViewById(R.id.edtSatuanPembelian);
		spnKodeDistributorPembelian = (Spinner) findViewById(R.id.edtKodeDistributorPembelian);
		edtTglTransaksiPembelian = (EditText) findViewById(R.id.edtTglTrxPembelian);
		edtTglTransaksiPembelian.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(TGL_DIALOG_ID);
			}
		});

		edtKodeBarangInventory = (EditText) findViewById(R.id.edtKodeBarangInventory);
		edtNamaBarangInventory = (EditText) findViewById(R.id.edtNamaBarangInventory);
//		edtKategoryIdInventory = (Spinner) findViewById(R.id.edtKategoriIdInventory);
		edtSatuanInventory = (EditText) findViewById(R.id.edtSatuanInventory);
		edtHargaJualInventory = (EditText) findViewById(R.id.edtHargaJualInventory);
		edtHargaBeliInventory = (EditText) findViewById(R.id.edtHargaBeliInventory);
		edtExpDateInventory = (EditText) findViewById(R.id.edtExpDateInventory);
		edtExpDateInventory.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(TGL_DIALOG_ID);
			}
		});

		// edtIdPelanggan = (EditText) findViewById(R.id.edtIdPelanggan);
		edtNamaPelanggan = (EditText) findViewById(R.id.edtNamaPelanggan);
		edtPhonePelanggan = (EditText) findViewById(R.id.edtPhonePelanggan);
		edtAlamatPelanggan = (EditText) findViewById(R.id.edtAlamatPelanggan);

		edtKodeDistributor = (EditText) findViewById(R.id.edtKodeDistributor);
		edtNamaDistributor = (EditText) findViewById(R.id.edtNamaDistributor);

		spnKodeBarangPenjualan = (Spinner) findViewById(R.id.edtKodeBarangPenjualan);
		edtTglTransaksiPenjualan = (EditText) findViewById(R.id.edtTglTransaksi);
		edtTglTransaksiPenjualan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(TGL_DIALOG_ID);
			}
		});
		edtSatuanPenjualan = (EditText) findViewById(R.id.edtSatuanPenjualan);
	}
	
	public void actionSendData(View v) {
		mProgressDialog.show();

		if (isMenuPembelian()) {
			hashPost = new Hashtable<String, String>();
			hashPost.put("cmd", "add");
			hashPost.put(OBJ_KODE_BARANG, getSelectedKodeBarang());
			hashPost.put(OBJ_SATUAN_BARANG, edtSatuanPembelian.getText().toString());
			hashPost.put(OBJ_KODE_DISTRIBUTOR, getSelectedKodeDistributor());
			hashPost.put(OBJ_CREATOR, savedId);
			new HttpConnectionTask(hashPost, this, 0).execute(Constants.API_POST_PEMBELIAN);
		} else if (isMenuPenjualan()) {
			hashPost = new Hashtable<String, String>();
			hashPost.put("cmd", "add");
			hashPost.put(OBJ_KODE_BARANG, getSelectedKodeBarang());
			hashPost.put(OBJ_TGL_TRANS, edtTglTransaksiPenjualan.getText().toString());
			hashPost.put(OBJ_SATUAN_BARANG, edtSatuanPenjualan.getText().toString());
			hashPost.put(OBJ_CREATOR, savedId);
			new HttpConnectionTask(hashPost, this, 0).execute(Constants.API_POST_PENJUALAN);
		} else if (isMenuPelanggan()) {
			hashPost = new Hashtable<String, String>();
			hashPost.put("cmd", "add");
			hashPost.put(OBJ_NAMA, edtNamaPelanggan.getText().toString());
			hashPost.put(OBJ_ALAMAT, edtAlamatPelanggan.getText().toString());
			hashPost.put(OBJ_PHONE, edtPhonePelanggan.getText().toString());
			hashPost.put(OBJ_CREATOR, savedId);
			new HttpConnectionTask(hashPost, this, 0).execute(Constants.API_POST_PELANGGAN);
		} else if (isMenuDistributor()) {
			hashPost = new Hashtable<String, String>();
			hashPost.put("cmd", "add");
			hashPost.put(OBJ_KODE_DISTRIBUTOR, edtKodeDistributor.getText().toString());
			hashPost.put(OBJ_NAMA, edtNamaDistributor.getText().toString());
			hashPost.put(OBJ_CREATOR, savedId);
			new HttpConnectionTask(hashPost, this, 2).execute(Constants.API_POST_DISTRIBUTOR);
		} else if (isMenuInventory()) {
			hashPost = new Hashtable<String, String>();
			hashPost.put("cmd", "add");
			hashPost.put(OBJ_KODE_BARANG, edtKodeBarangInventory.getText().toString());
			hashPost.put(OBJ_NAMA_BARANG, edtNamaBarangInventory.getText().toString());
			hashPost.put(OBJ_SATUAN_BARANG, edtSatuanInventory.getText().toString());
			hashPost.put(OBJ_HARGA_JUAL, edtHargaJualInventory.getText().toString());
			hashPost.put(OBJ_HARGA_BELI, edtHargaBeliInventory.getText().toString());
			hashPost.put(OBJ_EXP_DATE, edtExpDateInventory.getText().toString());
			hashPost.put(OBJ_CREATOR, savedId);
			new HttpConnectionTask(hashPost, this, 1).execute(Constants.API_POST_INVENTORY);
		}
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TGL_DIALOG_ID:
			return new DatePickerDialog(this, tglTransaksiPickerListener, lYear, lMonth, lDay);
		}
		
		return null;
	}

	private DatePickerDialog.OnDateSetListener tglTransaksiPickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			lYear = year;
			lMonth = monthOfYear;
			lDay = dayOfMonth;

			if (isMenuPembelian()) {
				edtTglTransaksiPembelian.setText(new StringBuilder().append(lYear).append("-").append(lMonth + 1).append("-").append(lDay));				
			}else if (isMenuPenjualan()) {
				edtTglTransaksiPenjualan.setText(new StringBuilder().append(lYear).append("-").append(lMonth + 1).append("-").append(lDay));
			}else if (isMenuInventory()) {
				edtExpDateInventory.setText(new StringBuilder().append(lYear).append("-").append(lMonth + 1).append("-").append(lDay));
			}
		}
	};
	
	private void initSpinnerKodeBarang() {
		spinnerAdapterKodeBarang = new SpinnerAdapter(this, 0, lstKodeBarang);	
		if (isMenuPembelian()) {
			spnKodeBarangPembelian.setAdapter(spinnerAdapterKodeBarang);
			spnKodeBarangPembelian.setOnItemSelectedListener(this);
		}else if (isMenuPenjualan()) {
			spnKodeBarangPenjualan.setAdapter(spinnerAdapterKodeBarang);
			spnKodeBarangPenjualan.setOnItemSelectedListener(this);
		}
	}

//	private void initSpinnerKodeBarangPenjualan() {
//		spinnerAdapter = new SpinnerAdapter(this, 0, lstKodeBarang);
//		spnKodeBarangPenjualan.setAdapter(spinnerAdapter);
//		spnKodeBarangPenjualan.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapter) { 
//            	
//            }
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                setSelectedKodeBarang(spinnerAdapter.getItem(arg2));
//			}
//        });
//	}
	
	private void initSpinnerKodeDistributor() {
		spinnerAdapterKodeDistributor = new SpinnerAdapter(this, 0, lstKodeDistributor);
		spnKodeDistributorPembelian.setAdapter(spinnerAdapterKodeDistributor);
		spnKodeDistributorPembelian.setOnItemSelectedListener(this);
	}
	
	@Override
	public void resultSuccess(int type, String result) {
		if (mProgressDialog != null)
			mProgressDialog.dismiss();
		switch (type) {
		case 1:
			inventoryObj.retrieveKodeBarang();
			break;
		case 2:
			distributorObj.retrieveKodeDistributor();
			break;
		default:
			break;
		}
		Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void resultFailed(int type, String strError) {
		if (mProgressDialog != null)
			mProgressDialog.dismiss();

		Toast.makeText(this, strError, Toast.LENGTH_SHORT).show();
	}
	
	private String getSelectedKodeDistributor() {
		return selectedKodeDistributor;
	}

	private void setSelectedKodeDistributor(String selectedKodeDistributor) {
		this.selectedKodeDistributor = selectedKodeDistributor;
	}

	private String getSelectedKodeBarang() {
		return selectedKodeBarang;
	}

	private void setSelectedKodeBarang(String selectedKodeBarang) {
		this.selectedKodeBarang = selectedKodeBarang;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg0.getId()) {
		case R.id.edtKodeBarangPembelian:
		case R.id.edtKodeBarangPenjualan:
			setSelectedKodeBarang(spinnerAdapterKodeBarang.getItem(arg2));			
			break;
		case R.id.edtKodeDistributorPembelian:
			setSelectedKodeDistributor(spinnerAdapterKodeDistributor.getItem(arg2));
			break;
		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
}
