package com.mpmp.freya.mobile.provider;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.util.Log;

public class ItemsProvider {

	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

	private final String URL = "http://82.145.64.188:8080/freya-web/items?n=20&uid=test";

	public void startFetchingData() {
		final Runnable beeper = new Runnable() {
			public void run() {
				InputStream source = retrieveStream(URL);		        
		        Gson gson = new Gson();		        
		        Reader reader = new InputStreamReader(source);		
		        
		        Type listOfTestObject = new TypeToken<List<Item>>(){}.getType();
		       // String s = gson.toJson(list, listOfTestObject);
		        List<Item> items = gson.fromJson(reader, listOfTestObject);
		        
/*		        SearchResponse response = gson.fromJson(reader, SearchResponse.class);
		       
		        List<Item> items = response.getResult();*/
			}
		};

		final ScheduledFuture beeperHandle = scheduler.scheduleAtFixedRate(
				beeper, 1, 30, TimeUnit.SECONDS);
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
