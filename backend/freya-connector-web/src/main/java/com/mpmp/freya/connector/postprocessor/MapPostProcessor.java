package com.mpmp.freya.connector.postprocessor;

import java.util.Collection;

import com.mpmp.freya.connector.postprocessor.PostProcessor;
import com.mpmp.iface.model.Item;

public class MapPostProcessor implements PostProcessor {

    private PostProcessor postProcessor;

    public MapPostProcessor(PostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    @Override
    public Collection<Item> postProcess(Collection<Item> items) {
        Collection<Item> itemsAfterProcessing = process(items);
        return postProcessor.postProcess(itemsAfterProcessing);
    }

    private Collection<Item> process(Collection<Item> items) {
        // TODO add implementation that uses google maps api
        return items;
    }

}
