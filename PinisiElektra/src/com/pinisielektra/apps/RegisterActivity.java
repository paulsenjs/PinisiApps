package com.pinisielektra.apps;

import org.json.JSONException;
import org.json.JSONObject;

import com.pinisielektra.apps.connection.HttpConnectionTask;
import com.pinisielektra.apps.connection.IHttpResponseListener;
import com.pinisielektra.apps.utils.Constants;

import android.app.Activity;
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
				new HttpConnectionTask(RegisterActivity.this, RegisterActivity.this, 0, "GET").execute(Constants.API_REGISTER+"userid="+edtUserName.getText().toString()+"&password="+edtPassword.getText().toString()+"&name="+edtName.getText().toString()+"");
			}
		});
	}

	@Override
	public void resultSuccess(int type, String result) {
		JSONObject jObj;
		try {
			jObj = new JSONObject(result);
			if (jObj.optString("result") == "1") {
				Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void resultFailed(int type, String strError) {
		
	}
}
