package logeek;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import logeek.domain.Storage;
import logeek.health.StorageHealthcheck;
import logeek.infrastructure.SupplyBySchedule;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by msokolov on 9/29/2015.
 */
public class LogeekBarConfig extends Configuration {
    @Valid
    @NotNull
    private final Storage.Factory storageFactory = new Storage.Factory();

    @Valid
    @NotNull
    private final StorageHealthcheck.Factory storageHealthCheckFactory = new StorageHealthcheck.Factory();

    @Valid
    @NotNull
    private final SupplyBySchedule.Factory supplyByScheduleFactory = new SupplyBySchedule.Factory();

    @JsonProperty("storage")
    public Storage.Factory getStorageFactory() {
        return storageFactory;
    }

    @JsonProperty("storageHealthCheck")
    public StorageHealthcheck.Factory getStorageHealthCheckFactory() {
        return storageHealthCheckFactory;
    }

    @JsonProperty("supplier")
    public SupplyBySchedule.Factory getSupplyByScheduleFactory() {
        return supplyByScheduleFactory;
    }
}
