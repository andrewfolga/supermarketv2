package qmetric.supermarket.domain;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Item {
    private final ItemType itemType;
    private final PriceDefinition priceDefinition;
    private final BigDecimal quantity;

    public Item(final ItemType itemType, final PriceDefinition priceDefinition, final BigDecimal quantity) {
        Validate.validState(quantity.compareTo(BigDecimal.ZERO) >= 0, "Quantity has to be positive");
        this.itemType = itemType;
        this.priceDefinition = priceDefinition;
        this.quantity = quantity;
    }

    public Item(final ItemType itemType, PriceDefinition priceDefinition) {
        this(itemType, priceDefinition, new BigDecimal("1.00"));
    }

    public ItemType getItemType() {
        return itemType;
    }

    public PriceDefinition getPriceDefinition() {
        return priceDefinition;
    }

    public BigDecimal getAmountPerUnit() {
        return priceDefinition.getAmountPerUnit();
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

    public String getDescription() {
        return String.format(getUnit().getDisplayFormat(),
                getItemType().getName(),
                getQuantity(),
                getPriceDefinition().getAmountPerUnit());
    }
}
