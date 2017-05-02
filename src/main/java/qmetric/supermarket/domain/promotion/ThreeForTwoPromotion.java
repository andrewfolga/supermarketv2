package qmetric.supermarket.domain.promotion;

import qmetric.supermarket.domain.ItemType;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ThreeForTwoPromotion extends Promotion {

    private static final BigDecimal TRIGGER_QUANTITY = new BigDecimal(3);
    private static final BigDecimal TARGET_QUANTITY = new BigDecimal(2);

    public ThreeForTwoPromotion(ItemType itemType) {
        super(TRIGGER_QUANTITY, Optional.of(TARGET_QUANTITY), Optional.empty(), itemType);
    }

    @Override
    public PromotionType getPromotionType() {
        return PromotionType.THREE_FOR_TWO;
    }

}
