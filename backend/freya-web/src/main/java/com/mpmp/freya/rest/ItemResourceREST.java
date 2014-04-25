package com.mpmp.freya.rest;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.mpmp.iface.model.Item;
import com.mpmp.iface.service.ItemDAO;
import com.mpmp.iface.service.ItemService;

/**
 * Item service REST implementation
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
@Stateless
@Path("/items")
public class ItemResourceREST implements ItemService {

	@EJB
	private ItemDAO itemDAO;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> getItems(@QueryParam(value = "n") int size, @QueryParam(value = "uid") String userId) {
		checkArgument(size > 0, "Provide positive size");
		checkNotNull(userId, "Provide proper user id in header");

		return itemDAO.findAll();
	}

}
