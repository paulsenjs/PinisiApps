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
import android.widget.Toast;

import com.pinisielektra.apps.FilteringActivity;
import com.pinisielektra.apps.PieChartActivity;
import com.pinisielektra.apps.R;
import com.pinisielektra.apps.connection.IHttpResponseListener;

public class DashboardFragment extends Fragment implements OnClickListener, IHttpResponseListener{
	
	private ImageView btnSalesReport;
	private ImageView btnBestSales;
	private ImageView btnInventoryReport;
	private ImageView btnPurchaseReport;
	private ImageView btnInputPurchasing;
	private TextView txtName;
	
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
		txtName = (TextView) view.findViewById(R.id.txt_username);
		
		btnSalesReport.setOnClickListener(this);
		btnBestSales.setOnClickListener(this);
		btnInventoryReport.setOnClickListener(this);
		btnPurchaseReport.setOnClickListener(this);
		btnInputPurchasing.setOnClickListener(this);
		
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
		case R.id.img_sales_report:
		case R.id.img_best_selling_products:
		case R.id.img_purchasing_report:
		case R.id.img_inventory:
			Intent intent = new Intent(getActivity(), FilteringActivity.class);
			startActivity(intent);
			break;
		case R.id.img_input_purchasing:
			Toast.makeText(getActivity(), "input", Toast.LENGTH_SHORT).show();
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
