package com.mpmp.freya.rest.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mpmp.iface.model.Item;
import com.mpmp.iface.model.Location;
import com.mpmp.iface.model.Preference;
import com.mpmp.iface.model.User;

public class ProviderMock {
	public List<Item> getItems(int size, String userId) {
		List<Item> items = new ArrayList<Item>();
		
		for(int i = 0 ; i < size; ++i) {
			Item item = createItem(i, userId);
			items.add(item);
		}
		
		return items;
	}

	
	public User createUser(String userId) {
		Location location = new Location(55.6, 75.5, new Date());
		User user = new User(userId, location);
		return user;
	}
	
	private Item createItem(int i, String userId) {
		Date startTime =  new Date();
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, i);
		Date endTime = calendar.getTime();
		
		return new Item(null, 
				"Some title " + i, 
				"Some description " + i, 
				startTime,
				endTime,
				"Location " + userId,
				"http://some.url.com/" + i);
	}
}
