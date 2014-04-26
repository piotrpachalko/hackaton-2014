package com.mpmp.freya.connector.fetcher;

import java.io.IOException;

import javax.ejb.Stateless;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mpmp.freya.connector.commons.QueryParameters;
import com.mpmp.freya.connector.commons.WebAddress;

@Stateless
public class HtmlFetcher implements Fetcher<Document> {

    @Override
    public Document retrieve(WebAddress address, QueryParameters params) {
        Document document = null;
        try {
            document = Jsoup.connect(address.getUrl()).get();
        } catch (IOException e) {
            // TODO handle?
            e.printStackTrace();
        }
        return document;
    }

}
