package logeek.domain;

import com.google.common.base.Optional;

import javax.swing.text.html.Option;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by msokolov on 9/29/2015.
 */
public abstract class Storage<T> {
    protected final int capacity;
    protected final AtomicInteger itemsLeft;
    private ConcurrentLinkedQueue<T> ordersQueue;

    public Storage(int capacity) {
        this.capacity = capacity;
        this.itemsLeft = new AtomicInteger(capacity);
        ordersQueue = new ConcurrentLinkedQueue<>();
    }

    //synch
    public Optional<T> get() {
        if (!isEmpty()) {
            itemsLeft.compareAndSet()decrementAndGet();
            return Optional.of(create());
            Optional.fromNullable(ordersQueue.poll());
        } else {
            return Optional.absent();
        }
    }

    public abstract T create();

    public int capacity() {
        return capacity;
    }

    public int itemsLeft() {
        return itemsLeft.get();
    }

    public boolean isEmpty() {
        return itemsLeft.get() == 0;
    }

    public abstract String getItemName();
}
