package com.mpmp.freya.connector.duplicatefilter;

import java.util.Collection;

import com.mpmp.iface.model.Item;

public interface DuplicateFilter {
    Collection<Item> removeDuplicates(Collection<Item> items);
}
