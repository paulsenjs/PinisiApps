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

public class DistributorObj implements Serializable, IHttpResponseListener, JsonObjConstant{
	private static final long serialVersionUID = 1L;
	private String kodeDistributor;
	private String nama;
	private String creator;
	private String dateCreated;
	private String editor;
	private String dateEdited;
	private Context context;
	private Set<String> data;
	private String savedId;
	
	public DistributorObj(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(Constants.MY_PREFS_NAME, Context.MODE_PRIVATE);
		savedId = prefs.getString("uId", null);
		this.context = ctx;
	}
	
	public String getKodeDistributor() {
		return kodeDistributor;
	}
	public void setKodeDistributor(String kodeDistributor) {
		this.kodeDistributor = kodeDistributor;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
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
	
	public void retrieveKodeDistributor() {
		new HttpConnectionTask((Activity)context, this, 0, "GET").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.API_LIST_DISTRIBUTOR+"&creator="+savedId+"");
	}
	
	@Override
	public void resultSuccess(int type, String result) {
		try {
			data = new HashSet<String>();
			JSONObject jObj = new JSONObject(result);
			SharedPreferences.Editor editor = context.getSharedPreferences(Constants.PREF_KODE_DISTRIBUTOR, Context.MODE_PRIVATE).edit();
			if (jObj.optString("result").equalsIgnoreCase("1")) {
				JSONArray jArray = jObj.getJSONArray("rows");
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject jObjArr = jArray.getJSONObject(i);
//					data.add(jObjArr.optString(OBJ_KODE_DISTRIBUTOR));
					data.add(jObjArr.optString(OBJ_NAMA));
				}				
				
				if (editor != null) {
					editor.remove("kodedist");
				}
				Constants.KODE_DIST_NULL = false;
				editor.putStringSet("kodedist", data);
			}else if (jObj.optString("result").equalsIgnoreCase("0")) {
				if (editor != null) {
					editor.remove("kodedist");
				}
				editor.putString("kodedist", "no-records");
				Constants.KODE_DIST_NULL = true;
			}
			
			Log.d("***init ", "distributorObj "+Constants.KODE_DIST_NULL);
			
			editor.commit();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void resultFailed(int type, String strError) {
		Toast.makeText(context, "DistributorObj "+strError, Toast.LENGTH_SHORT).show();
	}
}
