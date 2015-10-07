package logeek.domain;

import com.google.common.base.Optional;

import javax.swing.text.html.Option;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by msokolov on 9/29/2015.
 */
public abstract class Storage<T> {
    private ConcurrentLinkedQueue<T> storage;
    private Supplier<T> newItem;

    protected Storage(Collection<T> initial, Supplier<T> newItemSupplier) {
        storage = new ConcurrentLinkedQueue<>(initial);
        newItem = newItemSupplier;
    }

    public Optional<T> get() {
        return Optional.fromNullable(storage.poll());
    }

    public void add() {
        storage.add(newItem.get());
    }

    public int itemsLeft() {
        return storage.size();
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    public abstract String getItemName();

    public static class Factory {
        @Min(value = 1)
        private int beerInit;
        @Min(value = 1)
        private int pizzaInit;

        public int getBeerInit() {
            return beerInit;
        }

        public void setBeerInit(int beerInit) {
            this.beerInit = beerInit;
        }

        public int getPizzaInit() {
            return pizzaInit;
        }

        public void setPizzaInit(int pizzaInit) {
            this.pizzaInit = pizzaInit;
        }

        public Storage beerStorage() {
            return new Storage(supplies(beerInit, Beer::new), Beer::new) {
                @Override
                public String getItemName() {
                    return null;
                }
            };
        }

        public Storage pizzaStorage() {
            return new Storage(supplies(pizzaInit, Pizza::new), Pizza::new) {
                @Override
                public String getItemName() {
                    return null;
                }
            };
        }

        private <T> Collection<T> supplies(int amount, Supplier<T> supplier) {
            return IntStream.rangeClosed(1, amount).mapToObj(i -> supplier.get()).collect(Collectors.toList());
        }
    }
}
