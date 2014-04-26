package com.mpmp.freya.connector;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.mpmp.freya.connector.duplicatefilter.DuplicateFilter;
import com.mpmp.freya.connector.parser.Parser;
import com.mpmp.freya.connector.postprocessor.PostProcessor;
import com.mpmp.iface.service.ItemDAO;

@Stateless
@Path("monument")
public class MonumentsConnector {

    @EJB(beanName = "MonumentsParser")
    private Parser parser;

    @EJB(beanName = "MonumentsDuplicateFinder")
    private DuplicateFilter filter;

    @EJB(beanName = "ImagePostProcessor")
    private PostProcessor imagePostProcessor;
    
    @EJB(beanName = "MapPostProcessor")
    private PostProcessor mapPostProcessor;
    
    @EJB(lookup = "java:global/freya/freya-ejb-1.0-SNAPSHOT/ItemDAOImpl!com.mpmp.iface.service.ItemDAO")
    private ItemDAO dao;

    @GET
    @Path("/")
    @Schedule(hour="*/1")
    public void retrieveItems() {
        new Connector(parser, filter, dao, imagePostProcessor, mapPostProcessor).retrieveItems();
    }

}
