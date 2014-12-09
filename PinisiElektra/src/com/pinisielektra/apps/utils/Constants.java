package com.pinisielektra.apps.utils;

public class Constants {
	//user
	public static final String API_LOGIN = "http://cpos.pinisi-elektra.com/api/user.php?cmd=login&";
	public static final String API_REGISTER = "http://cpos.pinisi-elektra.com/api/user.php?cmd=add&";
	public static final String MY_PREFS_NAME = "pinisiEl_sharePref";
	public static final String PREF_KODE_DISTRIBUTOR = "kodedistributor";
	public static final String PREF_KODE_BARANG = "kodebarang";
	
	//penjualan
	public static final String API_POST_PENJUALAN = "http://cpos.pinisi-elektra.com/api/penjualan.php";
	public static final String API_LIST_PENJUALAN = "http://cpos.pinisi-elektra.com/api/penjualan.php?cmd=list";
	
	//pembelian
	public static final String API_POST_PEMBELIAN = "http://cpos.pinisi-elektra.com/api/pembelian.php";
	public static final String API_LIST_PEMBELIAN = "http://cpos.pinisi-elektra.com/api/pembelian.php?cmd=list";
	
	//inventory
	public static final String API_POST_INVENTORY = "http://cpos.pinisi-elektra.com/api/barang.php";
	public static final String API_LIST_INVENTORY = "http://cpos.pinisi-elektra.com/api/barang.php?cmd=list";
	
	//pelanggan
	public static final String API_POST_PELANGGAN = "http://cpos.pinisi-elektra.com/api/pelanggan.php";
	public static final String API_LIST_PELANGGAN = "http://cpos.pinisi-elektra.com/api/pelanggan.php?cmd=list";
	
	//distributor
	public static final String API_POST_DISTRIBUTOR = "http://cpos.pinisi-elektra.com/api/distributor.php";
	public static final String API_LIST_DISTRIBUTOR = "http://cpos.pinisi-elektra.com/api/distributor.php?cmd=list";

	//merchant
	public static final String API_POST_MERCHANT = "http://cpos.pinisi-elektra.com/api/merchant.php";
	public static final String API_LIST_MERCHANT = "http://cpos.pinisi-elektra.com/api/merchant.php?cmd=list";
}
