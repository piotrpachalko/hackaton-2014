package com.mpmp.freya.connector;

import java.util.Collection;

import com.mpmp.freya.connector.duplicatefilter.DuplicateFilter;
import com.mpmp.freya.connector.parser.Parser;
import com.mpmp.freya.connector.postprocessor.PostProcessor;
import com.mpmp.iface.model.Item;
import com.mpmp.iface.service.ItemDAO;

public class Connector {

    private final Parser parser;
    private final ItemDAO dao;
    private final DuplicateFilter duplicateFinder;
    private final PostProcessor[] postProcessors;

    public Connector(Parser parser, DuplicateFilter duplicateFinder, ItemDAO dao, PostProcessor ... postProcessors) {
        this.parser = parser;
        this.duplicateFinder = duplicateFinder;
        this.dao = dao;
        this.postProcessors = postProcessors;
    }
    
    public void retrieveItems() {
        
        Collection<Item> items = parser.getItems();
        
        items = duplicateFinder.removeDuplicates(items);

        for(PostProcessor postProcessor : postProcessors) {
            items = postProcessor.postProcess(items);
        }
        
        for(Item item : items) {
            dao.store(item);
        }
    }
}
