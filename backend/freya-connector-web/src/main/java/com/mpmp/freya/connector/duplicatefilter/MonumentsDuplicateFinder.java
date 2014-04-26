package com.mpmp.freya.connector.duplicatefilter;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.mpmp.iface.model.Item;
import com.mpmp.iface.service.ItemDAO;

@Stateless
public class MonumentsDuplicateFinder implements DuplicateFilter {

    @EJB
    private ItemDAO dao;

    public MonumentsDuplicateFinder() {
    }
    
    public MonumentsDuplicateFinder(ItemDAO dao) {
        this.dao = dao;
    }
    
    @Override
    public Collection<Item> removeDuplicates(Collection<Item> items) {
        return items;
    }

    
    
}
