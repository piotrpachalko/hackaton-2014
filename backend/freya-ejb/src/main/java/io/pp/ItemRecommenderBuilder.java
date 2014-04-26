package io.pp;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

/**
 * @author pp
 */
public class ItemRecommenderBuilder implements RecommenderBuilder {

    @Override
    public Recommender buildRecommender(DataModel dataModel) throws TasteException {

        // build data model and similarity model

        ItemSimilarity is;
        try {
            is = new PearsonCorrelationSimilarity(dataModel);
        } catch (TasteException e) {
            throw new IllegalArgumentException(e);
        }

        // build recommender

        // algo:
        // for every item i that u has no preference for yet
        //   for every item j that u has a preference for
        //     compute a similarity s between i and j
        //     add u's preference for j, weighted by s, to a running average
        //     return the top items, ranked by weighted average

        return new GenericItemBasedRecommender(dataModel, is);
    }
}
