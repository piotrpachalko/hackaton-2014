package com.mpmp.freya.connector.postprocessor;

import java.util.Collection;

import com.mpmp.iface.model.Item;

public class ImagePostProcessor extends PostProcessor {

    public ImagePostProcessor() {
        this(null);
    }
    
    public ImagePostProcessor(PostProcessor postProcessor) {
        super(postProcessor);
    }

    @Override
    protected Collection<Item> process(Collection<Item> items) {
        // TODO retrieve image for item
        return items;
    }
}
