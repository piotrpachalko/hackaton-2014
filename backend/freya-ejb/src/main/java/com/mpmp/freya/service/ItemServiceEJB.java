package com.mpmp.freya.service;

import io.pp.ItemRecommenderEJB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import com.mpmp.iface.model.Item;
import com.mpmp.iface.model.User;
import com.mpmp.iface.service.ItemDAO;
import com.mpmp.iface.service.ItemService;
import com.mpmp.iface.service.UserService;

@Stateless
@Remote(ItemService.class)
public class ItemServiceEJB implements ItemService {

	@EJB
	private ItemDAO itemDAO;
	
	@EJB
	private UserService userServiceEJB;

	@EJB
	private ItemRecommenderEJB recommenderEJB;

	@Override
	public List<Item> getItems(int size, String userId, double latitude, double longitude, long time) {
		userServiceEJB.updateLocation(userId, latitude, longitude, new Date(time));
		User user = userServiceEJB.findUser(userId);

		List<RecommendedItem> recommendations = null;
		try {
			recommendations = recommenderEJB.getRecsFor(user.getId(), size);
		} catch (TasteException e) {
			throw new RuntimeException(e);
		}
		return toItems(recommendations);
	}

	private List<Item> toItems(List<RecommendedItem> recommendations) {
		List<Item> items = new ArrayList<Item>();

		for (RecommendedItem recommendedItem : recommendations) {
			Item item = itemDAO.findById(recommendedItem.getItemID());
			if(item != null) {
				items.add(item);
			} 
		}

		return items;
	}

}
