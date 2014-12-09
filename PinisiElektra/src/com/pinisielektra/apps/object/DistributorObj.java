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
//	private ArrayList<Object> arrObj;
	private Set<String> data;
	
	public DistributorObj(Context ctx) {
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
		new HttpConnectionTask((Activity)context, this, 0, "GET").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.API_LIST_DISTRIBUTOR);
	}
	
	@Override
	public void resultSuccess(int type, String result) {
		try {
//			arrObj = new ArrayList<Object>();
			data = new HashSet<String>();
			JSONObject jObj = new JSONObject(result);
			JSONArray jArray = jObj.getJSONArray("rows");
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jObjArr = jArray.getJSONObject(i);
				data.add(jObjArr.optString(OBJ_KODE_DISTRIBUTOR));
//				arrObj.add(i, jObjArr.optString(OBJ_KODE_DISTRIBUTOR));
			}
			
			SharedPreferences.Editor editor = context.getSharedPreferences(Constants.PREF_KODE_DISTRIBUTOR, Context.MODE_PRIVATE).edit();
			editor.putStringSet("kodedist", data);
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
