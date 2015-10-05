package logeek.health;

import com.codahale.metrics.health.HealthCheck;
import logeek.domain.Storage;

/**
 * Created by msokolov on 9/29/2015.
 */
public class StorageHealthcheck extends HealthCheck {
    private final int threshold;
    private Storage storage;

    public StorageHealthcheck(Storage storage) {
        this.storage = storage;
        this.threshold = storage.capacity() / 3;
    }

    @Override
    protected Result check() throws Exception {
        int itemsLeft = storage.itemsLeft();
        return (itemsLeft > threshold) ? Result.healthy("Items left: " + itemsLeft) : Result.unhealthy("Almost run out of " + storage.getItemName());
    }
}
