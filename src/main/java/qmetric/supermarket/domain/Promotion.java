package qmetric.supermarket.domain;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Promotion implements Function<Item, BigDecimal> {

    private final BigDecimal triggerQuantity;
    private final Optional<BigDecimal> targetQuantity;
    private final Optional<BigDecimal> targetPrice;
    private final ItemType itemType;
    private final PromotionType promotionType;

    public static Promotion quantityForPrice(BigDecimal triggerQuantity, BigDecimal price, ItemType itemType, PromotionType promotionType) {
        return new Promotion(triggerQuantity, Optional.empty(), Optional.of(price), itemType, promotionType);
    }

    public static Promotion quantityForQuantity(BigDecimal triggerQuantity, BigDecimal targetQuantity, ItemType itemType, PromotionType promotionType) {
        return new Promotion(triggerQuantity, Optional.of(targetQuantity), Optional.empty(), itemType, promotionType);
    }

    public Promotion(final BigDecimal triggerQuantity, final Optional<BigDecimal> targetQuantity, final Optional<BigDecimal> targetPrice, final ItemType itemType, PromotionType promotionType) {
        Validate.notNull(triggerQuantity, "Trigger quantity must be provided");
        Validate.validState(targetQuantity.isPresent() || targetPrice.isPresent(), "Either target quantity or target price must be provided");
        Validate.validState(targetPrice.isPresent() || triggerQuantity.compareTo(targetQuantity.get()) > 0, "Trigger quantity must be greater than target quantity");
        this.triggerQuantity = triggerQuantity;
        this.targetQuantity = targetQuantity;
        this.targetPrice = targetPrice;
        this.itemType = itemType;
        this.promotionType = promotionType;
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
