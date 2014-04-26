package com.mpmp.freya.connector;

import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.mpmp.freya.connector.commons.Parameter;
import com.mpmp.freya.connector.commons.QueryParameters;
import com.mpmp.freya.connector.commons.WebAddress;
import com.mpmp.freya.connector.duplicatefilter.DuplicateFilter;
import com.mpmp.freya.connector.duplicatefilter.MonumentsDuplicateFinder;
import com.mpmp.freya.connector.fetcher.Fetcher;
import com.mpmp.freya.connector.fetcher.RestFetcher;
import com.mpmp.freya.connector.parser.MonumentsParser;
import com.mpmp.freya.connector.parser.Parser;
import com.mpmp.freya.connector.postprocessor.MapPostProcessor;
import com.mpmp.freya.connector.postprocessor.PostProcessor;
import com.mpmp.iface.model.Item;
import com.mpmp.iface.service.ItemDAO;

public class ConnectorTest {

    @Test
    public void shouldStoreCorrectAmountOfItems() throws IOException, ParseException {

        // given
        WebAddress listAddress = new WebAddress("http://otwartezabytki.pl/api/v1/relics.json");
        Parameter place = new Parameter("place", "szczecin");
        Parameter page = new Parameter("page", "1");
        QueryParameters params = new QueryParameters(place, page);
        
        Fetcher mockRestFetcher = mock(Fetcher.class);
        
        Parameter secondPageParam = new Parameter("page", "2");
        QueryParameters paramsSecondPage = new QueryParameters(place, secondPageParam);
        
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream firstPageStream = classloader.getResourceAsStream("multiple-page-first.json");
        InputStream secondPageStream = classloader.getResourceAsStream("multiple-page-second.json");
        
        JSONObject firstPage = (JSONObject) new JSONParser().parse(new BufferedReader(new InputStreamReader(firstPageStream)));
        Mockito.when(mockRestFetcher.retrieve(listAddress, params)).thenReturn(firstPage );
        
        JSONObject secondPage = (JSONObject) new JSONParser().parse(new BufferedReader(new InputStreamReader(secondPageStream)));
        Mockito.when(mockRestFetcher.retrieve(listAddress, paramsSecondPage)).thenReturn(secondPage);
        
        Parser parser = new MonumentsParser(listAddress, mockRestFetcher);
        PostProcessor postProcessor = new MapPostProcessor();
        ItemDAO dao = Mockito.mock(ItemDAO.class);
        DuplicateFilter duplicateFinder = new MonumentsDuplicateFinder(dao);
        
        Connector connector = new Connector(parser, duplicateFinder, dao, postProcessor);
        
        // when
        connector.retrieveItems();
        
        // then
        Mockito.verify(dao, Mockito.times(6)).store(Mockito.any(Item.class));
    }
    
    @Ignore
    @Test
    public void realCaseScenario() {

        
        // given
        WebAddress listAddress = new WebAddress("http://otwartezabytki.pl/api/v1/relics.json");
        Fetcher fetcher = new RestFetcher();

        Parser parser = new MonumentsParser(listAddress, fetcher);
        ItemDAO dao = Mockito.mock(ItemDAO.class);
        PostProcessor postProcessor = new MapPostProcessor();
        DuplicateFilter duplicateFinder = new MonumentsDuplicateFinder(dao);
        
        Connector connector = new Connector(parser, duplicateFinder, dao, postProcessor);
        
        // when
        connector.retrieveItems();
        
        // then
        Mockito.verify(dao, Mockito.times(6)).store(Mockito.any(Item.class));
    }
}
