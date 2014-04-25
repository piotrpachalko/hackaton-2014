package com.mpmp.freya.connector.fetcher;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;

import org.fest.assertions.Assertions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import com.google.mockwebserver.MockResponse;
import com.google.mockwebserver.MockWebServer;

public class RestFetcherTest {

    private static final String FULL_ADDRESS = "http://localhost?firstParam=foo&secondParam=bar&thirdParam=baz";
    private static final String FIRST_PARAM_VALUE = "foo";
    private static final String SECOND_PARAM_VALUE = "bar";
    private static final String THIRD_PARAM_VALUE = "baz";
    
    private static final String FIRST_PARAM_KEY = "firstParam";
    private static final String SECOND_PARAM_KEY = "secondParam";
    private static final String THIRD_PARAM_KEY = "thirdParam";
    
    private static final String HOST = "http://localhost";

    private static final String JSON_ARRAY = "{\"results\":[{\"name\" : \"first name\",\"description\" : \"first description\"},{\"name\" : \"second name\",\"description\" : \"second description\"}]}";

    private MockWebServer server;
    
    @Test
    public void shouldCreateProperAddress() {
        
        // given
        Fetcher fetcher = new RestFetcher();
        
        // when
        fetcher.setAddress(HOST);
        fetcher.addParam(FIRST_PARAM_KEY, FIRST_PARAM_VALUE);
        fetcher.addParam(SECOND_PARAM_KEY, SECOND_PARAM_VALUE);
        fetcher.addParam(THIRD_PARAM_KEY, THIRD_PARAM_VALUE);
        
        // then
        assertThat(fetcher.getFullAddress()).isEqualTo(FULL_ADDRESS);
    }
    
    @Test
    public void shouldRetrieveJsonFromGivenAddress() throws IOException {
        
        // given
        server = new MockWebServer();
        server.play();
        
        server.enqueue(new MockResponse().setBody(JSON_ARRAY));
        
        Fetcher fetcher = new RestFetcher();
        fetcher.setAddress(server.getUrl("/PATH").toString());
        
        // when
        JSONObject result = fetcher.retrieve();
        System.out.println(result);
        
        // then
        JSONArray resultArray = (JSONArray) result.get("results");
        System.out.println(resultArray);
        
        assertThat(resultArray.size()).isEqualTo(2);
        
        JSONObject firstResult = (JSONObject) resultArray.get(0);
        assertThat(firstResult.get("name")).isEqualTo("first name");
        assertThat(firstResult.get("description")).isEqualTo("first description");
        
        JSONObject secondResult = (JSONObject) resultArray.get(1);
        assertThat(secondResult.get("name")).isEqualTo("second name");
        assertThat(secondResult.get("description")).isEqualTo("second description");
        
        server.shutdown();
    }
}
