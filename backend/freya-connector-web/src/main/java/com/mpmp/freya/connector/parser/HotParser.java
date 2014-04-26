package com.mpmp.freya.connector.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mpmp.freya.connector.commons.WebAddress;
import com.mpmp.freya.connector.fetcher.HtmlFetcher;
import com.mpmp.iface.model.Item;

@Stateless
public class HotParser implements Parser {

    WebAddress address = new WebAddress("http://hotmag.pl");
    
    @EJB
    private HtmlFetcher fetcher;

    public HotParser() {
    }
    
    public HotParser(HtmlFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public Collection<Item> getItems() {
        Collection<Item> items = new LinkedList<Item>();
        Document retrieved = fetcher.retrieve(address, null);
        
         Element element = retrieved.getElementsByClass("events_zakladki").get(0);
        
         items.addAll(parseTopic(element.getElementsByClass("events_zakladkiMiasto-default").get(0)));
         items.addAll(parseTopic(element.getElementsByClass("events_zakladkiImprezy-default").get(0)));
         items.addAll(parseTopic(element.getElementsByClass("events_zakladkiKoncerty-default").get(0)));
        
         return items;
    }

    private Collection<Item> parseTopic(Element element) {
        return new ArrayList<Item>();
    }

}