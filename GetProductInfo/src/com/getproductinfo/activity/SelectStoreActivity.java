package com.getproductinfo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.getproductinfo.model.Constants;
import com.getproductinfo.model.Store;
import com.getproductinfo.utils.GetStoreTask;
import com.getproductinfo.utils.Utils;
import com.github.barcodeeye.scan.CaptureQRCodeActivity;
import com.google.android.glass.app.Card;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class SelectStoreActivity extends Activity {

	private GestureDetector mGestureDetector;
	private AudioManager mAudioManager;
	private boolean isComingFromAppLaunchActivity = false;

	private List<Card> mCards;
	private CardScrollView mCardScrollView;

	private List<Store> storesList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Call web service and display the list of available outlets
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mGestureDetector = createGestureDetector(this);

		new GetStoreTask(SelectStoreActivity.this).execute();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
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

	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
			if (storesList != null) {
				int i = 1;
				for (Store store : storesList) {
					menu.addSubMenu(0, i, Menu.NONE, store.getName());
					i++;
				}
			}
			return true;
		}
		// Pass through to super to setup touch menu.
		return super.onCreatePanelMenu(featureId, menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
			if (item.getTitle() != null
					|| "".equalsIgnoreCase(item.getTitle().toString())) {

				Utils.saveStringPreferences(this, Constants.KEY_STORE_NAME,
						item.getTitle().toString());

				Bundle bundle = getIntent().getExtras();
				if (bundle != null) {
					isComingFromAppLaunchActivity = bundle
							.getBoolean(Constants.KEY_IS_COMING_FROM_APP_LAUNCH_ACTIVITY);
				}
				Intent intent = null;
				if (isComingFromAppLaunchActivity) {
					intent = new Intent(SelectStoreActivity.this,
							CaptureQRCodeActivity.class);
				} else {
					intent = new Intent(SelectStoreActivity.this,
							SelectFlowActivity.class);
				}
				startActivity(intent);
				finish();
			}

			return true;
		}
		// Good practice to pass through to super if not handled
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		if (storesList != null) {
			int i = 1;
			for (Store store : storesList) {
				menu.addSubMenu(0, i, Menu.NONE, store.getName());
				i++;
			}
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		mAudioManager.playSoundEffect(Sounds.TAP);

		if (item.getTitle() != null
				|| "".equalsIgnoreCase(item.getTitle().toString())) {

			Utils.saveStringPreferences(this, Constants.KEY_STORE_NAME, item
					.getTitle().toString());

			Bundle bundle = getIntent().getExtras();
			if (bundle != null) {
				isComingFromAppLaunchActivity = bundle
						.getBoolean(Constants.KEY_IS_COMING_FROM_APP_LAUNCH_ACTIVITY);
			}
			Intent intent = null;
			if (isComingFromAppLaunchActivity) {
				intent = new Intent(SelectStoreActivity.this,
						CaptureQRCodeActivity.class);
			} else {
				intent = new Intent(SelectStoreActivity.this,
						SelectFlowActivity.class);
			}
			startActivity(intent);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * Send generic motion events to the gesture detector
	 */
	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		if (mGestureDetector != null) {
			return mGestureDetector.onMotionEvent(event);
		}
		return false;
	}

	private GestureDetector createGestureDetector(final Context context) {

		GestureDetector gestureDetector = new GestureDetector(context);
		// Create a base listener for generic gestures
		gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
			@Override
			public boolean onGesture(Gesture gesture) {
				if (gesture == Gesture.TAP) {
					dLog("Gesture.TAP");
					mAudioManager.playSoundEffect(Sounds.TAP);
					openOptionsMenu();
					return true;
				} else if (gesture == Gesture.SWIPE_DOWN) {
					dLog("Gesture.SWIPE_DOWN");
					mAudioManager.playSoundEffect(Sounds.DISMISSED);
					return false;
				}
				return false;
			}
		});

		gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
			@Override
			public void onFingerCountChanged(int previousCount, int currentCount) {
				// do something on finger count changes
			}
		});

		gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
			@Override
			public boolean onScroll(float displacement, float delta,
					float velocity) {
				// do something on scrolling
				return true;
			}
		});
		return gestureDetector;

	}

	public void onCompleteGetStoreTask(List<Store> storesList) {
		eLog("productInfo" + storesList);

		this.storesList = storesList;

		Card card;
		if (storesList != null) {
			mCards = new ArrayList<Card>();
			for (Store store : storesList) {
				card = new Card(this);
				card.setText(store.getName());
				mCards.add(card);

			}
			mCardScrollView = new CardScrollView(this);
			SelectStoreCardScrollAdapter adapter = new SelectStoreCardScrollAdapter();
			mCardScrollView.setAdapter(adapter);
			mCardScrollView.activate();
			setContentView(mCardScrollView);
			Toast.makeText(this, "Scroll left/right to explore more",
					Toast.LENGTH_SHORT).show();
		} else {
			card = new Card(this);
			card.setText("There is no available store.");
			View cardView = card.getView();
			setContentView(cardView);
			dLog("Getting empty response");
		}

		/**
		 * Display list of stores and once the user select any store save it in
		 * the SharedPreferences. and run the following code.
		 * 
		 * Bundle bundle = getIntent().getExtras(); if (bundle != null) {
		 * isComingFromAppLaunchActivity = bundle.getBoolean(Constants.
		 * KEY_IS_COMING_FROM_APP_LAUNCH_ACTIVITY); } Intent intent = null;
		 * 
		 * if (isComingFromAppLaunchActivity) { intent = new
		 * Intent(SelectStoreActivity.this,CaptureQRCodeActivity.class); } else
		 * { intent = new Intent(SelectStoreActivity.this,
		 * SelectFlowActivity.class); } startActivity(intent); finish();
		 */
	}

	private void dLog(String message) {
		Log.d(SelectStoreActivity.this.getLocalClassName(), message);
	}

	private void eLog(String message) {
		Log.e(SelectStoreActivity.this.getLocalClassName(), message);
	}

	private class SelectStoreCardScrollAdapter extends CardScrollAdapter {

		@Override
		public int getPosition(Object item) {
			return mCards.indexOf(item);
		}

		@Override
		public int getCount() {
			return mCards.size();
		}

		@Override
		public Object getItem(int position) {
			return mCards.get(position);
		}

		@Override
		public int getViewTypeCount() {
			return Card.getViewTypeCount();
		}

		@Override
		public int getItemViewType(int position) {
			return mCards.get(position).getItemViewType();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return mCards.get(position).getView(convertView, parent);
		}
	}

}
