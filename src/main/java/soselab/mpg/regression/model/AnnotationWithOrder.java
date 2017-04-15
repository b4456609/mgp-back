package soselab.mpg.regression.model;

/**
 * Created by bernie on 2017/4/15.
 */
public class AnnotationWithOrder implements PriorityOrder {
    private int order;
    private String id;

    public AnnotationWithOrder(int order, String id) {
        this.order = order;
        this.id = id;
    }


    public String getId() {
        return id;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
