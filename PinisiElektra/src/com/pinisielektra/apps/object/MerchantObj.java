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
import android.widget.Toast;

import com.pinisielektra.apps.connection.HttpConnectionTask;
import com.pinisielektra.apps.connection.IHttpResponseListener;
import com.pinisielektra.apps.utils.Constants;
import com.pinisielektra.apps.utils.JsonObjConstant;

public class MerchantObj implements Serializable, IHttpResponseListener, JsonObjConstant{
	private static final long serialVersionUID = 1L;
	private String merchantId;
	private String merchantName;
	private String address;
	private String creator;
	private String dateCreated;
	private String merchantUserId;
	private String savedId;
	private Context context;
	private Set<String> data;
	
	
	public MerchantObj(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(Constants.MY_PREFS_NAME, Context.MODE_PRIVATE);
		savedId = prefs.getString("uId", null);
		this.context = ctx;
	}
	
	public String getMerchantUserId() {
		return merchantUserId;
	}
	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void retrieveKodeMerchant() {
		new HttpConnectionTask((Activity)context, this, 0, "GET").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.API_LIST_MERCHANT+"&creator="+savedId+"");
	}
	
	@Override
	public void resultSuccess(int type, String result) {
		try {
			data = new HashSet<String>();
			JSONObject jObj = new JSONObject(result);
			SharedPreferences.Editor editor = context.getSharedPreferences(Constants.PREF_KODE_MERCHANT, Context.MODE_PRIVATE).edit();
			if (jObj.optString("result").equalsIgnoreCase("1")) {
				JSONArray jArray = jObj.getJSONArray("rows");
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject jObjArr = jArray.getJSONObject(i);
//					data.add(jObjArr.optString(OBJ_MERCHANT_ID));
					data.add(jObjArr.optString(OBJ_MERCHANT_NAME));
				}
				
				if (editor != null) {
					editor.remove("kodemerch");
				}
				editor.putStringSet("kodemerch", data);
				Constants.KODE_MERCHANT_NULL = false;
			}else if (jObj.optString("result").equalsIgnoreCase("0")) {
				if (editor != null) {
					editor.remove("kodemerch");
				}
				editor.putString("kodemerch", "no-records");
				Constants.KODE_MERCHANT_NULL = true;
			}
			
			Log.d("***init ", "merchantObj "+Constants.KODE_MERCHANT_NULL);
			
			editor.commit();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}
	@Override
	public void resultFailed(int type, String strError) {
		Toast.makeText(context, "MerchantObj "+strError, Toast.LENGTH_SHORT).show();
	}
}