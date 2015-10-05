package logeek.domain;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by msokolov on 10/5/2015.
 */
public class Order {
    @NotEmpty
    private final int id;
    @NotEmpty
    private final MenuItem menuItem;

    public Order(int id, MenuItem menuItem) {
        this.id = id;
        this.menuItem = menuItem;
    }

    public int getId() {
        return id;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }
}
