package com.mpmp.freya.connector.fetcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.fest.assertions.Assertions;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.google.mockwebserver.MockResponse;
import com.google.mockwebserver.MockWebServer;
import com.mpmp.freya.connector.commons.WebAddress;

public class HtmlFetcherTest {

    private MockWebServer server;

    @Test
    public void shouldRetrieveHtml() throws IOException {

        // given
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("simple.html");
        String response = new Scanner(is).useDelimiter("//Z").next();
        setupServerWithResponse(response);
        
        Fetcher<Document> fetcher = new HtmlFetcher();
        WebAddress address = new WebAddress(server.getUrl("/PATH").toString());

        // when
        // TODO api should be slightly changed - html fetcher does not need
        // params
        Document html = fetcher.retrieve(address, null);
        
        // then
        Assertions.assertThat(html.toString()).isNotEmpty();
    }

    private void setupServerWithResponse(String response) throws IOException {
        server = new MockWebServer();
        server.play();
        server.enqueue(new MockResponse().setBody(response));
    }

}
