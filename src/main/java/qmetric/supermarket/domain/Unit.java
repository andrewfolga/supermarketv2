package qmetric.supermarket.domain;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public enum Unit {
    ITEM("%s"), KG("%s\n%5.3f kg @ £%5.2f/kg"), L("%s\n%5.3f l @ £%5.2f/l");

    private String displayFormat;

    Unit(final String displayFormat) {
        this.displayFormat = displayFormat;
    }

    public String getDisplayFormat() {
        return displayFormat;
    }
}
