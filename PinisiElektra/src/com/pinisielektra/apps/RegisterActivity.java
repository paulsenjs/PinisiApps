package com.pinisielektra.apps;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.pinisielektra.apps.connection.HttpConnectionTask;
import com.pinisielektra.apps.connection.IHttpResponseListener;
import com.pinisielektra.apps.utils.Constants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements IHttpResponseListener{

	private Button btnRegister;
	private EditText edtUserName;
	private EditText edtName;
	private EditText edtPassword;
	private ProgressDialog mProgress;
	private String strUserName, strName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
	
		edtUserName = (EditText) findViewById(R.id.edtUserName);
		edtName = (EditText) findViewById(R.id.edtName);
		edtPassword = (EditText) findViewById(R.id.edtPassword);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		
		
		btnRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				mProgress = new ProgressDialog(RegisterActivity.this);
				mProgress.setMessage("Load data ...");
				mProgress.setTitle("Loading");
				mProgress.show();

				try {
					strUserName = URLEncoder.encode(edtUserName.getText().toString(), "UTF-8");
					strName = URLEncoder.encode(edtName.getText().toString(), "UTF-8");			
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				new HttpConnectionTask(RegisterActivity.this, RegisterActivity.this, 0, "GET").execute(Constants.API_REGISTER+"userid="+strUserName+"&password="+edtPassword.getText().toString()+"&name="+strName+"");
			}
		});
	}

	@Override
	public void resultSuccess(int type, String result) {
		if (mProgress != null)
			mProgress.dismiss();
		
		JSONObject jObj;
		try {
			jObj = new JSONObject(result);
			if (jObj.optString("result").equalsIgnoreCase("1")) {
				Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
				finish();
			}else{
				Toast.makeText(this, "User already exist", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void resultFailed(int type, String strError) {
		if (mProgress != null)
			mProgress.dismiss();
		
		Toast.makeText(this, "Error :"+strError, Toast.LENGTH_SHORT).show();
	}
}
