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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((params == null) ? 0 : params.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QueryParameters other = (QueryParameters) obj;
        if (params == null) {
            if (other.params != null)
                return false;
        } else if (!params.equals(other.params))
            return false;
        return true;
    }
}
