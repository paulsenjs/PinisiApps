package com.pinisielektra.apps;

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
import com.pinisielektra.apps.utils.JsonObjConstant;

public class EditInputActivity extends Activity implements IHttpResponseListener, JsonObjConstant{

	//pembelian
	private EditText edtKodeBarangPembelian;
	private EditText edtSatuanPembelian;
	private EditText edtKodeDistributor;
	private EditText edtIdTransaksi;
	
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
	private String[] menuIntent;
	
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
		menuIntent = getIntent().getExtras().getStringArray("edit");
		
		relativePembelian = (RelativeLayout)findViewById(R.id.relativePembelian);
		relativePenjualan = (RelativeLayout)findViewById(R.id.relativePenjualan);
		relativePelanggan = (RelativeLayout)findViewById(R.id.relativePelanggan);
		relativeDistributor = (RelativeLayout)findViewById(R.id.relativeDistributor);
		relativeInventory = (RelativeLayout)findViewById(R.id.relativeInventory);
		
		edtIdTransaksi = (EditText) findViewById(R.id.edtIdTransPembelian);
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
		
		if (menuIntent != null) {
			if (menuIntent[0].equalsIgnoreCase("edit_pembelian")){
				relativePembelian.setVisibility(View.VISIBLE);
				relativePenjualan.setVisibility(View.GONE);
				relativePelanggan.setVisibility(View.GONE);
				relativeDistributor.setVisibility(View.GONE);
				relativeInventory.setVisibility(View.GONE);
				showPembelianCurrentData(menuIntent);
			}else if (menuIntent[0].equalsIgnoreCase("edit_penjualan")) {
				relativePembelian.setVisibility(View.GONE);
				relativePenjualan.setVisibility(View.VISIBLE);
				relativePelanggan.setVisibility(View.GONE);
				relativeDistributor.setVisibility(View.GONE);
				relativeInventory.setVisibility(View.GONE);
			}else if (menuIntent[0].equalsIgnoreCase("edit_pelanggan")) {
				relativePembelian.setVisibility(View.GONE);
				relativePenjualan.setVisibility(View.GONE);
				relativePelanggan.setVisibility(View.VISIBLE);
				relativeDistributor.setVisibility(View.GONE);
				relativeInventory.setVisibility(View.GONE);
			}else if (menuIntent[0].equalsIgnoreCase("edit_distributor")) {
				relativePembelian.setVisibility(View.GONE);
				relativePenjualan.setVisibility(View.GONE);
				relativePelanggan.setVisibility(View.GONE);
				relativeDistributor.setVisibility(View.VISIBLE);
				relativeInventory.setVisibility(View.GONE);
			}else if (menuIntent[0].equalsIgnoreCase("edit_inventory")) {
				relativePembelian.setVisibility(View.GONE);
				relativePenjualan.setVisibility(View.GONE);
				relativePelanggan.setVisibility(View.GONE);
				relativeDistributor.setVisibility(View.GONE);
				relativeInventory.setVisibility(View.VISIBLE);
				showIventoryCurrentData(menuIntent);
			}
		}
	}

	private void showIventoryCurrentData(String[] data) {
		edtKodeBarangInventory.setText(data[1]);
		edtNamaBarangInventory.setText(data[2]);
		edtKategoryIdInventory.setText(data[3]);
		edtSatuanInventory.setText(data[4]);
		edtHargaJualInventory.setText(data[5]);
		edtHargaBeliInventory.setText(data[6]);
		edtExpDateInventory.setText(data[7]);
	}
	
	private void showPembelianCurrentData(String[] data) {
		edtKodeBarangPembelian.setText(data[1]);
		edtSatuanPembelian.setText(data[2]);
		edtKodeDistributor.setText(data[3]);
		edtIdTransaksi.setText(data[4]);
	}
	
	public void actionSendData(View v) {
		mProgressDialog.show();
		
		if (menuIntent[0].equalsIgnoreCase("edit_pembelian")){
			hashPost = new Hashtable<String, String>();
			hashPost.put("cmd", "edit");
			hashPost.put(OBJ_ID_TRANS, edtIdTransaksi.getText().toString());
			hashPost.put(OBJ_KODE_BARANG, edtKodeBarangPembelian.getText().toString());
			hashPost.put(OBJ_SATUAN_BARANG, edtSatuanPembelian.getText().toString());
			hashPost.put(OBJ_KODE_DISTRIBUTOR, edtKodeDistributor.getText().toString());
			new HttpConnectionTask(hashPost, this, 0).execute(Constants.API_POST_PEMBELIAN);
		}else if (menuIntent[0].equalsIgnoreCase("edit_penjualan")) {
			hashPost = new Hashtable<String, String>();
			hashPost.put("cmd", "edit");
//			hashPost.put("kodebarang", edtKodeBarangPembelian.getText().toString());
//			hashPost.put("satuan", edtSatuanPembelian.getText().toString());
//			hashPost.put("kodedistributor", edtKodeDistributor.getText().toString());
//			hashPost.put("tgl_transaksi", currentDate);
//			new HttpConnectionTask(hashPost, this, 0).execute(Constants.API_ADD_PEMBELIAN);
		}else if (menuIntent[0].equalsIgnoreCase("edit_pelanggan")) {
			hashPost = new Hashtable<String, String>();
			hashPost.put("cmd", "edit");
//			hashPost.put("kodebarang", edtKodeBarangPembelian.getText().toString());
//			hashPost.put("satuan", edtSatuanPembelian.getText().toString());
//			hashPost.put("kodedistributor", edtKodeDistributor.getText().toString());
//			hashPost.put("tgl_transaksi", currentDate);
//			new HttpConnectionTask(hashPost, this, 0).execute(Constants.API_ADD_PEMBELIAN);
		}else if (menuIntent[0].equalsIgnoreCase("edit_distributor")) {
			hashPost = new Hashtable<String, String>();
			hashPost.put("cmd", "edit");
//			hashPost.put("kodebarang", edtKodeBarangPembelian.getText().toString());
//			hashPost.put("satuan", edtSatuanPembelian.getText().toString());
//			hashPost.put("kodedistributor", edtKodeDistributor.getText().toString());
//			hashPost.put("tgl_transaksi", currentDate);
//			new HttpConnectionTask(hashPost, this, 0).execute(Constants.API_ADD_PEMBELIAN);
		}else if (menuIntent[0].equalsIgnoreCase("edit_inventory")) {
			hashPost = new Hashtable<String, String>();
			hashPost.put("cmd", "edit");
			hashPost.put(OBJ_KODE_BARANG, edtKodeBarangInventory.getText().toString());
			hashPost.put(OBJ_NAMA_BARANG, edtNamaBarangInventory.getText().toString());
			hashPost.put(OBJ_CATEGORY_ID, edtKategoryIdInventory.getText().toString());
			hashPost.put(OBJ_SATUAN_BARANG, edtSatuanInventory.getText().toString());
			hashPost.put(OBJ_HARGA_JUAL, edtHargaJualInventory.getText().toString());
			hashPost.put(OBJ_HARGA_BELI, edtHargaBeliInventory.getText().toString());
			hashPost.put(OBJ_EXP_DATE, edtExpDateInventory.getText().toString());
			new HttpConnectionTask(hashPost, this, 0).execute(Constants.API_POST_INVENTORY);
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
