package qmetric.domain.promotion;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import qmetric.supermarket.domain.Item;
import qmetric.supermarket.domain.ItemType;
import qmetric.supermarket.domain.PriceDefinition;
import qmetric.supermarket.domain.Unit;
import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.PromotionType;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by andrzejfolga on 02/05/2017.
 */
public class PromotionTest {

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
    public void shouldNotApplyPromotionIfTriggerQuantityNotProvided() throws Exception {
        buildPromotion(null, TARGET_QUANTITY_2, TARGET_PRICE_EMPTY, ItemType.BEANS);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotApplyPromotionIfTargetQuantityLowerThanTriggerQuantity() throws Exception {
        buildPromotion(TRIGGER_QUANTITY_3, TARGET_QUANTITY_3, TARGET_PRICE_EMPTY, ItemType.BEANS);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotApplyPromotionIfTriggerQuantityProvidedAndNeitherTargetQuantityNorTargetPrice() throws Exception {
        buildPromotion(TRIGGER_QUANTITY_3, TARGET_QUANTITY_EMPTY, TARGET_PRICE_EMPTY, ItemType.BEANS);
    }

    @Test
    public void shouldNotApply3For2PromotionForDifferentItemType() throws Exception {
        Promotion promotion = buildPromotion(TRIGGER_QUANTITY_3, TARGET_QUANTITY_2, TARGET_PRICE_EMPTY, ItemType.BEANS);
        Item item = new Item(ItemType.COKE, new PriceDefinition(new BigDecimal("2.00"), Unit.ITEM), new BigDecimal("5.00"));

        BigDecimal totalToPay = promotion.apply(item);

        softly.assertThat(totalToPay).as("Total to Pay").isEqualTo(BigDecimal.ZERO.setScale(2));
    }

    @Test
    public void shouldApply3For2Promotion() throws Exception {
        Promotion promotion = buildPromotion(TRIGGER_QUANTITY_3, TARGET_QUANTITY_2, TARGET_PRICE_EMPTY, ItemType.BEANS);
        Item item = new Item(ItemType.BEANS, new PriceDefinition(new BigDecimal("2.00"), Unit.ITEM), new BigDecimal("5.00"));

        BigDecimal totalToPay = promotion.apply(item);

        softly.assertThat(totalToPay).as("Total to Pay").isEqualTo(new BigDecimal("8.00"));
    }

    @Test
    public void shouldApply2ForPricePromotion() throws Exception {
        Promotion promotion = buildPromotion(TRIGGER_QUANTITY_2, TARGET_QUANTITY_EMPTY, TARGET_PRICE_1, ItemType.COKE);
        Item item = new Item(ItemType.COKE, new PriceDefinition(new BigDecimal("1.00"), Unit.ITEM), new BigDecimal("4.0"));

        BigDecimal totalToPay = promotion.apply(item);

        softly.assertThat(totalToPay).as("Total to Pay").isEqualTo(new BigDecimal("2.00"));
    }

    @Test
    public void shouldApply4For3Promotion() throws Exception {
        Promotion promotion = buildPromotion(TRIGGER_QUANTITY_4, TARGET_QUANTITY_3, TARGET_PRICE_EMPTY, ItemType.ORANGES);
        Item item = new Item(ItemType.ORANGES, new PriceDefinition(new BigDecimal("1.00"), Unit.ITEM), new BigDecimal("6.0"));

        BigDecimal totalToPay = promotion.apply(item);

        softly.assertThat(totalToPay).as("Total to Pay").isEqualTo(new BigDecimal("5.00"));
    }

    private Promotion buildPromotion(BigDecimal triggerQuantity, Optional<BigDecimal> targetQuantity, Optional<BigDecimal> targetPrice, ItemType itemType) {
        return new Promotion(triggerQuantity, targetQuantity, targetPrice, itemType) {
            @Override
            public PromotionType getPromotionType() {
                return null;
            }
        };
    }

}