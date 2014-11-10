package com.pinisielektra.apps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private Handler handler;
	private LinearLayout linearButtonLogin;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        linearButtonLogin = (LinearLayout) findViewById(R.id.linear_button);
        
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				goToDashboardScreen();
			}

		};
        
		if (isAlreadyLogin()) {
			linearButtonLogin.setVisibility(View.GONE);
			new SplashLauncher().start();		
		}else{
			linearButtonLogin.setVisibility(View.VISIBLE);
		}
		
    }

    private void goToDashboardScreen() {
    	finish();
		startActivity(new Intent().setClass(this, DashboardActivity.class));
    }

    private boolean isAlreadyLogin() {
    	return true;
    }
    
    class SplashLauncher extends Thread {
		public void run() {
			try {
				Thread.sleep(3000);
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
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
