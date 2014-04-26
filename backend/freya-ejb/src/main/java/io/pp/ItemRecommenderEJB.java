package io.pp;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousUserDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.PostgreSQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericRecommendedItem;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author pp
 */
@Singleton(name = "RecommenderEJB")
public class ItemRecommenderEJB {

    @Resource(lookup = "java:jboss/datasources/PostgreSQLDS")
    private DataSource dataSource;

    Recommender recommender;

    @Inject
    private Rescorer rescorer;

    @Inject
    private RecommenderBuilder builder;

    private DataModel model;
    private PlusAnonymousUserDataModel modelWithAnonymous;

    public ItemRecommenderEJB() {}

    @PostConstruct
    void buildRecommender() {
        try {
            model = new ReloadFromJDBCDataModel(new PostgreSQLJDBCDataModel(dataSource));
            modelWithAnonymous = new PlusAnonymousUserDataModel(model);
            setAnonymousPrefs(modelWithAnonymous);
            recommender = builder.buildRecommender(model);
        } catch (TasteException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void setAnonymousPrefs(PlusAnonymousUserDataModel model) {
        PreferenceArray prefs = new GenericUserPreferenceArray(3);
        prefs.setUserID(0, PlusAnonymousUserDataModel.TEMP_USER_ID);
        prefs.setItemID(0, 102L); prefs.setValue(0, 1.0f);
        prefs.setItemID(1, 103L); prefs.setValue(1, 3.0f);
        prefs.setItemID(2, 104L); prefs.setValue(2, 2.0f);
        model.setTempPrefs(prefs);
    }

    /**
     * @throws org.apache.mahout.cf.taste.common.TasteException in case of problems accessing underlying data model
     */
    public  List<RecommendedItem> getRecsFor(long userId, int howMany) throws TasteException {
        PreferenceArray existingPrefs = model.getPreferencesFromUser(userId);
        if (existingPrefs.length() == 0) {
            return recommender.recommend(PlusAnonymousUserDataModel.TEMP_USER_ID, howMany, rescorer.withUserId(userId));
        } else {
            List<RecommendedItem> newRecs = recommender.recommend(userId, howMany, rescorer.withUserId(userId));
            Iterator<Preference> it = existingPrefs.iterator();
            while (it.hasNext() && newRecs.size() <= howMany ) {
                Preference p = it.next();
                newRecs.add(new GenericRecommendedItem(p.getItemID(), p.getValue()));
            }

            Collections.sort(newRecs, new Comparator<RecommendedItem>() {
                @Override
                public int compare(RecommendedItem one, RecommendedItem another) {
                    return (int)(another.getValue() - one.getValue());
                }
            });

            return newRecs;
        }
    }
}
