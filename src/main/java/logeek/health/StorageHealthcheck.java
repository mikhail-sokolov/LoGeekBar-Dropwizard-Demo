package logeek.health;

import com.codahale.metrics.health.HealthCheck;
import logeek.domain.Storage;

import javax.validation.constraints.Min;

/**
 * Created by msokolov on 9/29/2015.
 */
public class StorageHealthcheck extends HealthCheck {
    private final int threshold;
    private Storage storage;

    protected StorageHealthcheck(Storage storage, int threshold) {
        this.storage = storage;
        this.threshold = threshold;
    }

    @Override
    protected Result check() throws Exception {
        int itemsLeft = storage.itemsLeft();
        return (itemsLeft > threshold)
                ? Result.healthy("Items left: " + itemsLeft)
                : Result.unhealthy("Almost run out of " + storage.getItemName() + ", Items left: " + itemsLeft);
    }

    public Storage getStorage() {
        return storage;
    }

    public static class Factory {
        @Min(1)
        private int threshold;

        public int getThreshold() {
            return threshold;
        }

        public void setThreshold(int threshold) {
            this.threshold = threshold;
        }

        public StorageHealthcheck healthcheckOf(Storage storage) {
            return new StorageHealthcheck(storage, threshold);
        }
    }
}
