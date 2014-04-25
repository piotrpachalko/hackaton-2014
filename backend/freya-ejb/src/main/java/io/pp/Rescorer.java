package io.pp;

import com.mpmp.iface.model.Item;
import com.mpmp.iface.service.ItemDAO;
import org.apache.mahout.cf.taste.recommender.IDRescorer;

import javax.inject.Inject;

/**
 * @author pp
 */
public class Rescorer implements IDRescorer {

    @Inject
    private ItemDAO itemDAO;

    // Chain of responsiblity to handle rescoring
    final private RescorerChain chain;

    long userId;

    private Rescorer() {
        RescorerChain c0 = new TimeBasedRescorer();
        RescorerChain c1 = new LocationNearRescorer();
        // TODO: more chain elements
        c0.setNext(c1);
        c1.setNext(null);
        chain = c0;
    }

    public IDRescorer withUserId(long id) {
        this.userId = id;
        return this;
    }

    @Override
    public boolean isFiltered(long id) {
        Item item = itemDAO.findById(id); // possibly hi cost op
        return chain.handleFiltering(item);
    }

    @Override
    public double rescore(long id, double originalScore) {
        Item item = itemDAO.findById(id); // possibly hi cost op
        return chain.handleRescoring(item, originalScore);
    }

    public static abstract class RescorerChain  {

        // The next element in the chain of responsibility
        private RescorerChain next;

        protected void setNext(RescorerChain element) {
            this.next = element;
        }

        private double handleRescoring(Item item, double originalScore) {
            double score = rescore(item, originalScore);
            return next == null ? score : next.handleRescoring(item, score);
        }

        protected boolean handleFiltering(Item item) {
            return isFiltered(item) || (next == null ? false : next.handleFiltering(item));
        }

        protected abstract double rescore(Item item, double originalScore);

        protected abstract boolean isFiltered(Item item);
    }

    private static final class TimeBasedRescorer extends RescorerChain {

        @Override
        protected double rescore(Item item, double originalScore) {
            // TODO: implement me
            return originalScore;
        }

        @Override
        protected boolean isFiltered(Item item) {
            return notEnoughTimeFor(item);
        }

        private boolean notEnoughTimeFor(Item item) {
            // TODO: implement me
            return false;
        }
    }

    private final class LocationNearRescorer extends RescorerChain {

        @Override
        protected double rescore(Item item, double originalScore) {
            // TODO: implement me
            return originalScore * distanceFactor(item);
        }

        private double distanceFactor(Item item) {
            // TODO: implement me
            return 1;
        }

        @Override
        protected boolean isFiltered(Item item) {
            return tooFarAway(item);
        }

        private boolean tooFarAway(Item item) {
            // TODO: implement me
            return false;
        }
    }

}
