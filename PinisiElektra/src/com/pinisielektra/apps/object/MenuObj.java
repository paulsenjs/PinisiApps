package com.pinisielektra.apps.object;

import android.support.v7.app.ActionBarActivity;

public class MenuObj extends ActionBarActivity{
	
	private boolean menuPenjualan;
	private boolean menuPembelian;
	private boolean menuInventory;
	private boolean menuPelanggan;
	private boolean menuDistributor;
	private boolean menuMerchant;
	
	protected static final int ID_PENJUALAN = 1;
	protected static final int ID_PEMBELIAN = 2;
	protected static final int ID_INVENTORY = 3;
	protected static final int ID_PELANGGAN = 4;
	protected static final int ID_DISTRIBUTOR = 5;
	protected static final int ID_MERCHANT = 8;
	
	protected static final int ID_KODE_DISTRIBUTOR = 6;
	protected static final int ID_KODE_BARANG = 7;
	
	protected boolean isMenuPenjualan() {
		return menuPenjualan;
	}
	protected void setMenuPenjualan(boolean menuPenjualan) {
		this.menuPenjualan = menuPenjualan;
	}
	protected boolean isMenuPembelian() {
		return menuPembelian;
	}
	protected void setMenuPembelian(boolean menuPembelian) {
		this.menuPembelian = menuPembelian;
	}
	protected boolean isMenuInventory() {
		return menuInventory;
	}
	protected void setMenuInventory(boolean menuInventory) {
		this.menuInventory = menuInventory;
	}
	protected boolean isMenuPelanggan() {
		return menuPelanggan;
	}
	protected void setMenuPelanggan(boolean menuPelanggan) {
		this.menuPelanggan = menuPelanggan;
	}
	protected boolean isMenuDistributor() {
		return menuDistributor;
	}
	protected void setMenuDistributor(boolean menuDistributor) {
		this.menuDistributor = menuDistributor;
	}
	protected boolean isMenuMerchant() {
		return menuMerchant;
	}
	protected void setMenuMerchant(boolean menuMerchant) {
		this.menuMerchant = menuMerchant;
	}
}
