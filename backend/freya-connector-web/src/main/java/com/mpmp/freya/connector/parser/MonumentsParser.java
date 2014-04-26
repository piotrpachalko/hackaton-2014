package com.mpmp.freya.connector.parser;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mpmp.freya.connector.commons.Parameter;
import com.mpmp.freya.connector.commons.QueryParameters;
import com.mpmp.freya.connector.commons.WebAddress;
import com.mpmp.freya.connector.fetcher.Fetcher;
import com.mpmp.iface.model.Item;

public class MonumentsParser implements Parser {

    private WebAddress source;
    private Fetcher fetcher;

    public MonumentsParser(WebAddress source, Fetcher fetcher) {
        this.source = source;
        this.fetcher = fetcher;
    }

    @Override
    public Collection<Item> getItems() {
        Collection<Item> items = new LinkedList<Item>();
        
        
        Parameter place = new Parameter("place", "szczecin");
        QueryParameters params = new QueryParameters(place);
        JSONObject result = fetcher.retrieve(source, params);

        JSONArray relics = (JSONArray) result.get("relics");
        for(int i = 0; i < relics.size(); ++i) {
            JSONObject relic = (JSONObject) relics.get(i);
            
            String name = (String) relic.get("identification");
            
            StringBuilder url = new StringBuilder();
            url.append("http://otwartezabytki.pl/pl/relics?utf8=%E2%9C%93&search[q]=");
            url.append(name.replaceAll(" ", "%20"));
            url.append("&search[place]=Szczecin");

            StringBuilder location = new StringBuilder();
            location.append(Double.toString((Double) relic.get("latitude")));
            location.append(";");
            location.append(Double.toString((Double) relic.get("longitude")));
            
            Item item = new Item();
            item.setTitle(name);
            item.setDescr("mock data"); // TODO put some data here
            item.setStartTime(new Date(0L));
            item.setEndTime(new Date(0L));
            item.setUrl(url.toString());
            item.setLocation(location.toString());
            items.add(item);
        }
        
        return items;
    }

}
