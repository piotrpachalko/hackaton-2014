package com.mpmp.freya.connector.commons;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class QueryParameters {

    private Map<String, Parameter> params = new HashMap<String, Parameter>();
    
    public QueryParameters(Parameter ... params) {
        for(Parameter param : params) {
            this.params.put(param.getKey(), param);
        }
    }
    
    public String getParam(String key) {
        return params.get(key).getValue();
    }
    
    public Collection<Parameter> getParams() {
        return Collections.unmodifiableCollection(params.values());
    }
    
}
