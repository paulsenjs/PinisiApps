package com.pinisielektra.apps.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinisielektra.apps.R;
import com.pinisielektra.apps.ReportActivity;
import com.pinisielektra.apps.connection.IHttpResponseListener;
import com.pinisielektra.apps.object.DistributorObj;
import com.pinisielektra.apps.object.InventoryObj;
import com.pinisielektra.apps.object.MerchantObj;
import com.pinisielektra.apps.utils.Constants;

public class DashboardFragment extends Fragment implements OnClickListener, IHttpResponseListener{
	
	private ImageView btnPembelian;
	private ImageView btnPenjualan;
	private ImageView btnInventory;
	private ImageView btnPelanggan;
	private ImageView btnDistributor;
	private ImageView btnMerchant;
	private TextView txtName;
	
	private MerchantObj merchantObj;
	private InventoryObj inventoryObj;
	private DistributorObj distributorObj;
	
	String savedKodeDistributor;
	String savedKodeBarang;
	String savedKodeMerchant;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
		
		btnPembelian = (ImageView) view.findViewById(R.id.imgPembelian);
		btnPenjualan = (ImageView) view.findViewById(R.id.imgPenjualan);
		btnInventory = (ImageView) view.findViewById(R.id.imgInventory);
		btnPelanggan = (ImageView) view.findViewById(R.id.imgPelanggan);
		btnDistributor = (ImageView) view.findViewById(R.id.imgDistributor);
		btnMerchant = (ImageView) view.findViewById(R.id.imgMerchant);
		txtName = (TextView) view.findViewById(R.id.txt_username);
		
		btnPembelian.setOnClickListener(this);
		btnPenjualan.setOnClickListener(this);
		btnInventory.setOnClickListener(this);
		btnPelanggan.setOnClickListener(this);
		btnDistributor.setOnClickListener(this);
		btnMerchant.setOnClickListener(this);

		txtName.setText("Hi " + getActivity().getIntent().getExtras().getString("uName"));
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		merchantObj = new MerchantObj(getActivity());
		merchantObj.retrieveKodeMerchant();
		
		inventoryObj = new InventoryObj(getActivity());
		inventoryObj.retrieveKodeBarang();
		
		distributorObj = new DistributorObj(getActivity());
		distributorObj.retrieveKodeDistributor();
		
		try {
			SharedPreferences prefsKodeDistributor = getActivity().getSharedPreferences(Constants.PREF_KODE_DISTRIBUTOR, Context.MODE_PRIVATE);
			savedKodeDistributor = prefsKodeDistributor.getString("kodedist", null);
					
		} catch (ClassCastException e) {
			Log.d("error ", e.getMessage());
		}
		
		try {
			SharedPreferences prefsKodeBarang = getActivity().getSharedPreferences(Constants.PREF_KODE_BARANG, Context.MODE_PRIVATE);
			savedKodeBarang = prefsKodeBarang.getString("kodebrg", null);
				
		} catch (ClassCastException e) {
			Log.d("error ", e.getMessage());
		}
		
		try {
			SharedPreferences prefsKodeMerchant = getActivity().getSharedPreferences(Constants.PREF_KODE_MERCHANT, Context.MODE_PRIVATE);
			savedKodeMerchant = prefsKodeMerchant.getString("kodemerch", null);
			
		} catch (ClassCastException e) {
			Log.d("error ", e.getMessage());
		}
		
		
		Log.d(">>dashboard ", ""+Constants.KODE_BARANG_NULL);
		Log.d(">>dashboard ", ""+Constants.KODE_DIST_NULL);
		Log.d(">>dashboard ", ""+Constants.KODE_MERCHANT_NULL);
		
		try {
//			SharedPreferences prefsKodeBarang = getActivity().getSharedPreferences(Constants.PREF_KODE_BARANG, Context.MODE_PRIVATE);
//			savedKodeBarang = prefsKodeBarang.getString("kodebrg", null);
//			
//			SharedPreferences prefsKodeDistributor = getActivity().getSharedPreferences(Constants.PREF_KODE_DISTRIBUTOR, Context.MODE_PRIVATE);
//			savedKodeDistributor = prefsKodeDistributor.getString("kodedist", null);
//			
//			SharedPreferences prefsKodeMerchant = getActivity().getSharedPreferences(Constants.PREF_KODE_MERCHANT, Context.MODE_PRIVATE);
//			savedKodeMerchant = prefsKodeMerchant.getString("kodemerch", null);

			if (savedKodeBarang == null &&
					(savedKodeDistributor == null &&
							savedKodeMerchant == null)){
				
				btnPembelian.setEnabled(true);
				btnPembelian.setAlpha(1.0f);
				btnPenjualan.setEnabled(true);
				btnPenjualan.setAlpha(1.0f);
			}else{
				btnPembelian.setEnabled(false);
				btnPembelian.setAlpha(0.5f);
				btnPenjualan.setEnabled(false);
				btnPenjualan.setAlpha(0.5f);
			}
			
			/*if (savedKodeBarang.equalsIgnoreCase("no-records") ||
					(savedKodeDistributor.equalsIgnoreCase("no-records") ||
							savedKodeMerchant.equalsIgnoreCase("no-records"))){
				
				btnPembelian.setEnabled(false);
				btnPembelian.setAlpha(0.5f);
				btnPenjualan.setEnabled(false);
				btnPenjualan.setAlpha(0.5f);
				btnPelanggan.setEnabled(false);
				btnPelanggan.setAlpha(0.5f);
			}else {
				btnPembelian.setEnabled(true);
				btnPembelian.setAlpha(1.0f);
				btnPenjualan.setEnabled(true);
				btnPenjualan.setAlpha(1.0f);
				btnPelanggan.setEnabled(true);
				btnPelanggan.setAlpha(1.0f);
			}*/
		} catch (ClassCastException e) {
			Log.d("error ", e.getMessage());
		}
		
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		getActivity().getMenuInflater().inflate(R.menu.dashboard, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgPembelian:
			startActivity(new Intent().setClass(getActivity(), ReportActivity.class).putExtra("menu", "menu_pembelian"));
			break;
		case R.id.imgPenjualan:
			startActivity(new Intent().setClass(getActivity(), ReportActivity.class).putExtra("menu", "menu_penjualan"));
			break;
		case R.id.imgInventory:
			startActivity(new Intent().setClass(getActivity(), ReportActivity.class).putExtra("menu", "menu_inventory"));
			break;
		case R.id.imgPelanggan:
			startActivity(new Intent().setClass(getActivity(), ReportActivity.class).putExtra("menu", "menu_pelanggan"));
			break;
		case R.id.imgDistributor:
			startActivity(new Intent().setClass(getActivity(), ReportActivity.class).putExtra("menu", "menu_distributor"));
			break;
		case R.id.imgMerchant:
			startActivity(new Intent().setClass(getActivity(), ReportActivity.class).putExtra("menu", "menu_merchant"));
			break;
		default:
			break;
		}
	}

	@Override
	public void resultSuccess(int type, String result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resultFailed(int type, String strError) {
		// TODO Auto-generated method stub
		
	}
}
