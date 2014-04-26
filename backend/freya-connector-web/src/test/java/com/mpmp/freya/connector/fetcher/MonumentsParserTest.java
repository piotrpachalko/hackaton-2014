package com.mpmp.freya.connector.fetcher;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.fest.assertions.Assertions;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.mockito.Mockito;

import com.mpmp.freya.connector.commons.Parameter;
import com.mpmp.freya.connector.commons.QueryParameters;
import com.mpmp.freya.connector.commons.WebAddress;
import com.mpmp.freya.connector.parser.MonumentsParser;
import com.mpmp.freya.connector.parser.Parser;
import com.mpmp.iface.model.Item;

public class MonumentsParserTest {

    @Test
    public void shouldGetItems() throws FileNotFoundException, IOException, ParseException {
        
        // given
        WebAddress listAddress = new WebAddress("http://otwartezabytki.pl/api/v1/relics.json");
        Parameter place = new Parameter("place", "szczecin");
        QueryParameters params = new QueryParameters(place);
        
        Fetcher mockRestFetcher = mock(Fetcher.class);
        
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("single-list.json");
                
        JSONObject object = (JSONObject) new JSONParser().parse(new BufferedReader(new InputStreamReader(is)));
        Mockito.when(mockRestFetcher.retrieve(listAddress, params)).thenReturn(object );
        
        Parser parser = new MonumentsParser(listAddress, mockRestFetcher);
        
        // when
        Collection<Item> items = parser.getItems();
        
        // then
        assertThat(items.size()).isEqualTo(3);
        
        Iterator<Item> it = items.iterator();
        
        Item firstItem = it.next();
        assertThat(firstItem.getTitle()).isEqualTo("Port wolnocłowy - Łasztownia");
        assertThat(firstItem.getDescr()).isEqualTo("mock data");
        assertThat(firstItem.getStartTime()).isEqualTo(new Date(0L));
        assertThat(firstItem.getEndTime()).isEqualTo(new Date(0L));
        assertThat(firstItem.getUrl()).isEqualTo("http://otwartezabytki.pl/pl/relics?utf8=%E2%9C%93&search[q]=Port%20wolnocłowy%20-%20Łasztownia&search[place]=Szczecin");
        assertThat(firstItem.getLocation()).isEqualTo("53.424689;14.5597174");
        
        Item secondItem = it.next();
        assertThat(secondItem.getTitle()).isEqualTo("Zespół dworski Sienno");
        assertThat(secondItem.getDescr()).isEqualTo("mock data");
        assertThat(secondItem.getStartTime()).isEqualTo(new Date(0L));
        assertThat(secondItem.getEndTime()).isEqualTo(new Date(0L));
        assertThat(secondItem.getUrl()).isEqualTo("http://otwartezabytki.pl/pl/relics?utf8=%E2%9C%93&search[q]=Zespół%20dworski%20Sienno&search[place]=Szczecin");
        assertThat(secondItem.getLocation()).isEqualTo("53.470523;14.566093");
        
        Item thirdItem = it.next();
        assertThat(thirdItem.getTitle()).isEqualTo("Zespół portowej straży pożarnej");
        assertThat(thirdItem.getDescr()).isEqualTo("mock data");
        assertThat(thirdItem.getStartTime()).isEqualTo(new Date(0L));
        assertThat(thirdItem.getEndTime()).isEqualTo(new Date(0L));
        assertThat(thirdItem.getUrl()).isEqualTo("http://otwartezabytki.pl/pl/relics?utf8=%E2%9C%93&search[q]=Zespół%20portowej%20straży%20pożarnej&search[place]=Szczecin");
        assertThat(thirdItem.getLocation()).isEqualTo("53.417576;14.569226");
    }
}
