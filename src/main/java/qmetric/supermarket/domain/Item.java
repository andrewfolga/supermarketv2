package qmetric.supermarket.domain;

import java.math.BigDecimal;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Item {
    private static final String DESCRIPTION_FORMAT = "%s%s";
    private static final String KG_FORMAT = "%5.3f kg @ £5.2f/kg";
    private final ItemType itemType;
    private final PriceDefinition priceDefinition;
    private final BigDecimal quantity;

    public Item(ItemType itemType, PriceDefinition priceDefinition, BigDecimal quantity) {
        this.itemType = itemType;
        this.priceDefinition = priceDefinition;
        this.quantity = quantity;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public PriceDefinition getPriceDefinition() {
        return priceDefinition;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalPrice() {
        return priceDefinition.getAmountPerUnit().multiply(quantity);
    }

    public BigDecimal getAmountPerUnit() {
        return priceDefinition.getAmountPerUnit();
    }

    public String getDescription() {
        return String.format(DESCRIPTION_FORMAT,
                getItemType(),
                getPriceDefinition().getUnit() == Unit.KG ? String.format(KG_FORMAT, getQuantity(), getPriceDefinition().getAmountPerUnit()) : "");
    }
}
