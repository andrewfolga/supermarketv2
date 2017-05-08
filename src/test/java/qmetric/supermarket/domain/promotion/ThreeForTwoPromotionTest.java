package qmetric.supermarket.domain.promotion;

import org.junit.Assert;
import org.junit.Test;
import qmetric.supermarket.domain.Item;
import qmetric.supermarket.domain.ItemType;
import qmetric.supermarket.domain.PriceDefinition;
import qmetric.supermarket.domain.Unit;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by andrzejfolga on 08/05/2017.
 */
public class ThreeForTwoPromotionTest {

    private ThreeForTwoPromotion threeForTwoPromotion = new ThreeForTwoPromotion(ItemType.BEANS);

    @Test
    public void shouldTriggerPromotionWithExactQuantity() throws Exception {
        Item item = new Item(ItemType.BEANS, new PriceDefinition(new BigDecimal("0.70"), Unit.ITEM), new BigDecimal("3.00"));

        BigDecimal promotionPrice = threeForTwoPromotion.apply(item);

        assertThat(promotionPrice, is(equalTo(new BigDecimal("1.40"))));
    }

    @Test
    public void shouldTriggerPromotionWithHigherQuantity() throws Exception {
        Item item = new Item(ItemType.BEANS, new PriceDefinition(new BigDecimal("0.70"), Unit.ITEM), new BigDecimal("7.00"));

        BigDecimal promotionPrice = threeForTwoPromotion.apply(item);

        assertThat(promotionPrice, is(equalTo(new BigDecimal("3.50"))));
    }

    @Test
    public void shouldNotTriggerPromotionWithLowerQuantity() throws Exception {
        Item item = new Item(ItemType.BEANS, new PriceDefinition(new BigDecimal("0.70"), Unit.ITEM), new BigDecimal("2.00"));

        BigDecimal promotionPrice = threeForTwoPromotion.apply(item);

        assertThat(promotionPrice, is(equalTo(new BigDecimal("1.40"))));
    }

    @Test
    public void shouldNotTriggerPromotionWithWrongItemType() throws Exception {
        Item item = new Item(ItemType.COKE, new PriceDefinition(new BigDecimal("0.70"), Unit.ITEM), new BigDecimal("2.00"));

        BigDecimal promotionPrice = threeForTwoPromotion.apply(item);

        assertThat(promotionPrice, is(equalTo(new BigDecimal("0.00"))));
    }
}