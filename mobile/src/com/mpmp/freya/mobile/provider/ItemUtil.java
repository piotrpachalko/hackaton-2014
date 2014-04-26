package com.mpmp.freya.mobile.provider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class ItemUtil {

	/**
	 * Returns set of ids to remove
	 * 
	 * @param newItems
	 *            retrieved
	 * @param currentItems
	 *            current (old) items
	 * 
	 * @return ids to remove
	 */
	public static Set<Integer> elementsToRemove(List<Item> newItems,
			Map<Integer, Item> currentItems) {

		final Set<Integer> currentKeys = currentItems.keySet();
		final List<Integer> newKeys = Lists.transform(newItems,
				new Function<Item, Integer>() {
					public Integer apply(Item item) {
						return (int) item.getId(); // Integer, ask Mario why
					}
				});

		currentKeys.removeAll(newKeys);

		return currentKeys;
	}
	

	public static List<Integer> mapElementsToAdd(List<Item> newItems) {

		List<Integer> itemsToAdd = new ArrayList<Integer>();

		for (Item item : newItems) {
			int itemId = (int) item.getId();
				itemsToAdd.add(itemId);		
		}

		return itemsToAdd;
	}

	/**
	 * Creates map of id -> item
	 * 
	 * @param newItems
	 *            retrieved
	 * @param currentItems
	 *            current (old) items
	 * @return map
	 */
	public static Map<Integer, Item> mapElementsToAdd(List<Item> newItems,
			Map<Integer, Item> currentItems) {

		Map<Integer, Item> itemsToAdd = new LinkedHashMap<Integer, Item>();

		for (Item item : newItems) {
			int itemId = (int) item.getId();
			if (!currentItems.containsKey(itemId)) {
				itemsToAdd.put(itemId, item);
			}
		}

		return itemsToAdd;
	}

}
