package io.pp;

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
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author pp
 */
@RunWith(MockitoJUnitRunner.class)
public class ItemRecommenderEJBTest {

    @Mock private Rescorer rescorer;

    private DataSource ds;

    {
        Properties props = new Properties();
        //props.setProperty("driver", "org.hsqldb.jdbcDriver");
        props.setProperty("url", "jdbc:hsqldb:mem:aname");
        props.setProperty("user", "sa");
        props.setProperty("password", "");
        try {
            ds = JDBCDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Spy private DataSource dsSpy = ds;

    @Spy private RecommenderBuilder builder = new ItemRecommenderBuilder();

    @InjectMocks private ItemRecommenderEJB ir;

    @Test
    public void testGetRecsForRealUser() throws Exception {
        assertThat(ir.getRecsFor(4, 10))
            .hasSize(3)
            .extracting("itemID")
                .contains(102L, 103L, 101L)
        ;
    }

    @Test(expected = NoSuchUserException.class)
    public void testGetRecsForAnonymousUser() throws Exception {
        assertThat(ir.getRecsFor(PlusAnonymousUserDataModel.TEMP_USER_ID, 10))
                .hasSize(1)
                .extracting("itemID")
                .contains(101L)
        ;
    }

    @Test
    public void evaluate() throws TasteException {
        RandomUtils.useTestSeed();
        RecommenderEvaluator evaluator =
                new AverageAbsoluteDifferenceRecommenderEvaluator();
        double score = evaluator.evaluate(
                builder, null, ir.recommender.getDataModel(), 0.7, 1.0);
        assertThat(score).isNaN();
    }

    @Before
    public void foo() throws Exception {

        Connection c = ds.getConnection();
        PreparedStatement ps = c.prepareStatement("DROP TABLE IF EXISTS taste_preferences;");
        ps.execute();
        ps = c.prepareStatement("CREATE TABLE taste_preferences (" +
                "user_id BIGINT NOT NULL, " +
                "item_id BIGINT NOT NULL, " +
                "preference REAL NOT NULL, " +
                "PRIMARY KEY (user_id, item_id));");
        ps.execute();
        ps = c.prepareStatement("CREATE INDEX taste_preferences_user_id_index ON taste_preferences (user_id);");
        ps.execute();
        ps = c.prepareStatement("CREATE INDEX taste_preferences_item_id_index ON taste_preferences (item_id);");
        ps.execute();

        ps = c.prepareStatement("INSERT INTO taste_preferences VALUES (?, ?, ?);");
        ps.setLong(1, 1L); ps.setLong(2, 101L); ps.setFloat(3, 5.0f); ps.addBatch();
        ps.setLong(1, 1L); ps.setLong(2, 102L); ps.setFloat(3, 3.0f); ps.addBatch();
        ps.setLong(1, 1L); ps.setLong(2, 103L); ps.setFloat(3, 2.5f); ps.addBatch();
        ps.setLong(1, 2L); ps.setLong(2, 101L); ps.setFloat(3, 2.0f); ps.addBatch();
        ps.setLong(1, 2L); ps.setLong(2, 102L); ps.setFloat(3, 2.5f); ps.addBatch();
        ps.setLong(1, 2L); ps.setLong(2, 103L); ps.setFloat(3, 5.0f); ps.addBatch();
        ps.setLong(1, 2L); ps.setLong(2, 104L); ps.setFloat(3, 2.0f); ps.addBatch();
        ps.setLong(1, 3L); ps.setLong(2, 101L); ps.setFloat(3, 2.5f); ps.addBatch();
        ps.setLong(1, 3L); ps.setLong(2, 104L); ps.setFloat(3, 4.0f); ps.addBatch();
        ps.setLong(1, 3L); ps.setLong(2, 105L); ps.setFloat(3, 4.5f); ps.addBatch();
        ps.setLong(1, 3L); ps.setLong(2, 107L); ps.setFloat(3, 5.0f); ps.addBatch();
        ps.setLong(1, 4L); ps.setLong(2, 101L); ps.setFloat(3, 5.0f); ps.addBatch();
        ps.setLong(1, 4L); ps.setLong(2, 103L); ps.setFloat(3, 3.0f); ps.addBatch();

        ps.executeBatch();

        ir.buildRecommender();
    }
}
