package qmetric.supermarket.domain.promotion;

import qmetric.supermarket.domain.Item;
import qmetric.supermarket.domain.ItemType;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by andrzejfolga on 03/05/2017.
 */
public class PromotionFunction implements Function<Item, BigDecimal> {

    private final BigDecimal triggerQuantity;
    private final Optional<BigDecimal> targetQuantity;
    private final Optional<BigDecimal> targetPrice;
    private final ItemType itemType;
    private final PromotionType promotionType;

    public PromotionFunction(Promotion promotion) {
        this.triggerQuantity = promotion.getTriggerQuantity();
        this.targetQuantity = promotion.getTargetQuantity();
        this.targetPrice = promotion.getTargetPrice();
        this.itemType = promotion.getItemType();
        this.promotionType = promotion.getPromotionType();
    }

    @Override
    public BigDecimal apply(Item item) {
        BigDecimal priceToPay = BigDecimal.ZERO;
        final BigDecimal itemQuantity = item.getQuantity();
        if (item.getItemType().equals(itemType)) {
            final BigDecimal applyTimes = itemQuantity.divide(triggerQuantity, 0, BigDecimal.ROUND_DOWN);
            final BigDecimal applyReminder = itemQuantity.remainder(triggerQuantity);

            priceToPay = getPromotionPrice(item).multiply(getPromotionQuantity(item));

            priceToPay = priceToPay.multiply(applyTimes);
            priceToPay = priceToPay.add(item.getPriceDefinition().getAmountPerUnit().multiply(applyReminder));
        }
        return priceToPay.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    private BigDecimal getPromotionPrice(final Item item) {
        return targetPrice.orElseGet(() -> item.getPriceDefinition().getAmountPerUnit());
    }

    public ItemType getItemType() {
        return itemType;
    }

    private BigDecimal getPromotionQuantity(final Item item) {
        return targetQuantity.orElseGet(() -> BigDecimal.ONE);
    }

    public String getDescription() {
        return String.format(promotionType.getDisplayFormat(), itemType.getName(), targetPrice.orElseGet(() -> BigDecimal.ZERO));
    }
}
