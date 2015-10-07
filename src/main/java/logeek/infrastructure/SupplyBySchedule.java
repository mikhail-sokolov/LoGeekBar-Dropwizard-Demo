package logeek.infrastructure;

import io.dropwizard.lifecycle.Managed;
import logeek.domain.ItemSupplier;
import logeek.domain.SupplyPolicy;

import javax.validation.constraints.Min;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by msokolov on 10/8/2015.
 */
public class SupplyBySchedule implements ItemSupplier, Managed{
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture handle;
    private SupplyPolicy supplyPolicy;
    private long delay;

    public SupplyBySchedule(long delay, SupplyPolicy policy) {
        this.delay = delay;
        this.supplyPolicy = policy;
    }

    @Override
    public void startSupplying() {
        handle = scheduler.scheduleAtFixedRate(
                () -> supplyPolicy.apply(),
                60L,
                delay,
                TimeUnit.SECONDS
        );
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() throws Exception {
        handle.cancel(true);
    }

    public static class Factory {
        @Min(5)
        private long delayInSeconds;

        public long getDelayInSeconds() {
            return delayInSeconds;
        }

        public void setDelayInSeconds(long delayInSeconds) {
            this.delayInSeconds = delayInSeconds;
        }

        public ItemSupplier supplier(SupplyPolicy policy) {
            return new SupplyBySchedule(delayInSeconds, policy);
        }
    }
}
