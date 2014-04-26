package com.mpmp.tools;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mpmp.iface.model.Item;

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
	public static Set<Integer> elementsToRemove(List<Item> newItems, Map<Integer, Item> currentItems) {

		final Set<Integer> currentKeys = currentItems.keySet();
		final Set<Integer> newKeys = new LinkedHashSet<Integer>();
		
		for(Item newItem: newItems) {
			newKeys.add((int)newItem.getId());
		}
		
		currentKeys.removeAll(newKeys);

		return currentKeys;
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
	public static Map<Integer, Item> mapElementsToAdd(List<Item> newItems, Map<Integer, Item> currentItems) {

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
