package com.mpmp.freya.connector.fetcher;

import org.json.simple.JSONObject;

import com.mpmp.freya.connector.commons.QueryParameters;
import com.mpmp.freya.connector.commons.WebAddress;


public interface Fetcher {
    JSONObject retrieve(WebAddress address, QueryParameters params);
}
