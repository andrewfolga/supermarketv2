package qmetric.supermarket.domain;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public enum Unit {
    ITEM("%s"), KG("%s\n%5.3f kg @ £5.2f/kg");

    private String displayFormat;

    Unit(String displayFormat) {
        this.displayFormat = displayFormat;
    }

    public String getDisplayFormat() {
        return displayFormat;
    }
}
