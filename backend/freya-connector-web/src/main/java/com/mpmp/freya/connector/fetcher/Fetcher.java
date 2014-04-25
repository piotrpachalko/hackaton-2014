package com.mpmp.freya.connector.fetcher;

import org.json.simple.JSONObject;


public interface Fetcher {

    void setAddress(String string);

    void addParam(String key, String value);

    String getFullAddress();

    JSONObject retrieve();

}
