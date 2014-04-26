/**
 * 
 */
package com.mpmp.freya.mobile;

import java.util.List;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * @author sondemar
 * 
 */
public abstract class AbstractActivity extends ActionBarActivity {

	private ListView mListView;
	private List<Integer> items;

	public ListView getListView() {
		return mListView;
	}
	
	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow()
					.addFlags(
							android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.activity_main_listview);
		mListView.setDivider(null);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	

	public List<Integer> getItems() {
		/*ArrayList<Integer> items = new ArrayList<Integer>();
		for (int i = 0; i < 1000; i++) {
			items.add(i);
		}*/
		return items;
	}

	public void setItems(List<Integer> items) {
		this.items = items;		
	}
	
	

}
