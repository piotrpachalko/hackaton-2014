package com.mpmp.freya.connector.fetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.ejb.Stateless;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mpmp.freya.connector.commons.Parameter;
import com.mpmp.freya.connector.commons.QueryParameters;
import com.mpmp.freya.connector.commons.WebAddress;

/**
 * Rest fetcher class allows to connect to given http address and fetch json
 * data from it.
 * 
 * To use it:
 * 1. use setAddress method to set http adress of api method you want to call
 * 2. use addParameter method to fill all parameters for call (e.g. api keys)
 * 3. use retrieve method to get json you desire
 *  
 * @author michal
 */
@Stateless
public class RestFetcher implements Fetcher<JSONObject> {

    @Override
    public JSONObject retrieve(WebAddress address, QueryParameters params) {

        
        JSONObject result = new JSONObject();

        // TODO extract to class
        InputStream stream = null;
        StringBuilder rawResult = new StringBuilder();
        try {
            URL url = new URL(getFullAddress(address, params));
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

    private String getFullAddress(WebAddress address, QueryParameters params) {
        
        StringBuilder builder = new StringBuilder();
        builder.append(address.getUrl());
        builder.append("?");

        if (params != null) {
            for (Parameter parameter : params.getParams()) {
                builder.append(parameter.getKey());
                builder.append("=");
                builder.append(parameter.getValue());
                builder.append("&");
            }
            // remove last "&" sign
            // TODO refactor
            builder.setLength(builder.length() - 1);
        }

        return builder.toString();

    }


}
