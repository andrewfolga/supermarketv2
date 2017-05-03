package qmetric.supermarket.domain.promotion;

import qmetric.supermarket.domain.ItemType;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ThreeForTwoPromotion extends AbstractPromotion {

    private static final BigDecimal TRIGGER_QUANTITY = new BigDecimal("3.00");
    private static final BigDecimal TARGET_QUANTITY = new BigDecimal("2.00");

    public ThreeForTwoPromotion(final ItemType itemType) {
        super(TRIGGER_QUANTITY, Optional.of(TARGET_QUANTITY), Optional.empty(), itemType);
    }

    @Override
    public PromotionType getPromotionType() {
        return PromotionType.THREE_FOR_TWO;
    }

}
