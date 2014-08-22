package com.getproductinfo.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.getproductinfo.model.Constants;
import com.getproductinfo.model.Store;
import com.getproductinfo.model.StoresList;
import com.getproductinfo.session.AppSession;
import com.getproductinfo.utils.GetStore;
import com.getproductinfo.utils.GetStoreTask;
import com.getproductinfo.utils.Utils;

public class AppLaunchActivity extends Activity {

	private String outletId;
	private String storeWebServiceIp;
	private boolean isInitialSetup = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_app_launch);

		outletId = Utils.getStringPreferences(this, Constants.KEY_STORE_ID);
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
		
		dLog("outletId : "+outletId);
		dLog("storeWebServiceIp : "+storeWebServiceIp);
		
		if (outletId.equals("") || storeWebServiceIp.equals("")) {
			isInitialSetup = true;
			new GetStoreTask().execute();
		} else {

			isInitialSetup = false;
			new GetStoreTask().execute();

			intent = new Intent(AppLaunchActivity.this,
					SelectFlowActivity.class);
			startActivity(intent);
			finish();
		}

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

	private class GetStoreTask extends AsyncTask<Void, Void, List<Store>> {

		@Override
		protected List<Store> doInBackground(Void... params) {
			List<Store> storesList = null;
			AppSession appSession = AppSession.getInstance();
			if (appSession.getStoresList() == null) {
				storesList = GetStore.parseJson();
				appSession.setStoresList(storesList);
			}else{
				storesList = appSession.getStoresList();
			}
			return storesList;
		}

		@Override
		protected void onPostExecute(List<Store> storesList) {
			super.onPostExecute(storesList);

			if (isInitialSetup) {
				Intent intent = new Intent(AppLaunchActivity.this,
						SelectStoreActivity.class);
				intent.putExtra(
						Constants.KEY_IS_COMING_FROM_APP_LAUNCH_ACTIVITY, true);
				startActivity(intent);
				finish();

			}
			
			// Parcelable StoresList
			//StoresList stores = new StoresList();

			// populate normal store list into parcelable store list
			//for (Store store : storesList) {
			//	stores.add(store);
			//}
			//Bundle b = new Bundle();
			//b.putParcelable("stores", stores); // Insert list in a Bundle object
			//intent.putExtras(b); // Insert the Bundle object in the Intent'
									// Extras

			/*
			 * String storesListAsString = ""; for (Store store : storesList) {
			 * storesListAsString = storesListAsString + store.getActive() + ","
			 * + store.getName() + "," + store.getNumber() + "," +
			 * store.getWwsEndpoint() + ","; }
			 * intent.putExtra(Constants.KEY_STORE_LIST, storesListAsString);
			 */

			 // Start Activity
		}

	}
	
	private void dLog(String message) {
		Log.d(AppLaunchActivity.this.getLocalClassName(), message);
	}

	private void eLog(String message) {
		Log.e(AppLaunchActivity.this.getLocalClassName(), message);
	}

}
