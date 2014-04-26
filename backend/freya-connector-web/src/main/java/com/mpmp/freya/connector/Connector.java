package com.mpmp.freya.connector;

import java.util.Collection;

import com.mpmp.freya.connector.duplicatefilter.DuplicateFilter;
import com.mpmp.freya.connector.parser.Parser;
import com.mpmp.freya.connector.postprocessor.PostProcessor;
import com.mpmp.iface.model.Item;
import com.mpmp.iface.service.ItemDAO;

public class Connector {

    private final Parser parser;
    private final PostProcessor postProcessor;
    private final ItemDAO dao;
    private DuplicateFilter duplicateFinder;

    public Connector(Parser parser, DuplicateFilter duplicateFinder, PostProcessor postProcessor, ItemDAO dao) {
        this.parser = parser;
        this.duplicateFinder = duplicateFinder;
        this.postProcessor = postProcessor;
        this.dao = dao;
    }
    
    public void retrieveItems() {
        Collection<Item> items = parser.getItems();
        items = duplicateFinder.removeDuplicates(items);
        items = postProcessor.postProcess(items);
        for(Item item : items) {
            dao.store(item);
        }
    }
}
