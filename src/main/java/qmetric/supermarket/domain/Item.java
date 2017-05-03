package qmetric.supermarket.domain;

import java.math.BigDecimal;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Item {
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

    public Unit getUnit() {
        return priceDefinition.getUnit();
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalPrice() {
        return priceDefinition.getAmountPerUnit().multiply(quantity).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public BigDecimal getAmountPerUnit() {
        return priceDefinition.getAmountPerUnit();
    }

    public String getDescription() {
        return String.format(getUnit().getDisplayFormat(),
                getItemType().getName(),
                getQuantity(),
                getPriceDefinition().getAmountPerUnit());
    }
}
