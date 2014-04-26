package com.mpmp.freya.mobile;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class LoadURLImage extends AsyncTask<String, Void, Bitmap> {

	private ImageView imageView;
	
	public LoadURLImage(
			ImageView imageView) {
		this.imageView = imageView;
	}

	@Override
	protected Bitmap doInBackground(String... urls) {
		Bitmap map = null;
		for (String url : urls) {
			map = downloadImage(url);
		}
		return map;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		imageView.setImageBitmap(result);
	}

	private Bitmap downloadImage(String url) {
		Bitmap bitmap = null;
		InputStream stream = null;

		try {
			stream = getHttpConnection(url);
			bitmap = decodeBitMap(stream);
			stream.close();
		} catch (IOException e) {
			Log.w(e.getMessage(), e);
		}
		return bitmap;
	}

	private InputStream getHttpConnection(String urlString) throws IOException {
		InputStream stream = null;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = null;
		httpGet = new HttpGet(urlString);

		HttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpGet);
			HttpEntity getResponseEntity = httpResponse.getEntity();
			return getResponseEntity.getContent();
		} catch (IOException e) {
			httpGet.abort();
			Log.w(e.getMessage(), e);
		}

		return stream;
	}
	
	private Bitmap decodeBitMap(InputStream stream){
	    //Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		//The new size we want to scale to
		final int REQUIRED_SIZE=70;

		//Find the correct scale value. It should be the power of 2.
		int scale=1;
		while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
		    scale*=2;

		//Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize=scale;
		return BitmapFactory.decodeStream(stream, null, o2);
	}

}
