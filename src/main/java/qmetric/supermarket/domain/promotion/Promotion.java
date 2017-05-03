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
public class Promotion {

    private final BigDecimal triggerQuantity;
    private final Optional<BigDecimal> targetQuantity;
    private final Optional<BigDecimal> targetPrice;
    private final ItemType itemType;
    private final PromotionType promotionType;

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

    public static Promotion quantityForPrice(BigDecimal triggerQuantity, BigDecimal price, ItemType itemType, PromotionType promotionType) {
        return new Promotion(triggerQuantity, Optional.empty(), Optional.of(price), itemType, promotionType);
    }

    public static Promotion quantityForQuantity(BigDecimal triggerQuantity, BigDecimal targetQuantity, ItemType itemType, PromotionType promotionType) {
        return new Promotion(triggerQuantity, Optional.of(targetQuantity), Optional.empty(), itemType, promotionType);
    }

    public ItemType getItemType() {
        return itemType;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public BigDecimal getTriggerQuantity() {
        return triggerQuantity;
    }

    public Optional<BigDecimal> getTargetQuantity() {
        return targetQuantity;
    }

    public Optional<BigDecimal> getTargetPrice() {
        return targetPrice;
    }

}
