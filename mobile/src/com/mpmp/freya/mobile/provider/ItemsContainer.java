package com.mpmp.freya.mobile.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ItemsContainer {

	public static Map<Integer, Item> viewItems = new HashMap<Integer, Item>();
	public static List<Integer> providedItems = new ArrayList<Integer>();
	public static Map<Long, Item> tempItems = new HashMap<Long, Item>();
	
	
	public static void addItems(List<Item> providedItems) {
	//	tempItems = (LinkedHashMap<Long, Item>) viewItems.clone();
		for (Item item : providedItems) {
			ItemsContainer.providedItems.add((int) item.getId());
			ItemsContainer.viewItems.put((int) item.getId(), item);
		}
		/*Set<Entry<Long, Item>> entrySet = viewItems.entrySet();
		for (Entry<Long, Item> entry : entrySet) {
			if(providedItems.contains(object))
		}*/
	}
	
}
