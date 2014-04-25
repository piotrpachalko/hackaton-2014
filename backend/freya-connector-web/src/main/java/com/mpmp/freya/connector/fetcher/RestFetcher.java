package com.mpmp.freya.connector.fetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RestFetcher implements Fetcher {

    private String address;
    private Map<String, String> params = new LinkedHashMap<String, String>();

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void addParam(String key, String value) {
        params.put(key, value);
    }

    @Override
    public String getFullAddress() {
        StringBuilder builder = new StringBuilder();
        builder.append(address);
        builder.append("?");

        for (Entry<String, String> param : params.entrySet()) {
            builder.append(param.getKey());
            builder.append("=");
            builder.append(param.getValue());
            builder.append("&");
        }
        builder.setLength(builder.length() - 1); // remove last "&" sign TODO
                                                 // refactor

        return builder.toString();
    }

    @Override
    public JSONObject retrieve() {

        JSONObject result = new JSONObject();

        // TODO extract to class
        InputStream stream = null;
        StringBuilder rawResult = new StringBuilder();
        try {
            URL url = new URL(getFullAddress());
            stream = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            
            String line = null;
            while((line = reader.readLine()) != null) {
                rawResult.append(line);
            }
            
        } catch (IOException e) {
            // TODO better handle? logs?
            return result;
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                // TODO better handle? log?
                e.printStackTrace();
            }
        }

        // TODO maybe parse stream directly?
        try {
            result = (JSONObject) new JSONParser().parse(rawResult.toString());
        } catch (ParseException e) {
            // TODO handle exception? log?
            e.printStackTrace();
        }
        
        return result;
    }

}
