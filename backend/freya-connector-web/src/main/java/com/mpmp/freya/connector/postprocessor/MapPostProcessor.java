package com.mpmp.freya.connector.postprocessor;

import java.util.Collection;

import javax.ejb.Stateless;

import com.mpmp.iface.model.Item;

@Stateless
public class MapPostProcessor implements PostProcessor {

    @Override
    public Collection<Item> postProcess(Collection<Item> items) {
        return items;
    }
}
