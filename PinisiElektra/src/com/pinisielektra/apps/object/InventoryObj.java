package com.pinisielektra.apps.object;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.pinisielektra.apps.connection.HttpConnectionTask;
import com.pinisielektra.apps.connection.IHttpResponseListener;
import com.pinisielektra.apps.utils.Constants;
import com.pinisielektra.apps.utils.JsonObjConstant;

public class InventoryObj implements Serializable, IHttpResponseListener, JsonObjConstant{
	private static final long serialVersionUID = 1L;
	private String kodeBarang;
	private String catId;
	private String namaBarang;
	private String satuan;
	private String hargaJual;
	private String hargaBeli;
	private String expDate;
	private String creator;
	private String dateCreated;
	private String editor;
	private String dateEdited;
	private Context context;
	private Set<String> data;
	private String savedId;
	
	public InventoryObj(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(Constants.MY_PREFS_NAME, Context.MODE_PRIVATE);
		savedId = prefs.getString("uId", null);
		this.context = ctx;
	}
	
	public String getKodeBarang() {
		return kodeBarang;
	}
	public void setKodeBarang(String kodeBarang) {
		this.kodeBarang = kodeBarang;
	}
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public String getNamaBarang() {
		return namaBarang;
	}
	public void setNamaBarang(String namaBarang) {
		this.namaBarang = namaBarang;
	}
	public String getSatuan() {
		return satuan;
	}
	public void setSatuan(String satuan) {
		this.satuan = satuan;
	}
	public String getHargaJual() {
		return hargaJual;
	}
	public void setHargaJual(String hargaJual) {
		this.hargaJual = hargaJual;
	}
	public String getHargaBeli() {
		return hargaBeli;
	}
	public void setHargaBeli(String hargaBeli) {
		this.hargaBeli = hargaBeli;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public String getDateEdited() {
		return dateEdited;
	}
	public void setDateEdited(String dateEdited) {
		this.dateEdited = dateEdited;
	}
	
	public void retrieveKodeBarang() {
		new HttpConnectionTask((Activity)context, this, 1, "GET").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.API_LIST_INVENTORY+"&creator="+savedId+"");
	}
	
	
	@Override
	public void resultSuccess(int type, String result) {
		try {
			data = new HashSet<String>();
			JSONObject jObj = new JSONObject(result);
			SharedPreferences.Editor editor = context.getSharedPreferences(Constants.PREF_KODE_BARANG, Context.MODE_PRIVATE).edit();
			if (jObj.optString("result").equalsIgnoreCase("1")) {
				JSONArray jArray = jObj.getJSONArray("rows");
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject jObjArr = jArray.getJSONObject(i);
//					data.add(jObjArr.optString(OBJ_KODE_BARANG));
					data.add(jObjArr.optString(OBJ_NAMA_BARANG));
				}				
				
				if (editor != null) {
					editor.remove("kodebrg");
				}
				editor.putStringSet("kodebrg", data);
				
				Constants.KODE_BARANG_NULL = false;
				
			}else if (jObj.optString("result").equalsIgnoreCase("0")) {
				if (editor != null) {
					editor.remove("kodebrg");
				}
				editor.putString("kodebrg", "no-records");
				Constants.KODE_BARANG_NULL = true;
			}
			
			Log.d("***init ", "inventoryObj "+Constants.KODE_BARANG_NULL);
			
			editor.commit();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void resultFailed(int type, String strError) {
		// TODO Auto-generated method stub
		
	}
}
