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
import android.widget.Toast;

import com.pinisielektra.apps.FilteringActivity;
import com.pinisielektra.apps.R;

public class DashboardFragment extends Fragment implements OnClickListener{
	
	private ImageView btnSalesReport;
	private ImageView btnBestSales;
	private ImageView btnInventoryReport;
	private ImageView btnPurchaseReport;
	private ImageView btnInputPurchasing;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
		
		btnSalesReport = (ImageView) view.findViewById(R.id.img_sales_report);
		btnBestSales = (ImageView) view.findViewById(R.id.img_best_selling_products);
		btnInventoryReport = (ImageView) view.findViewById(R.id.img_inventory);
		btnPurchaseReport = (ImageView) view.findViewById(R.id.img_purchasing_report);
		btnInputPurchasing = (ImageView) view.findViewById(R.id.img_input_purchasing);
		
		btnSalesReport.setOnClickListener(this);
		btnBestSales.setOnClickListener(this);
		btnInventoryReport.setOnClickListener(this);
		btnPurchaseReport.setOnClickListener(this);
		btnInputPurchasing.setOnClickListener(this);
		
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
		case R.id.img_sales_report:
			Toast.makeText(getActivity(), "sales report", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(getActivity(), FilteringActivity.class);
			startActivity(intent);
			break;
		case R.id.img_best_selling_products:
			Toast.makeText(getActivity(), "best sales", Toast.LENGTH_SHORT).show();
			break;
		case R.id.img_inventory:
			Toast.makeText(getActivity(), "inventory", Toast.LENGTH_SHORT).show();
			break;
		case R.id.img_purchasing_report:
			Toast.makeText(getActivity(), "purchasing", Toast.LENGTH_SHORT).show();
			break;
		case R.id.img_input_purchasing:
			Toast.makeText(getActivity(), "input", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
}
