package com.mpmp.freya.connector.postprocessor;

import java.util.Collection;

import com.mpmp.iface.model.Item;

public class MapPostProcessor extends PostProcessor {

    public MapPostProcessor() {
        this(null);
    }
    
    public MapPostProcessor(PostProcessor postProcessor) {
        super(postProcessor);
    }

    @Override
    protected Collection<Item> process(Collection<Item> items) {
        // TODO add implementation that uses google maps api
        return items;
    }
}
