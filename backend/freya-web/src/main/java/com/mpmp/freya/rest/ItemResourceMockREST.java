package com.mpmp.freya.rest;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.mpmp.freya.rest.mock.ProviderMock;
import com.mpmp.iface.model.Item;
import com.mpmp.iface.model.Preference;
import com.mpmp.iface.model.PreferenceKey;
import com.mpmp.iface.model.User;
import com.mpmp.iface.service.ItemDAO;
import com.mpmp.iface.service.PreferenceDAO;
import com.mpmp.iface.service.UserDAO;

@Stateless
@Path("/mock")
public class ItemResourceMockREST {
	@EJB
	private ItemDAO itemDAO;

	@EJB
	private UserDAO userDAO;

	@EJB
	private PreferenceDAO prefrenceDAO;

	@Inject
	private ProviderMock providerMock;

	@GET
	@Path("item")
	public void addItem(@QueryParam(value = "n") int size, @QueryParam(value = "uid") String userId) {
		List<Item> items = providerMock.getItems(size, userId);
		for (Item item : items) {
			itemDAO.store(item);
		}
	}

	@GET
	@Path("user")
	public void addUser(@QueryParam(value = "uid") String userId) {
		User user = providerMock.createUser(userId);
		userDAO.store(user);
	}

	@GET
	@Path("preference")
	public void addPrefrence(@QueryParam(value = "itemId") Long itemId, @QueryParam(value = "uid") String userToken,
			@QueryParam(value = "score") float score) {
		
		User user = userDAO.findByToken(userToken);
		checkNotNull(user, "invalid user id");
		
		Preference pref = new Preference(new PreferenceKey(itemId, user.getId()), score);
		prefrenceDAO.store(pref);
	}
}
