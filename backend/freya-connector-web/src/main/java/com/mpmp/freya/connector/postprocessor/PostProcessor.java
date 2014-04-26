package com.mpmp.freya.connector.postprocessor;

import java.util.Collection;

import com.mpmp.iface.model.Item;

public interface PostProcessor {

    Collection<Item> postProcess(Collection<Item> items);
}
