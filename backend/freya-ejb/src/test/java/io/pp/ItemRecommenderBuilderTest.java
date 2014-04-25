package io.pp;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ItemRecommenderBuilderTest {

    @Mock
    private DataModel model;

    @Test
    public void testBuildRecommender() throws Exception {
        ItemRecommenderBuilder builder = new ItemRecommenderBuilder();

        Recommender recr = builder.buildRecommender(model);
        assertThat(recr).isNotNull();

    }

    @Before
    public void foo() throws Exception {
        FastByIDMap<PreferenceArray> preferences =
                new FastByIDMap<PreferenceArray>();
        PreferenceArray prefsForUser1 = new GenericUserPreferenceArray(10);
        prefsForUser1.setUserID(0, 1L);
        prefsForUser1.setItemID(0, 101L);
        prefsForUser1.setValue(0, 3.0f);
        prefsForUser1.setItemID(1, 102L);
        prefsForUser1.setValue(1, 4.5f);
        preferences.put(1L, prefsForUser1);
        model = new GenericDataModel(preferences);
    }
}
