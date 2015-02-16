package com.pinisielektra.apps.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinisielektra.apps.DashboardActivitySecondDepth;
import com.pinisielektra.apps.R;
import com.pinisielektra.apps.ReportActivity;
import com.pinisielektra.apps.connection.IHttpResponseListener;

public class DashboardFragment extends Fragment implements OnClickListener, IHttpResponseListener{
	
	private ImageView btnPembelian;
	private ImageView btnPenjualan;
	private ImageView btnInventory;
	private ImageView btnPelanggan;
	private ImageView btnDistributor;
	private ImageView btnMerchant;
	private TextView txtName;
	
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
