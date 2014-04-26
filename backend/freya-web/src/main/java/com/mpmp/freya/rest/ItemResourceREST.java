package com.mpmp.freya.rest;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mpmp.iface.model.Item;
import com.mpmp.iface.service.ItemService;

/**
 * Item service REST implementation
 * 
 * @author Pawel Turczyk (pturczyk@gmail.com)
 */
@Stateless
@Path("/items")
public class ItemResourceREST {

	@EJB(beanName = "ItemServiceEJB")
	private ItemService itemServiceEJB;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItems(@QueryParam(value = "n") int size, @QueryParam(value = "uid") String userToken,
			@QueryParam(value = "latitude") double latitude, @QueryParam(value = "longitude") double longitude,
			@QueryParam(value = "time") long time) {

		checkArgument(size > 0, "Provide positive size");
		checkNotNull(userToken, "Provide proper user id");

		List<Item> items = itemServiceEJB.getItems(size, userToken, latitude, longitude, time);
		return Response.ok(items).build();
	}

	@POST
	@Path("/")
	public Response updatePreferences(@QueryParam(value = "uid") String userToken,
			@QueryParam(value = "itemId") Long itemId, @QueryParam(value = "preference") float preference) {
	
		checkNotNull(userToken, "Provide proper user id in header");
		checkNotNull(itemId, "Provide proper item id");

		itemServiceEJB.updatePreferences(userToken, itemId, preference);
		return Response.ok().build();
	}

}
