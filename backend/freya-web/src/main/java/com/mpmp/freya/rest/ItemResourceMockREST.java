package com.mpmp.freya.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.mpmp.freya.rest.mock.ItemProviderMock;
import com.mpmp.iface.model.Item;
import com.mpmp.iface.service.ItemDAO;

@Stateless
@Path("/mock")
public class ItemResourceMockREST {
	@EJB
	private ItemDAO itemDAO;
	
	@Inject
	private ItemProviderMock providerMock;

	@GET
	public void addItem(@QueryParam(value = "n") int size, @QueryParam(value = "uid") String userId) {
		List<Item> items = providerMock.getItems(size, userId);
		for (Item item : items) {
			itemDAO.store(item);
		}
	}
}
