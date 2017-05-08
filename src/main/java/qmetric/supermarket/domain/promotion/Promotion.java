package qmetric.supermarket.domain.promotion;

import qmetric.supermarket.domain.Item;
import qmetric.supermarket.domain.ItemType;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public abstract class Promotion implements Function<Item, BigDecimal> {

    private final ItemType itemType;
    private final PromotionType promotionType;

    public Promotion(final ItemType itemType, PromotionType promotionType) {
        this.itemType = itemType;
        this.promotionType = promotionType;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getDescription() {
        return String.format(promotionType.getDisplayFormat(), itemType.getName(), getTargetPrice().orElseGet(() -> ""));
    }

    public abstract Optional<String> getTargetPrice();
}
