package com.pinisielektra.apps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pinisielektra.apps.connection.HttpConnectionTask;
import com.pinisielektra.apps.connection.IHttpResponseListener;
import com.pinisielektra.apps.utils.Constants;

public class MainActivity extends Activity implements IHttpResponseListener {

	private Handler handler;
	private LinearLayout linearButtonLogin, linearImageLogo;
	private String userName = "Jhon Doe";
	private String userId;
	private final String MY_PREFS_NAME = "pinisiEl_sharePref";
	private EditText edtUserName;
	private EditText edtPassword;
	private TextView txtLogin;
	private ProgressDialog mProgressDialog;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        linearButtonLogin = (LinearLayout) findViewById(R.id.linearButton);
        linearImageLogo = (LinearLayout) findViewById(R.id.linearLogo);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        txtLogin = (TextView) findViewById(R.id.text_login);
        
        mProgressDialog = new ProgressDialog(this);
        
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				goToDashboardScreen();
			}
		};
        
		SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE); 
		String restoredText = prefs.getString("uId", null);
		if (restoredText != null) {
			linearButtonLogin.setVisibility(View.GONE);
			linearImageLogo.setVisibility(View.VISIBLE);
			userName = prefs.getString("uName", "No name defined");
			new SplashLauncher().start();
		}else{
			linearButtonLogin.setVisibility(View.VISIBLE);
			linearImageLogo.setVisibility(View.GONE);
		}

		txtLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				runLoginChecker();
			}
		});
    }

    private void goToDashboardScreen() {
    	finish();
		startActivity(new Intent().setClass(this, DashboardActivity.class).putExtra("uName", userName));
    }

    private void runLoginChecker() {
		mProgressDialog.show();
    	new HttpConnectionTask(this, this, 0, "GET").execute(Constants.API_LOGIN+"userid="+edtUserName.getText().toString()+"&password="+edtPassword.getText().toString()+"");
    }
    
    class SplashLauncher extends Thread {
		public void run() {
			try {
				Thread.sleep(2000);
				Message msg = new Message();
				String textTochange = "OK";
				msg.obj = textTochange;
				handler.sendMessage(msg);
			} catch (Exception e) {
				Log.e(getClass().getName(), e.getMessage());
			}
		}
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void resultSuccess(int type, String result) {
		Toast.makeText(this, "Welcome ...", Toast.LENGTH_SHORT).show();
		try {
			JSONObject jObj = new JSONObject(result);
			if (jObj.optString("result").equalsIgnoreCase("1")) {
				JSONArray jArray = jObj.getJSONArray("rows");
				for (int i=0; i<jArray.length(); i++) {
					JSONObject jObjArr = jArray.getJSONObject(i);
					userId = jObjArr.optString("userid");
					userName = jObjArr.optString("name");
				}
				
				SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
				editor.putString("uId", userId);
				editor.putString("uName", userName);
				editor.commit();
				
				new SplashLauncher().start();
			}else {
				Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}

	@Override
	public void resultFailed(int type, String strError) {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
		Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
		Log.d("MainActivity", "result failed " + strError);
	}
}
