package com.mpmp.freya.mobile;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

public class MainActivity extends AbstractActivity implements OnDismissCallback {

	private FreyaExpandableListItemAdapter mExpandableListItemAdapter;
	private ItemsProvider itemsProvider;
	private LocationManager locationManager;
	private String provider;
	private FreyaLocationListener locationListener;
	private Location location;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initLocationManager();

		itemsProvider = new ItemsProvider(this);
		itemsProvider.startFetchingData();

		mExpandableListItemAdapter = new FreyaExpandableListItemAdapter(this,
				getItems());

		SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
				new SwipeDismissAdapter(mExpandableListItemAdapter, this));
		swingBottomInAnimationAdapter.setInitialDelayMillis(300);
		swingBottomInAnimationAdapter.setAbsListView(getListView());

		getListView().setAdapter(swingBottomInAnimationAdapter);

	}

	private static class FreyaExpandableListItemAdapter extends
			ExpandableListItemAdapter<Integer> {

		private final Context mContext;
		private final LruCache<Integer, Bitmap> mMemoryCache;

		/**
		 * Creates a new ExpandableListItemAdapter with the specified list, or
		 * an empty list if items == null.
		 */
		private FreyaExpandableListItemAdapter(final Context context,
				final List<Integer> items) {
			super(context, R.layout.activity_expandlistitem_card,
					R.id.activity_expandlistitem_card_title,
					R.id.activity_expandlistitem_card_content, items);
			mContext = context;

			final int cacheSize = (int) (Runtime.getRuntime().maxMemory() / 1024);
			mMemoryCache = new LruCache<Integer, Bitmap>(cacheSize) {
				@Override
				protected int sizeOf(final Integer key, final Bitmap bitmap) {
					// The cache size will be measured in kilobytes rather than
					// number of items.
					return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
				}
			};
		}

		@Override
		public View getTitleView(final int position, final View convertView,
				final ViewGroup parent) {
			ViewHolder viewHolder;
			View view = convertView;
			if (view == null) {
				view = LayoutInflater.from(mContext).inflate(
						R.layout.activity_expandlist_label, parent, false);

				viewHolder = new ViewHolder();
				viewHolder.textView = (TextView) view
						.findViewById(R.id.activity_expandlist_label_textview);
				view.setTag(viewHolder);

				viewHolder.imageView = (ToggleButton) view
						.findViewById(R.id.activity_expandlist_label_togglebutton);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}

			viewHolder.textView.setText(mContext.getString(
					R.string.expandorcollapsecard, getItem(position)));

			return view;
		}

		@Override
		public View getContentView(final int position, final View convertView,
				final ViewGroup parent) {
			ImageView imageView = (ImageView) convertView;
			if (imageView == null) {
				imageView = new ImageView(mContext);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			}

			int imageResId;
			switch (getItem(position) % 5) {
			case 0:
				imageResId = R.drawable.img_nature1;
				break;
			case 1:
				imageResId = R.drawable.img_nature2;
				break;
			case 2:
				imageResId = R.drawable.img_nature3;
				break;
			case 3:
				imageResId = R.drawable.img_nature4;
				break;
			default:
				imageResId = R.drawable.img_nature5;
			}

			Bitmap bitmap = getBitmapFromMemCache(imageResId);
			if (bitmap == null) {
				bitmap = BitmapFactory.decodeResource(mContext.getResources(),
						imageResId);
				addBitmapToMemoryCache(imageResId, bitmap);
			}
			imageView.setImageBitmap(bitmap);

			return imageView;
		}

		private void addBitmapToMemoryCache(final int key, final Bitmap bitmap) {
			if (getBitmapFromMemCache(key) == null) {
				mMemoryCache.put(key, bitmap);
			}
		}

		private Bitmap getBitmapFromMemCache(final int key) {
			return mMemoryCache.get(key);
		}
	}

	@Override
	public void onDismiss(final AbsListView listView,
			final int[] reverseSortedPositions) {
		for (int position : reverseSortedPositions) {
			mExpandableListItemAdapter.remove(position);
		}
	}

	/**
	 * Temporary holder responsible for caching elements of single item
	 * 
	 */
	private static class ViewHolder {
		TextView textView;
		ToggleButton imageView;
	}

	public void onToggleClicked(View view) {

	}

	@Override
	protected void onDestroy() {
		itemsProvider.endFetchingData();
		super.onDestroy();
	}

	public void invokeMap(String latitude, String longitude) {
		String uri = "geo:" + latitude + "," + longitude;
		startActivity(new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse(uri)));
	}

	private void initLocationManager() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new FreyaLocationListener(this);

		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		if (location != null) {
			this.setLocation(location);
			locationListener.onLocationChanged(location);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1,
				locationListener);
		itemsProvider.resumeFetchingData();
	}
	
	@Override
	protected void onPause() {
		itemsProvider.cancelFetchingData();
		super.onPause();
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Location getLocation() {
		return location;
	}
}
