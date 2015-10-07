package logeek.infrastructure;

import logeek.domain.SupplyPolicy;
import logeek.health.StorageHealthcheck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msokolov on 10/8/2015.
 */
public class OnDemandSupplyPolicy implements SupplyPolicy {
    private List<StorageHealthcheck> healthchecks = new ArrayList<>();

    public OnDemandSupplyPolicy(List<StorageHealthcheck> healthchecks) {
        this.healthchecks = healthchecks;
    }

    @Override
    public void apply() {
        healthchecks.stream().filter(check -> !check.execute().isHealthy()).forEach(check -> check.getStorage().add());
    }
}
