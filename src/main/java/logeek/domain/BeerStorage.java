package logeek.domain;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by msokolov on 9/29/2015.
 */
public class BeerStorage extends Storage<Beer> {
    public BeerStorage(int capacity) {
        super(capacity);
    }

    @Override
    public Beer create() {
        return new Beer();
    }

    @Override
    public String getItemName() {
        return Beer.class.getSimpleName();
    }
}
