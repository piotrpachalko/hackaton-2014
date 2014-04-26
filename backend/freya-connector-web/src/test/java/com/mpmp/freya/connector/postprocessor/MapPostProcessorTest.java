package com.mpmp.freya.connector.postprocessor;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.mockito.Mockito;

import com.mpmp.freya.connector.postprocessor.PostProcessor;
import com.mpmp.iface.model.Item;

public class MapPostProcessorTest {

    @Test
    public void shouldDecoratePostProcessorWithAnotherOne() {
        
        // given
        PostProcessor mockPostProcessor = Mockito.mock(PostProcessor.class);
        PostProcessor mapPostProcessor = new MapPostProcessor(mockPostProcessor);
        
        Collection<Item> items = new ArrayList<Item>();
        
        // when
        items = mapPostProcessor.postProcess(items);
        
        // then
        Mockito.verify(mockPostProcessor).postProcess(items);
    }
    
}
