package qmetric.supermarket.domain;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public enum ItemType {
    BEANS("Beans"), ORANGES("Oranges"), COKE("Coke");

    private final String name;

    ItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
