package com.mpmp.freya.mobile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mpmp.freya.mobile.provider.Item;

public class ItemsProvider {

	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

	private final String URL = "http://82.145.64.188:8080/freya-web/items?n=20&uid=test&latitude=%d&longitude=%d&time=%d";
	private final Runnable worker;
	private ScheduledFuture<?> future;
	private List<Item> items;
	private MainActivity mainActivity;

	public ItemsProvider(MainActivity mainActivity) {
		this.mainActivity = mainActivity;

		final int latitude = (int) mainActivity.getLocation().getLatitude();
		final int longitude = (int) mainActivity.getLocation().getLongitude();
		final long time = mainActivity.getLocation().getTime();

		worker = new Runnable() {
			public void run() {
				InputStream source = retrieveStream(String.format(URL,
						latitude, longitude, time));
				Gson gson = new Gson();
				Reader reader = new InputStreamReader(source);

				Type listOfTestObject = new TypeToken<List<Item>>() {
				}.getType();
				items = gson.fromJson(reader, listOfTestObject);
			}
		};
	}

	public void startFetchingData() {
		future = scheduleFetchingData();
	}

	private ScheduledFuture<?> scheduleFetchingData() {
		return scheduler.isShutdown() ? null : scheduler.scheduleAtFixedRate(
				worker, 1, 30, TimeUnit.SECONDS);
	}

	public void resumeFetchingData() {
		if (future != null && future.isCancelled() && !future.isDone()) {
			startFetchingData();
		}
	}

	public void cancelFetchingData() {
		future.cancel(true);
	}

	public void endFetchingData() {
		scheduler.shutdown();
	}

	private InputStream retrieveStream(String url) {

		DefaultHttpClient client = new DefaultHttpClient();

		HttpGet getRequest = new HttpGet(url);

		try {

			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				Log.w(getClass().getSimpleName(), "Error " + statusCode
						+ " for URL " + url);
				return null;
			}

			HttpEntity getResponseEntity = getResponse.getEntity();
			return getResponseEntity.getContent();

		} catch (IOException e) {
			getRequest.abort();
			Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
		}

		return null;

	}

}
