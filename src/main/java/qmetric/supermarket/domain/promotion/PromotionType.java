package qmetric.supermarket.domain.promotion;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public enum PromotionType {
    THREE_FOR_TWO("%s 3 for 2"), TWO_FOR_PRICE("%s 2 for £%s");

    private final String displayFormat;

    PromotionType(String displayFormat) {
        this.displayFormat = displayFormat;
    }

    public String getDisplayFormat() {
        return displayFormat;
    }
}
