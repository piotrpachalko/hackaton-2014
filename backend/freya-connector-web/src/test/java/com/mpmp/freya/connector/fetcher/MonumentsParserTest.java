package com.mpmp.freya.connector.fetcher;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

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
    public void shouldGetItemsForOnePage() throws FileNotFoundException, IOException, ParseException {
        
        // given
        WebAddress listAddress = new WebAddress("http://otwartezabytki.pl/api/v1/relics.json");
        Parameter place = new Parameter("place", "szczecin");
        Parameter page = new Parameter("page", "1");
        QueryParameters params = new QueryParameters(place, page);
        
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
    
    @Test
    public void shouldGetItemsForMultiplePages() throws IOException, ParseException {

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

        // when
        Collection<Item> items = parser.getItems();
        
        // then
        assertThat(items.size()).isEqualTo(6);
        
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
        
        Item fourthItem = it.next();
        assertThat(fourthItem.getTitle()).isEqualTo("Zespół kościoła, obecnie kościół p.w. NMP Matki Kościoła");
        assertThat(fourthItem.getDescr()).isEqualTo("mock data");
        assertThat(fourthItem.getStartTime()).isEqualTo(new Date(0L));
        assertThat(fourthItem.getEndTime()).isEqualTo(new Date(0L));
        assertThat(fourthItem.getUrl()).isEqualTo("http://otwartezabytki.pl/pl/relics?utf8=%E2%9C%93&search[q]=Zespół%20kościoła,%20obecnie%20kościół%20p.w.%20NMP%20Matki%20Kościoła&search[place]=Szczecin");
        assertThat(fourthItem.getLocation()).isEqualTo("53.34113;14.75055");
        
        Item fifthItem = it.next();
        assertThat(fifthItem.getTitle()).isEqualTo("Zespół kościoła par. pw. Niepokalanego Serca Marii");
        assertThat(fifthItem.getDescr()).isEqualTo("mock data");
        assertThat(fifthItem.getStartTime()).isEqualTo(new Date(0L));
        assertThat(fifthItem.getEndTime()).isEqualTo(new Date(0L));
        assertThat(fifthItem.getUrl()).isEqualTo("http://otwartezabytki.pl/pl/relics?utf8=%E2%9C%93&search[q]=Zespół%20kościoła%20par.%20pw.%20Niepokalanego%20Serca%20Marii&search[place]=Szczecin");
        assertThat(fifthItem.getLocation()).isEqualTo("53.493723;14.6034425");

        Item sixthItem = it.next();
        assertThat(sixthItem.getTitle()).isEqualTo("Budynki z kompleksu d. fabryki Stoewera");
        assertThat(sixthItem.getDescr()).isEqualTo("mock data");
        assertThat(sixthItem.getStartTime()).isEqualTo(new Date(0L));
        assertThat(sixthItem.getEndTime()).isEqualTo(new Date(0L));
        assertThat(sixthItem.getUrl()).isEqualTo("http://otwartezabytki.pl/pl/relics?utf8=%E2%9C%93&search[q]=Budynki%20z%20kompleksu%20d.%20fabryki%20Stoewera&search[place]=Szczecin");
        assertThat(sixthItem.getLocation()).isEqualTo("53.445869;14.545044");
    }
}
