package com.mpmp.freya.connector.parser;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import org.fest.assertions.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.mpmp.freya.connector.commons.QueryParameters;
import com.mpmp.freya.connector.commons.WebAddress;
import com.mpmp.freya.connector.fetcher.HtmlFetcher;
import com.mpmp.iface.model.Item;

public class HotParserTest {

    @Test
    @Ignore
    public void shouldParseNames() {
        
        // given
        HtmlFetcher mockFetcher = Mockito.mock(HtmlFetcher.class);
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("simple.html");
        String response = new Scanner(is).useDelimiter("//Z").next();
        Document document = Jsoup.parse(response);
        Mockito.when(mockFetcher.retrieve(Mockito.any(WebAddress.class), Mockito.any(QueryParameters.class))).thenReturn(document);

        Parser hotParser = new HotParser(mockFetcher);
        
        // when
        Collection<Item> items = hotParser.getItems();
        
        // then
        Assertions.assertThat(items.size()).isEqualTo(15);
        
        Iterator<Item> it = items.iterator();
        
        Item item1 = it.next();
        Assertions.assertThat(item1.getTitle()).isEqualTo("Enemef: Psychonoc.");
        
        Item item2 = it.next();
        Assertions.assertThat(item2.getTitle()).isEqualTo("Festiwal Kids Love De.");

        Item item3 = it.next();
        Assertions.assertThat(item3.getTitle()).isEqualTo("Red Bull Tour Bus Gł.");
        
        Item item4 = it.next();
        Assertions.assertThat(item4.getTitle()).isEqualTo("Szczecin Street Art F.");
        
        Item item5 = it.next();
        Assertions.assertThat(item5.getTitle()).isEqualTo("IV Festiwal TEK...");
        
        Item item6 = it.next();
        Assertions.assertThat(item6.getTitle()).isEqualTo("ATB.");
        
        Item item7 = it.next();
        Assertions.assertThat(item7.getTitle()).isEqualTo("Canalia Night 17: Mar.");
        
        Item item8 = it.next();
        Assertions.assertThat(item8.getTitle()).isEqualTo("Fresh Boogie Da.");
        
        Item item9 = it.next();
        Assertions.assertThat(item9.getTitle()).isEqualTo("Juwenalia.");
        
        Item item10 = it.next();
        Assertions.assertThat(item10.getTitle()).isEqualTo("Olivia Anna Liv...");

        Item item11 = it.next();
        Assertions.assertThat(item11.getTitle()).isEqualTo("Ania Szarmach i Frank.");

        Item item12 = it.next();
        Assertions.assertThat(item12.getTitle()).isEqualTo("Cisza Jak Ta Koncert.");

        Item item13 = it.next();
        Assertions.assertThat(item13.getTitle()).isEqualTo("Koncert MaBaSo Trio.");

        Item item14 = it.next();
        Assertions.assertThat(item14.getTitle()).isEqualTo("Koncert Piersi.");

        Item item15 = it.next();
        Assertions.assertThat(item15.getTitle()).isEqualTo("Koncert Paluch...");
    }
    
}
