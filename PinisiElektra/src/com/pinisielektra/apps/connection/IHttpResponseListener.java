package com.pinisielektra.apps.connection;

public interface IHttpResponseListener {
	public void resultSuccess(int type, String result);
	public void resultFailed(int type, String strError);
}
