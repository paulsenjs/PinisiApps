package com.pinisielektra.apps;

import android.os.Bundle;
import android.widget.TextView;

import com.pinisielektra.apps.object.MenuObj;

public class DetailActivity extends MenuObj {

	private TextView txtDetail;
	private String[] menuIntent;
	private StringBuffer strBuffer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		txtDetail = (TextView) findViewById(R.id.txtDetail);
		menuIntent = getIntent().getStringArrayExtra("detail");
		
		if (menuIntent != null) {
			if (menuIntent[0].equalsIgnoreCase("detail_pembelian")) {
				setMenuPembelian(true);
				strBuffer = new StringBuffer();
				strBuffer.append("Id Transaksi : "+ menuIntent[1] +
						"\nKode Distributor : " + menuIntent[2] +
						"\nKode Barang : " + menuIntent[3] +
						"\nTgl Transaksi : " + menuIntent[4] +
						"\nSatuan : " + menuIntent[5] +
						"\nCreator : " + menuIntent[6] + 
						"\nDate Created : " + menuIntent[7] +
						"\nEditor : " + menuIntent[8] +
						"\nDate Edited : " + menuIntent[9]);
			} else if (menuIntent[0].equalsIgnoreCase("detail_penjualan")) {
				setMenuPenjualan(true);
				strBuffer = new StringBuffer();
				strBuffer.append("Id Penjualan : "+ menuIntent[1] +
						"\nKode Barang : " + menuIntent[2] +
						"\nTgl Transaksi : " + menuIntent[3] +
						"\nSatuan : " + menuIntent[4] +
						"\nCreator : " + menuIntent[5] + 
						"\nDate Created : " + menuIntent[6] +
						"\nEditor : " + menuIntent[7] +
						"\nDate Edited : " + menuIntent[8]);
			} else if (menuIntent[0].equalsIgnoreCase("detail_pelanggan")) {
				setMenuPelanggan(true);
				strBuffer = new StringBuffer();
				strBuffer.append("Id Pelanggan : "+ menuIntent[1] +
						"\nNama : " + menuIntent[2] +
						"\nAlamat : " + menuIntent[3] +
						"\nPhone : " + menuIntent[4] +
						"\nCreator : " + menuIntent[5] + 
						"\nDate Created : " + menuIntent[6]);
			} else if (menuIntent[0].equalsIgnoreCase("detail_distributor")) {
				setMenuDistributor(true);
				strBuffer = new StringBuffer();
				strBuffer.append("Kode Distributor : "+ menuIntent[1] +
						"\nNama : " + menuIntent[2] +
						"\nCreator : " + menuIntent[3] + 
						"\nDate Created : " + menuIntent[4] +
						"\nEditor : " + menuIntent[5] +
						"\nDate Edited : " + menuIntent[6]);
			} else if (menuIntent[0].equalsIgnoreCase("detail_inventory")) {
				setMenuInventory(true);
				strBuffer = new StringBuffer();
				strBuffer.append("Kode Barang : "+ menuIntent[1] +
						"\nCategori Id : " + menuIntent[2] +
						"\nNama Barang : " + menuIntent[3] +
						"\nSatuan : " + menuIntent[4] +
						"\nHarga Jual : " + menuIntent[5] +
						"\nHarga Beli : " + menuIntent[6] +
						"\nExp Date : " + menuIntent[7] +
						"\nCreator : " + menuIntent[8] + 
						"\nDate Created : " + menuIntent[9] +
						"\nEditor : " + menuIntent[10] +
						"\nDate Edited : " + menuIntent[11]);
			}
		}
		
		txtDetail.setText(strBuffer);
	}
}
