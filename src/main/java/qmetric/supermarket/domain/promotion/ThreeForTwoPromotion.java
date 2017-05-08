package qmetric.supermarket.domain.promotion;

import qmetric.supermarket.domain.Item;
import qmetric.supermarket.domain.ItemType;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ThreeForTwoPromotion extends Promotion {

    public static final BigDecimal THREE = new BigDecimal("3.00");
    public static final BigDecimal TWO = new BigDecimal("2.00");
    private final BigDecimal triggerQuantity;
    private final BigDecimal targetQuantity;

    public ThreeForTwoPromotion(final ItemType itemType) {
        super(itemType, PromotionType.THREE_FOR_TWO);
        this.triggerQuantity = THREE;
        this.targetQuantity = TWO;
    }

    @Override
    public BigDecimal apply(Item item) {
        BigDecimal priceToPay = BigDecimal.ZERO;
        final BigDecimal itemQuantity = item.getQuantity();
        if (item.getItemType().equals(getItemType())) {
            final BigDecimal applyTimes = itemQuantity.divide(triggerQuantity, 0, BigDecimal.ROUND_DOWN);
            final BigDecimal applyReminder = itemQuantity.remainder(triggerQuantity);

            priceToPay = item.getAmountPerUnit().multiply(targetQuantity);

            priceToPay = priceToPay.multiply(applyTimes);
            priceToPay = priceToPay.add(item.getPriceDefinition().getAmountPerUnit().multiply(applyReminder));
        }
        return priceToPay.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public Optional<String> getTargetPrice() {
        return Optional.empty();
    }
}
