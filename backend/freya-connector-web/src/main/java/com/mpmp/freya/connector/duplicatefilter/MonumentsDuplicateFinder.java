package com.mpmp.freya.connector.duplicatefilter;

import java.util.Collection;

import com.mpmp.iface.model.Item;
import com.mpmp.iface.service.ItemDAO;

public class MonumentsDuplicateFinder implements DuplicateFilter {

    private ItemDAO dao;

    public MonumentsDuplicateFinder(ItemDAO dao) {
        this.dao = dao;
    }
    
    @Override
    public Collection<Item> removeDuplicates(Collection<Item> items) {
        return items;
    }

    
    
}
