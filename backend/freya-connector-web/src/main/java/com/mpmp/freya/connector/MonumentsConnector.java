package com.mpmp.freya.connector;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.mpmp.freya.connector.duplicatefilter.DuplicateFilter;
import com.mpmp.freya.connector.parser.Parser;
import com.mpmp.freya.connector.postprocessor.PostProcessor;
import com.mpmp.iface.service.ItemDAO;

@Stateless
public class MonumentsConnector {

    @EJB(name = "MonumentsParser")
    private Parser parser;

    @EJB(name = "MonumentsDuplicateFinder")
    private DuplicateFilter filter;

    @EJB(name = "ImagePostProcessor")
    private PostProcessor imagePostProcessor;
    
    @EJB(name = "MapPostProcessor")
    private PostProcessor mapPostProcessor;
    
    @EJB
    private ItemDAO dao;

    public void retrieveItems() {
        new Connector(parser, filter, dao, imagePostProcessor, mapPostProcessor).retrieveItems();
    }

}
