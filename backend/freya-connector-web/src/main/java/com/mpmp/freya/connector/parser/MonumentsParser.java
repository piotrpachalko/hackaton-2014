package com.mpmp.freya.connector.parser;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mpmp.freya.connector.commons.Parameter;
import com.mpmp.freya.connector.commons.QueryParameters;
import com.mpmp.freya.connector.commons.WebAddress;
import com.mpmp.freya.connector.fetcher.Fetcher;
import com.mpmp.iface.model.Item;

@Stateless
public class MonumentsParser implements Parser {

    private WebAddress source = new WebAddress("http://otwartezabytki.pl/api/v1/relics.json");
    
    @EJB(name="RestFetcher")
    private Fetcher fetcher;

    public MonumentsParser(WebAddress source, Fetcher fetcher) {
        this.source = source;
        this.fetcher = fetcher;
    }
    
    @Override
    public Collection<Item> getItems() {
        Collection<Item> items = new LinkedList<Item>();

        Parameter place = new Parameter("place", "szczecin");
        Parameter page = new Parameter("page", "1");

        QueryParameters params = new QueryParameters(place, page);
        JSONObject result = fetcher.retrieve(source, params);

        JSONObject meta = (JSONObject) result.get("meta");
        Long totalPages = (Long) meta.get("total_pages");
        Long currentPage = 1L;

        do {

            JSONArray relics = (JSONArray) result.get("relics");

            for (int i = 0; i < relics.size(); ++i) {

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
            place = new Parameter("place", "szczecin");
            page = new Parameter("page", Long.toString(++currentPage));
            params = new QueryParameters(place, page);
            result = fetcher.retrieve(source, params);
            
        } while (currentPage <= totalPages);

        return items;
    }

}
