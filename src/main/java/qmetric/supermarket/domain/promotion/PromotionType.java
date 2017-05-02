package qmetric.supermarket.domain.promotion;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public enum PromotionType {
    THREE_FOR_TWO("3 for 2"), TWO_FOR_PRICE("2 for %s");

    private final String name;

    PromotionType(String name) {
        this.name = name;
    }

    public String getDisplayFormat() {
        return name;
    }
}
