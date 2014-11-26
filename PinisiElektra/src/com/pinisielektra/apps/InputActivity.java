package com.pinisielektra.apps;

import java.text.DateFormat;
import java.util.Date;
import java.util.Hashtable;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pinisielektra.apps.connection.HttpConnectionTask;
import com.pinisielektra.apps.connection.IHttpResponseListener;
import com.pinisielektra.apps.utils.Constants;

public class InputActivity extends Activity implements IHttpResponseListener{

	//pembelian
	private EditText edtKodeBarangPembelian;
	private EditText edtSatuanPembelian;
	private EditText edtKodeDistributor;
	
	//inventory
	private EditText edtKodeBarangInventory;
	private EditText edtNamaBarangInventory;
	private EditText edtKategoryIdInventory;
	private EditText edtSatuanInventory;
	private EditText edtHargaJualInventory;
	private EditText edtHargaBeliInventory;
	private EditText edtExpDateInventory;
	
	private ProgressDialog mProgressDialog;
	private Hashtable<String, String> hashPost;
	private String currentDate;
	private String menuIntent;
	
	private RelativeLayout relativePembelian;
	private RelativeLayout relativePenjualan;
	private RelativeLayout relativePelanggan;
	private RelativeLayout relativeDistributor;
	private RelativeLayout relativeInventory;
	
	private int lYear;
	private int lMonth;
	private int lDay;
	private static final int EXP_DATE_DIALOG_ID = 992;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
		
		mProgressDialog = new ProgressDialog(this);
		currentDate = DateFormat.getDateTimeInstance().format(new Date());
		menuIntent = getIntent().getExtras().getString("menu");
		
		relativePembelian = (RelativeLayout)findViewById(R.id.relativePembelian);
		relativePenjualan = (RelativeLayout)findViewById(R.id.relativePenjualan);
		relativePelanggan = (RelativeLayout)findViewById(R.id.relativePelanggan);
		relativeDistributor = (RelativeLayout)findViewById(R.id.relativeDistributor);
		relativeInventory = (RelativeLayout)findViewById(R.id.relativeInventory);
		
		if (menuIntent != null) {
			if (menuIntent.equalsIgnoreCase("menu_pembelian")){
				relativePembelian.setVisibility(View.VISIBLE);
				relativePenjualan.setVisibility(View.GONE);
				relativePelanggan.setVisibility(View.GONE);
				relativeDistributor.setVisibility(View.GONE);
				relativeInventory.setVisibility(View.GONE);
			}else if (menuIntent.equalsIgnoreCase("menu_penjualan")) {
				relativePembelian.setVisibility(View.GONE);
				relativePenjualan.setVisibility(View.VISIBLE);
				relativePelanggan.setVisibility(View.GONE);
				relativeDistributor.setVisibility(View.GONE);
				relativeInventory.setVisibility(View.GONE);
			}else if (menuIntent.equalsIgnoreCase("menu_pelanggan")) {
				relativePembelian.setVisibility(View.GONE);
				relativePenjualan.setVisibility(View.GONE);
				relativePelanggan.setVisibility(View.VISIBLE);
				relativeDistributor.setVisibility(View.GONE);
				relativeInventory.setVisibility(View.GONE);
			}else if (menuIntent.equalsIgnoreCase("menu_distributor")) {
				relativePembelian.setVisibility(View.GONE);
				relativePenjualan.setVisibility(View.GONE);
				relativePelanggan.setVisibility(View.GONE);
				relativeDistributor.setVisibility(View.VISIBLE);
				relativeInventory.setVisibility(View.GONE);
			}else if (menuIntent.equalsIgnoreCase("menu_inventory")) {
				relativePembelian.setVisibility(View.GONE);
				relativePenjualan.setVisibility(View.GONE);
				relativePelanggan.setVisibility(View.GONE);
				relativeDistributor.setVisibility(View.GONE);
				relativeInventory.setVisibility(View.VISIBLE);
			}
		}
		
		edtKodeBarangPembelian = (EditText) findViewById(R.id.edtKodeBarangPembelian);
		edtSatuanPembelian = (EditText) findViewById(R.id.edtSatuanPembelian);
		edtKodeDistributor = (EditText) findViewById(R.id.edtKodeDistributorPembelian);

		edtKodeBarangInventory = (EditText) findViewById(R.id.edtKodeBarangInventory); 
		edtNamaBarangInventory = (EditText) findViewById(R.id.edtNamaBarangInventory);
		edtKategoryIdInventory = (EditText) findViewById(R.id.edtKategoriIdInventory);
		edtSatuanInventory = (EditText) findViewById(R.id.edtSatuanInventory);
		edtHargaJualInventory = (EditText) findViewById(R.id.edtHargaJualInventory);
		edtHargaBeliInventory = (EditText) findViewById(R.id.edtHargaBeliInventory);
		edtExpDateInventory = (EditText) findViewById(R.id.edtExpDateInventory);
		edtExpDateInventory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(EXP_DATE_DIALOG_ID);
			}
		});
	}

	public void actionSendData(View v) {
		mProgressDialog.show();
		
		if (menuIntent.equalsIgnoreCase("menu_pembelian")){
			hashPost = new Hashtable<String, String>();
			hashPost.put("cmd", "add");
			hashPost.put("kodebarang", edtKodeBarangPembelian.getText().toString());
			hashPost.put("satuan", edtSatuanPembelian.getText().toString());
			hashPost.put("kodedistributor", edtKodeDistributor.getText().toString());
			hashPost.put("tgl_transaksi", currentDate);
			new HttpConnectionTask(hashPost, this, 0).execute(Constants.API_ADD_PEMBELIAN);
		}else if (menuIntent.equalsIgnoreCase("menu_penjualan")) {
			hashPost = new Hashtable<String, String>();
			hashPost.put("cmd", "add");
//			hashPost.put("kodebarang", edtKodeBarangPembelian.getText().toString());
//			hashPost.put("satuan", edtSatuanPembelian.getText().toString());
//			hashPost.put("kodedistributor", edtKodeDistributor.getText().toString());
//			hashPost.put("tgl_transaksi", currentDate);
			new HttpConnectionTask(hashPost, this, 0).execute(Constants.API_ADD_PEMBELIAN);
		}else if (menuIntent.equalsIgnoreCase("menu_pelanggan")) {
			hashPost = new Hashtable<String, String>();
			hashPost.put("cmd", "add");
//			hashPost.put("kodebarang", edtKodeBarangPembelian.getText().toString());
//			hashPost.put("satuan", edtSatuanPembelian.getText().toString());
//			hashPost.put("kodedistributor", edtKodeDistributor.getText().toString());
//			hashPost.put("tgl_transaksi", currentDate);
			new HttpConnectionTask(hashPost, this, 0).execute(Constants.API_ADD_PEMBELIAN);
		}else if (menuIntent.equalsIgnoreCase("menu_distributor")) {
			hashPost = new Hashtable<String, String>();
			hashPost.put("cmd", "add");
//			hashPost.put("kodebarang", edtKodeBarangPembelian.getText().toString());
//			hashPost.put("satuan", edtSatuanPembelian.getText().toString());
//			hashPost.put("kodedistributor", edtKodeDistributor.getText().toString());
//			hashPost.put("tgl_transaksi", currentDate);
			new HttpConnectionTask(hashPost, this, 0).execute(Constants.API_ADD_PEMBELIAN);
		}else if (menuIntent.equalsIgnoreCase("menu_inventory")) {
			hashPost = new Hashtable<String, String>();
			hashPost.put("cmd", "add");
			hashPost.put("kodebarang", edtKodeBarangInventory.getText().toString());
			hashPost.put("namabarang", edtNamaBarangInventory.getText().toString());
			hashPost.put("categoryid", edtKategoryIdInventory.getText().toString());
			hashPost.put("satuan", edtSatuanInventory.getText().toString());
			hashPost.put("harga_jual", edtHargaJualInventory.getText().toString());
			hashPost.put("harga_beli", edtHargaBeliInventory.getText().toString());
			hashPost.put("exp_date", edtExpDateInventory.getText().toString());
			new HttpConnectionTask(hashPost, this, 0).execute(Constants.API_ADD_PEMBELIAN);
		}
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case EXP_DATE_DIALOG_ID:
			return new DatePickerDialog(this, expDatePickerListener, lYear, lMonth, lDay);
		}
		return null;
	}
	
	private DatePickerDialog.OnDateSetListener expDatePickerListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			lYear = year;
			lMonth = monthOfYear;
			lDay = dayOfMonth;
			
			edtExpDateInventory.setText(new StringBuilder()
					.append(lYear)
					.append("-").append(lMonth + 1)
					.append("-").append(lDay));
		}
	};
	
	@Override
	public void resultSuccess(int type, String result) {
		if (mProgressDialog != null)
			mProgressDialog.dismiss();
		Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void resultFailed(int type, String strError) {
		if (mProgressDialog != null)
			mProgressDialog.dismiss();
		
		Toast.makeText(this, strError, Toast.LENGTH_SHORT).show();
	}
}
