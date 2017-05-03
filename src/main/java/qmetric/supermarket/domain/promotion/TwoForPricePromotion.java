package qmetric.supermarket.domain.promotion;

import qmetric.supermarket.domain.ItemType;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class TwoForPricePromotion extends AbstractPromotion {

    private static final BigDecimal TRIGGER_QUANTITY = new BigDecimal(2);

    public TwoForPricePromotion(final ItemType itemType, final BigDecimal price) {
        super(TRIGGER_QUANTITY, Optional.empty(), Optional.of(price), itemType);
    }

    @Override
    public PromotionType getPromotionType() {
        return PromotionType.TWO_FOR_PRICE;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.COKE;
    }

}
