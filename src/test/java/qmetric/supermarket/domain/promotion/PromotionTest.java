package qmetric.supermarket.domain.promotion;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import qmetric.supermarket.domain.Item;
import qmetric.supermarket.domain.ItemType;
import qmetric.supermarket.domain.PriceDefinition;
import qmetric.supermarket.domain.Unit;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by andrzejfolga on 02/05/2017.
 */
public class PromotionTest {

    public static final String TOTAL_TO_PAY = "Total to Pay";
    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    private static final BigDecimal TRIGGER_QUANTITY_2 = new BigDecimal("2.0");
    private static final BigDecimal TRIGGER_QUANTITY_3 = new BigDecimal("3.0");
    private static final BigDecimal TRIGGER_QUANTITY_4 = new BigDecimal("4.0");
    private static final Optional<BigDecimal> TARGET_QUANTITY_2 = Optional.of(new BigDecimal("2.0"));
    private static final Optional<BigDecimal> TARGET_QUANTITY_3 = Optional.of(new BigDecimal("3.0"));
    private static final Optional<BigDecimal> TARGET_QUANTITY_EMPTY = Optional.empty();
    private static final Optional<BigDecimal> TARGET_PRICE_EMPTY = Optional.empty();
    private static final Optional<BigDecimal> TARGET_PRICE_1 = Optional.of(new BigDecimal("1.0"));

    @Test(expected = NullPointerException.class)
    public void shouldNotApplyPromotionIfTriggerQuantityNotProvided() {
        buildPromotion(null, TARGET_QUANTITY_2, TARGET_PRICE_EMPTY, ItemType.BEANS, PromotionType.TWO_FOR_PRICE);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotApplyPromotionIfTargetQuantityLowerThanTriggerQuantity() {
        buildPromotion(TRIGGER_QUANTITY_3, TARGET_QUANTITY_3, TARGET_PRICE_EMPTY, ItemType.BEANS, PromotionType.THREE_FOR_TWO);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotApplyPromotionIfTriggerQuantityProvidedAndNeitherTargetQuantityNorTargetPrice() {
        buildPromotion(TRIGGER_QUANTITY_3, TARGET_QUANTITY_EMPTY, TARGET_PRICE_EMPTY, ItemType.BEANS, PromotionType.THREE_FOR_TWO);
    }

    @Test
    public void shouldNotApply3For2PromotionForDifferentItemType() {
        Promotion promotion = buildPromotion(TRIGGER_QUANTITY_3, TARGET_QUANTITY_2, TARGET_PRICE_EMPTY, ItemType.BEANS, PromotionType.THREE_FOR_TWO);
        Item item = new Item(ItemType.COKE, new PriceDefinition(new BigDecimal("2.00"), Unit.ITEM), new BigDecimal("5.00"));

        BigDecimal totalToPay = promotion.apply(item);

        softly.assertThat(totalToPay).as(TOTAL_TO_PAY).isEqualTo(BigDecimal.ZERO.setScale(2));
    }

    @Test
    public void shouldApply3For2Promotion() {
        Promotion promotion = buildPromotion(TRIGGER_QUANTITY_3, TARGET_QUANTITY_2, TARGET_PRICE_EMPTY, ItemType.BEANS, PromotionType.THREE_FOR_TWO);
        Item item = new Item(ItemType.BEANS, new PriceDefinition(new BigDecimal("2.00"), Unit.ITEM), new BigDecimal("5.00"));

        BigDecimal totalToPay = promotion.apply(item);

        softly.assertThat(totalToPay).as(TOTAL_TO_PAY).isEqualTo(new BigDecimal("8.00"));
    }

    @Test
    public void shouldApply2ForPricePromotion() {
        Promotion promotion = buildPromotion(TRIGGER_QUANTITY_2, TARGET_QUANTITY_EMPTY, TARGET_PRICE_1, ItemType.COKE, PromotionType.TWO_FOR_PRICE);
        Item item = new Item(ItemType.COKE, new PriceDefinition(new BigDecimal("1.00"), Unit.ITEM), new BigDecimal("4.0"));

        BigDecimal totalToPay = promotion.apply(item);

        softly.assertThat(totalToPay).as(TOTAL_TO_PAY).isEqualTo(new BigDecimal("2.00"));
    }

    @Test
    public void shouldApply4For3Promotion() {
        Promotion promotion = buildPromotion(TRIGGER_QUANTITY_4, TARGET_QUANTITY_3, TARGET_PRICE_EMPTY, ItemType.ORANGES, PromotionType.TWO_FOR_PRICE);
        Item item = new Item(ItemType.ORANGES, new PriceDefinition(new BigDecimal("1.00"), Unit.ITEM), new BigDecimal("6.0"));

        BigDecimal totalToPay = promotion.apply(item);

        softly.assertThat(totalToPay).as(TOTAL_TO_PAY).isEqualTo(new BigDecimal("5.00"));
    }

    private Promotion buildPromotion(final BigDecimal triggerQuantity, final Optional<BigDecimal> targetQuantity, final Optional<BigDecimal> targetPrice, final ItemType itemType, final PromotionType promotionType) {
        return new Promotion(triggerQuantity, targetQuantity, targetPrice, itemType, promotionType);
    }

}