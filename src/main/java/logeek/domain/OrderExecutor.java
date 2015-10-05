package logeek.domain;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by msokolov on 10/5/2015.
 */
public class OrderExecutor {
    private ConcurrentLinkedQueue<Order> ordersQueue;
    private BeerStorage beerStorage;
    private PizzaStorage pizzaStorage;

    public OrderExecutor(ConcurrentLinkedQueue<Order> ordersQueue, BeerStorage beerStorage, PizzaStorage pizzaStorage) {
        this.ordersQueue = ordersQueue;
        this.beerStorage = beerStorage;
        this.pizzaStorage = pizzaStorage;
    }

    public OrderAck proccess(Order order) {
        switch (order.getMenuItem()) {
            case BEER: beerStorage.get();
                break;
            case PIZZA: pizzaStorage.get();
            default:
                throw new IllegalArgumentException("Unknown menu item");
        }
        ordersQueue.add(order);
        return new OrderAck(order.getMenuItem());
    }

    public TotalOrder totalOrderById(int id) {
        Stream<Order>ordersById = ordersQueue.stream().filter(o -> o.getId() == id);
        return new TotalOrder(
                id,
                ordersById.filter(o -> o.getMenuItem().equals(MenuItem.BEER)).count(),
                ordersById.filter(o -> o.getMenuItem().equals(MenuItem.PIZZA)).count()
        );
    }


    public List<TotalOrder> totalOrder() {
        Map<Integer, List<Order>> ordersById = ordersQueue.stream().collect(Collectors.groupingBy(Order::getId));
        return ordersById.entrySet().stream().map(
                entry -> {
                    Map<MenuItem, List<Order>> byMenuItem = entry.getValue().stream().collect(Collectors.groupingBy(Order::getMenuItem));
                    return new TotalOrder(
                        entry.getKey(),
                        byMenuItem.getOrDefault(MenuItem.BEER, Collections.emptyList()).size(),
                        byMenuItem.getOrDefault(MenuItem.PIZZA, Collections.emptyList()).size()
                    );
                }
        ).collect(Collectors.toList());
    }
}
