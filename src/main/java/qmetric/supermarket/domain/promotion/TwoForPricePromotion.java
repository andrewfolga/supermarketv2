package qmetric.supermarket.domain.promotion;

import qmetric.supermarket.domain.Item;
import qmetric.supermarket.domain.ItemType;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class TwoForPricePromotion extends Promotion {

    public static final BigDecimal TWO = new BigDecimal("2.00");
    private final BigDecimal triggerQuantity;
    private final BigDecimal targetPrice;

    public TwoForPricePromotion(final BigDecimal targetPrice, final ItemType itemType) {
        super(itemType, PromotionType.TWO_FOR_PRICE);
        this.triggerQuantity = TWO;
        this.targetPrice = targetPrice;
    }

    @Override
    public BigDecimal apply(Item item) {
        BigDecimal priceToPay = BigDecimal.ZERO;
        final BigDecimal itemQuantity = item.getQuantity();
        if (item.getItemType().equals(getItemType())) {
            final BigDecimal applyTimes = itemQuantity.divide(triggerQuantity, 0, BigDecimal.ROUND_DOWN);
            final BigDecimal applyReminder = itemQuantity.remainder(triggerQuantity);

            priceToPay = targetPrice;

            priceToPay = priceToPay.multiply(applyTimes);
            priceToPay = priceToPay.add(item.getAmountPerUnit().multiply(applyReminder));
        }
        return priceToPay.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public Optional<String> getTargetPrice() {
        return Optional.of(targetPrice.toPlainString());
    }

}
