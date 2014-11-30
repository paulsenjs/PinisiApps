package com.pinisielektra.apps.utils;

public class Constants {
	public static final String API_LOGIN = "http://cpos.pinisi-elektra.com/api/user.php?cmd=login&";
	public static final String API_REGISTER = "http://cpos.pinisi-elektra.com/api/user.php?cmd=add&";
	
	//pembelian
	public static final String API_POST_PEMBELIAN = "http://cpos.pinisi-elektra.com/api/pembelian.php";
	public static final String API_LIST_PEMBELIAN = "http://cpos.pinisi-elektra.com/api/pembelian.php?cmd=list";
	public static final String API_DEL_LIST_PEMBELIAN = "http://cpos.pinisi-elektra.com/api/pembelian.php?cmd=list";
	
	//inventory
	public static final String API_POST_INVENTORY = "http://cpos.pinisi-elektra.com/api/barang.php";
	public static final String API_LIST_INVENTORY = "http://cpos.pinisi-elektra.com/api/barang.php?cmd=list";
	public static final String API_DEL_LIST_INVENTORY = "http://cpos.pinisi-elektra.com/api/barang.php?cmd=delete";
}
