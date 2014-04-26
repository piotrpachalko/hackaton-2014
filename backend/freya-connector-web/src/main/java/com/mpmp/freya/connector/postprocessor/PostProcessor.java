package com.mpmp.freya.connector.postprocessor;

import java.util.ArrayList;
import java.util.Collection;

import com.mpmp.iface.model.Item;

public abstract class PostProcessor {

    protected PostProcessor postProcessor;

    protected abstract Collection<Item> process(Collection<Item> items);

    public PostProcessor(PostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }
    
    public Collection<Item> postProcess(Collection<Item> items) {
        Collection<Item> postProcessedItems = new ArrayList<Item>(items);
        if(postProcessor != null) {
            postProcessedItems = postProcessor.postProcess(items);
        }
        
        return process(postProcessedItems);
    }
}
