package com.pinisielektra.apps.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pinisielektra.apps.R;
import com.pinisielektra.apps.object.InventoryObj;
import com.pinisielektra.apps.object.PembelianObj;

public class ReportAdapter extends BaseAdapter {
	
	private Context context;
	private int idMenu;
	private ArrayList<Object> arrObjs;
	
	public ReportAdapter(int idMenu, Context ctx, ArrayList<Object> arrObj) {
		this.idMenu = idMenu;
		this.context = ctx;
		this.arrObjs = arrObj;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrObjs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		
		if (convertView==null) {
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.item_report, null);
		
			viewHolder = new ViewHolder();
			viewHolder.linearPenjualan = (LinearLayout) convertView.findViewById(R.id.linearPenjualan);
			viewHolder.linearPembelian = (LinearLayout) convertView.findViewById(R.id.linearPembelian);
			viewHolder.linearInventory = (LinearLayout) convertView.findViewById(R.id.linearInventory);
			viewHolder.linearPelanggan = (LinearLayout) convertView.findViewById(R.id.linearPelanggan);
			viewHolder.linearDistributor = (LinearLayout) convertView.findViewById(R.id.linearDistributor);
			
			switch (idMenu) {
			case 1:
				viewHolder.linearPenjualan.setVisibility(View.VISIBLE);
				break;
			case 2:
				viewHolder.linearPembelian.setVisibility(View.VISIBLE);
				viewHolder.txtKodeBarangPembelian = (TextView) convertView.findViewById(R.id.txtKodeBarangPembelian);
				viewHolder.txtSatuanPembelian = (TextView) convertView.findViewById(R.id.txtSatuanPembelian);
				viewHolder.txtKodeDistributorPembelian = (TextView) convertView.findViewById(R.id.txtKodeDistributorPembelian);
				viewHolder.txtTanggalTransaksi = (TextView) convertView.findViewById(R.id.txtTglTrxPembelian);
				break;
			case 3:
				viewHolder.linearInventory.setVisibility(View.VISIBLE);
				viewHolder.txtNamaBarang = (TextView) convertView.findViewById(R.id.txtNamaBarangInventory);
				viewHolder.txtSatuan = (TextView) convertView.findViewById(R.id.txtSatuanInventory);
				viewHolder.txtHargaBeli = (TextView) convertView.findViewById(R.id.txtHargaBeliInvetory);
				viewHolder.txtHargaJual = (TextView) convertView.findViewById(R.id.txtHargaJualInvetory);
				break;
			case 4:
				viewHolder.linearPelanggan.setVisibility(View.VISIBLE);
				break;
			case 5:
				viewHolder.linearDistributor.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		switch (idMenu) {
		case 1:
			break;
		case 2:
			viewHolder.txtKodeBarangPembelian.setText(((PembelianObj) arrObjs.get(position)).getKodeBarang());
			viewHolder.txtSatuanPembelian.setText(((PembelianObj) arrObjs.get(position)).getSatuan());
			viewHolder.txtKodeDistributorPembelian.setText(((PembelianObj) arrObjs.get(position)).getKodeDistributor());
			viewHolder.txtTanggalTransaksi.setText(((PembelianObj) arrObjs.get(position)).getTglTransaksi());
			break;
		case 3:
			viewHolder.txtNamaBarang.setText(((InventoryObj) arrObjs.get(position)).getNamaBarang());
			viewHolder.txtSatuan.setText(((InventoryObj) arrObjs.get(position)).getSatuan());
			viewHolder.txtHargaBeli.setText(((InventoryObj) arrObjs.get(position)).getHargaBeli());
			viewHolder.txtHargaJual.setText(((InventoryObj) arrObjs.get(position)).getHargaJual());
			break;
		case 4:
			break;
		case 5:
			break;
		default:
			break;
		}

		return convertView;
	}
	
	class ViewHolder {
		
		//pembelian
		private TextView txtKodeBarangPembelian;
		private TextView txtSatuanPembelian;
		private TextView txtKodeDistributorPembelian;
		private TextView txtTanggalTransaksi;
		
		//inventory
		private TextView txtNamaBarang;
		private TextView txtSatuan;
		private TextView txtHargaBeli;
		private TextView txtHargaJual;
		
		private LinearLayout linearPenjualan;
		private LinearLayout linearPembelian;
		private LinearLayout linearInventory;
		private LinearLayout linearPelanggan;
		private LinearLayout linearDistributor;
		
	}

}
