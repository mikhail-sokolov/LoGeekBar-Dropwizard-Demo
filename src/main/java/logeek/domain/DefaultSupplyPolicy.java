package logeek.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msokolov on 10/8/2015.
 */
public class DefaultSupplyPolicy implements SupplyPolicy {
    private List<Storage> storages = new ArrayList<>();

    public DefaultSupplyPolicy(List<Storage> storages) {
        this.storages = storages;
    }

    @Override
    public void apply() {
        storages.stream().forEach(storage -> storage.add());
    }
}
