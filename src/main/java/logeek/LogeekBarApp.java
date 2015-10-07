package logeek;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import logeek.domain.*;
import logeek.health.StorageHealthcheck;
import logeek.infrastructure.OnDemandSupplyPolicy;
import logeek.infrastructure.SupplyBySchedule;
import logeek.resource.OrderResource;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by msokolov on 9/29/2015.
 */
public class LogeekBarApp extends Application<LogeekBarConfig> {
    @Override
    public void initialize(Bootstrap<LogeekBarConfig> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public void run(LogeekBarConfig logeekBarConfig, Environment environment) throws Exception {
        Storage.Factory storageFactory = logeekBarConfig.getStorageFactory();
        Storage beerStorage = storageFactory.beerStorage();
        Storage pizzaStorage = storageFactory.pizzaStorage();

        StorageHealthcheck.Factory healthCheckFactory = logeekBarConfig.getStorageHealthCheckFactory();
        StorageHealthcheck beerStorageCheck = healthCheckFactory.healthcheckOf(beerStorage);
        StorageHealthcheck pizzaStorageCheck = healthCheckFactory.healthcheckOf(pizzaStorage);

        //SupplyPolicy policy = new DefaultSupplyPolicy(Arrays.asList(beerStorage, pizzaStorage));
        SupplyPolicy policy = new OnDemandSupplyPolicy(Arrays.asList(beerStorageCheck, pizzaStorageCheck));
        ItemSupplier itemSupplier = logeekBarConfig.getSupplyByScheduleFactory().supplier(policy);
        itemSupplier.startSupplying();

        OrderExecutor orderExecutor = new OrderExecutor(new ConcurrentLinkedQueue<>(), beerStorage, pizzaStorage);

        environment.jersey().register(new OrderResource(orderExecutor));
        environment.healthChecks().register("Beer storage check", beerStorageCheck);
        environment.healthChecks().register("Pizza storage check", pizzaStorageCheck);
        environment.lifecycle().manage((Managed)itemSupplier);
    }

    public static void main(String[] args) throws Exception {
        new LogeekBarApp().run(args);
    }
}
