package com.getproductinfo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.getproductinfo.model.Constants;
import com.getproductinfo.utils.Utils;

public class AppLaunchActivity extends Activity {

	String outletId;
	String storeWebServiceIp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		outletId = Utils.getStringPreferences(this, Constants.KEY_OUTLET_ID);
		storeWebServiceIp = Utils.getStringPreferences(this,
				Constants.KEY_STORE_WEB_SERVICE_IP);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Intent intent = null;
		if (outletId.equals("") || storeWebServiceIp.equals("")) {
			intent = new Intent(AppLaunchActivity.this,
					SelectStoreActivity.class);
			intent.putExtra(Constants.KEY_IS_COMING_FROM_APP_LAUNCH_ACTIVITY, true);
		} else {
			intent = new Intent(AppLaunchActivity.this,
					SelectFlowActivity.class);
		}
		startActivity(intent);
		finish();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
