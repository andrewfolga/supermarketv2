package qmetric.supermarket.domain.promotion;

import org.apache.commons.lang3.Validate;
import qmetric.supermarket.domain.Item;
import qmetric.supermarket.domain.ItemType;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public abstract class AbstractPromotion implements Function<Item, BigDecimal> {

    protected final BigDecimal triggerQuantity;
    protected final Optional<BigDecimal> targetQuantity;
    protected final Optional<BigDecimal> targetPrice;
    private final ItemType itemType;

    public AbstractPromotion(final BigDecimal triggerQuantity, final Optional<BigDecimal> targetQuantity, final Optional<BigDecimal> targetPrice, final ItemType itemType) {
        Validate.notNull(triggerQuantity, "Trigger quantity must be provided");
        Validate.validState(targetQuantity.isPresent() || targetPrice.isPresent(), "Either target quantity or target price must be provided");
        Validate.validState(targetPrice.isPresent() || triggerQuantity.compareTo(targetQuantity.get()) > 0, "Trigger quantity must be greater than target quantity");
        this.triggerQuantity = triggerQuantity;
        this.targetQuantity = targetQuantity;
        this.targetPrice = targetPrice;
        this.itemType = itemType;
    }

    @Override
    public BigDecimal apply(final Item item) {
        BigDecimal priceToPay = BigDecimal.ZERO;
        final BigDecimal itemQuantity = item.getQuantity();
        if (item.getItemType().equals(itemType) && itemQuantity.compareTo(triggerQuantity) >= 0) {
            final BigDecimal applyTimes = itemQuantity.divide(triggerQuantity, 0, BigDecimal.ROUND_DOWN);
            final BigDecimal applyReminder = itemQuantity.remainder(triggerQuantity);

            priceToPay = getPromotionPrice(item).multiply(getPromotionQuantity(item));

            priceToPay = priceToPay.multiply(applyTimes);
            priceToPay = priceToPay.add(item.getPriceDefinition().getAmountPerUnit().multiply(applyReminder));
        }
        return priceToPay.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public ItemType getItemType() {
        return itemType;
    }

    private BigDecimal getPromotionPrice(final Item item) {
        return targetPrice.orElseGet(() -> item.getPriceDefinition().getAmountPerUnit());
    }

    public BigDecimal getPromotionQuantity(final Item item) {
        return targetQuantity.orElseGet(() -> BigDecimal.ONE);
    }

    public abstract PromotionType getPromotionType();

    public String getDescription() {
        return String.format(getPromotionType().getDisplayFormat(), itemType.getName(), targetPrice.orElseGet(() -> BigDecimal.ZERO));
    }
}
