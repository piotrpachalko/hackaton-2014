package com.mpmp.freya.connector.fetcher;

import com.mpmp.freya.connector.commons.QueryParameters;
import com.mpmp.freya.connector.commons.WebAddress;


public interface Fetcher<T> {
    T retrieve(WebAddress address, QueryParameters params);
}
