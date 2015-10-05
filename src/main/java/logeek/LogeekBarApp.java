package logeek;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import logeek.domain.BeerStorage;
import logeek.domain.PizzaStorage;
import logeek.health.StorageHealthcheck;
import logeek.resource.OrderResource;

/**
 * Created by msokolov on 9/29/2015.
 */
public class LogeekBarApp extends Application<LogeekBarConfig> {
    @Override
    public void initialize(Bootstrap<LogeekBarConfig> bootstrap) {
        super.initialize(bootstrap);
    }

    @Override
    public void run(LogeekBarConfig logeekBarConfig, Environment environment) throws Exception {
        BeerStorage beerStorage = new BeerStorage(10);
        PizzaStorage pizzaStorage = new PizzaStorage(10);
        environment.jersey().register(new OrderResource(beerStorage, pizzaStorage));
        environment.healthChecks().register("Beer storage check", new StorageHealthcheck(beerStorage));
        environment.healthChecks().register("Pizza storage check", new StorageHealthcheck(pizzaStorage));
    }

    public static void main(String[] args) throws Exception {
        new LogeekBarApp().run(args);
    }
}
