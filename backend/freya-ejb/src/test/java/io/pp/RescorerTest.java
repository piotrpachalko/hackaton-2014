package io.pp;

import com.mpmp.iface.model.Item;
import com.mpmp.iface.service.ItemDAO;
import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.common.RandomUtils;
import org.hsqldb.jdbc.JDBCDataSourceFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author pp
 */
@RunWith(MockitoJUnitRunner.class)
public class RescorerTest {

    @Mock private ItemDAO itemDAO;

    @InjectMocks private Rescorer rescorer;

    @Test
    public void testIsFiltered() throws Exception {
        assertThat(rescorer.isFiltered(777)).isFalse();
    }

    @Test
    public void testGetRecsForAnonymousUser() throws Exception {
        assertThat(rescorer.rescore(777, 5.0f)).isEqualTo(5.0f);
    }

    @Before
    public void foo() throws Exception {
        when(itemDAO.findById(777L)).thenReturn(new Item(777L, "title", "desc", new Date(), new Date(), "745682375N", "url"));
    }
}
