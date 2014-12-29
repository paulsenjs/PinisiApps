package com.pinisielektra.apps.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.pinisielektra.apps.R;
import com.pinisielektra.apps.object.DistributorObj;
import com.pinisielektra.apps.object.InventoryObj;
import com.pinisielektra.apps.object.MerchantObj;
import com.pinisielektra.apps.object.PelangganObj;
import com.pinisielektra.apps.object.PembelianObj;
import com.pinisielektra.apps.object.PenjualanObj;

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
			viewHolder.linearMerchant = (LinearLayout) convertView.findViewById(R.id.linearMerchant);
			
			switch (idMenu) {
			case 1:
				viewHolder.linearPenjualan.setVisibility(View.VISIBLE);
				viewHolder.txtIdPenjualan = (TextView) convertView.findViewById(R.id.txtIdPenjualan);
				viewHolder.txtKodeBarangPenjualan = (TextView) convertView.findViewById(R.id.txtKodeBarangPenjualan);
				viewHolder.txtTglTransaksiPenjualan = (TextView) convertView.findViewById(R.id.txtTglTransaksiPenjualan);
				viewHolder.txtSatuanPenjualan = (TextView) convertView.findViewById(R.id.txtSatuanPenjualan);
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
				viewHolder.txtIdPelanggan = (TextView) convertView.findViewById(R.id.txtIdPelanggan);
				viewHolder.txtNamaPelanggan = (TextView) convertView.findViewById(R.id.txtNamaPelanggan);
				viewHolder.txtAlamatPelanggan = (TextView) convertView.findViewById(R.id.txtAlamatPelanggan);
				viewHolder.txtPhonePelanggan = (TextView) convertView.findViewById(R.id.txtPhonePelanggan);
				break;
			case 5:
				viewHolder.linearDistributor.setVisibility(View.VISIBLE);
				viewHolder.txtNamaDistributor = (TextView) convertView.findViewById(R.id.txtNamaDistributor);
				viewHolder.txtKodeDistributor = (TextView) convertView.findViewById(R.id.txtKodeDistributor);
				break;
			case 8:
				viewHolder.linearMerchant.setVisibility(View.VISIBLE);
				viewHolder.txtMerchantId = (TextView) convertView.findViewById(R.id.txtMerchantId);
				viewHolder.txtMerchantName = (TextView) convertView.findViewById(R.id.txtMerchantName);
				viewHolder.txtMerchantAddr = (TextView) convertView.findViewById(R.id.txtMerchantAddr);
			default:
				break;
			}
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		switch (idMenu) {
		case 1:
			viewHolder.txtIdPenjualan.setText(((PenjualanObj) arrObjs.get(position)).getIdJual());
			viewHolder.txtKodeBarangPenjualan.setText(((PenjualanObj) arrObjs.get(position)).getKodeBarang());
			viewHolder.txtTglTransaksiPenjualan.setText(((PenjualanObj) arrObjs.get(position)).getTglTransaksi());
			viewHolder.txtSatuanPenjualan.setText(((PenjualanObj) arrObjs.get(position)).getSatuan());
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
			viewHolder.txtIdPelanggan.setText(((PelangganObj) arrObjs.get(position)).getIdPel());
			viewHolder.txtNamaPelanggan.setText(((PelangganObj) arrObjs.get(position)).getNama());
			viewHolder.txtAlamatPelanggan.setText(((PelangganObj) arrObjs.get(position)).getAlamat());
			viewHolder.txtPhonePelanggan.setText(((PelangganObj) arrObjs.get(position)).getPhone());
			break;
		case 5:
			viewHolder.txtKodeDistributor.setText(((DistributorObj) arrObjs.get(position)).getKodeDistributor());
			viewHolder.txtNamaDistributor.setText(((DistributorObj) arrObjs.get(position)).getNama());
			break;
		case 8:
			viewHolder.txtMerchantId.setText(((MerchantObj) arrObjs.get(position)).getMerchantId());
			viewHolder.txtMerchantName.setText(((MerchantObj) arrObjs.get(position)).getMerchantName());
			viewHolder.txtMerchantAddr.setText(((MerchantObj) arrObjs.get(position)).getAddress());
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
		private LinearLayout linearMerchant;
		
		//pelanggan
		private TextView txtIdPelanggan;
		private TextView txtNamaPelanggan;
		private TextView txtAlamatPelanggan;
		private TextView txtPhonePelanggan;
		
		//distributor
		private TextView txtNamaDistributor;
		private TextView txtKodeDistributor;
		
		//penjualan
		private TextView txtIdPenjualan;
		private TextView txtKodeBarangPenjualan;
		private TextView txtTglTransaksiPenjualan;
		private TextView txtSatuanPenjualan;
	
		//merchant
		private TextView txtMerchantId;
		private TextView txtMerchantName;
		private TextView txtMerchantAddr;
	}

}
