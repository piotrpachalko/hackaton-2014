package com.mpmp.freya.mobile;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

public class GetAuthToken extends AsyncTask<Void, Void, String> {

	private MainActivity mActivity;
	private String mEmail;

	public GetAuthToken(MainActivity mActivity, String mEmail) {
		this.mActivity = mActivity;
		this.mEmail = mEmail;
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			Log.i("MainActivity", mEmail);
			String token = GoogleAuthUtil.getToken(mActivity, mEmail,
					"oauth2:https://www.googleapis.com/auth/userinfo.profile");
			Log.i("MainActivity", token);
			return token;

		} catch (UserRecoverableAuthException userRecoverableException) {
			Log.wtf(userRecoverableException.getMessage(), userRecoverableException);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		//if (result != null)
			//mActivity.setToken(result);
	}

}
