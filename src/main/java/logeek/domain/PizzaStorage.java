package logeek.domain;

/**
 * Created by msokolov on 9/29/2015.
 */
public class PizzaStorage extends Storage<Pizza> {
    public PizzaStorage(int capacity) {
        super(capacity);
    }

    @Override
    public Pizza create() {
        return new Pizza();
    }

    @Override
    public String getItemName() {
        return Pizza.class.getSimpleName();
    }
}


