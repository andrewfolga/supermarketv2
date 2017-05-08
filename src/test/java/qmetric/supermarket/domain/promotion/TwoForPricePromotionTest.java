package qmetric.supermarket.domain.promotion;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import qmetric.supermarket.domain.Item;
import qmetric.supermarket.domain.ItemType;
import qmetric.supermarket.domain.PriceDefinition;
import qmetric.supermarket.domain.Unit;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by andrzejfolga on 08/05/2017.
 */
public class TwoForPricePromotionTest {

    private TwoForPricePromotion twoForPricePromotion = new TwoForPricePromotion(new BigDecimal("1.00"), ItemType.COKE);

    @Test
    public void shouldTriggerPromotionWithExactQuantity() throws Exception {
        Item item = new Item(ItemType.COKE, new PriceDefinition(new BigDecimal("0.70"), Unit.ITEM), new BigDecimal("2.00"));

        BigDecimal promotionPrice = twoForPricePromotion.apply(item);

        assertThat(promotionPrice, is(equalTo(new BigDecimal("1.00"))));
    }

    @Test
    public void shouldTriggerPromotionWithHigherQuantity() throws Exception {
        Item item = new Item(ItemType.COKE, new PriceDefinition(new BigDecimal("0.70"), Unit.ITEM), new BigDecimal("5.00"));

        BigDecimal promotionPrice = twoForPricePromotion.apply(item);

        assertThat(promotionPrice, is(equalTo(new BigDecimal("2.70"))));
    }

    @Test
    public void shouldNotTriggerPromotionWithLowerQuantity() throws Exception {
        Item item = new Item(ItemType.COKE, new PriceDefinition(new BigDecimal("0.70"), Unit.ITEM), new BigDecimal("1.00"));

        BigDecimal promotionPrice = twoForPricePromotion.apply(item);

        assertThat(promotionPrice, is(equalTo(new BigDecimal("0.70"))));
    }

    @Test
    public void shouldNotTriggerPromotionWithWrongItemType() throws Exception {
        Item item = new Item(ItemType.BEANS, new PriceDefinition(new BigDecimal("0.70"), Unit.ITEM), new BigDecimal("1.00"));

        BigDecimal promotionPrice = twoForPricePromotion.apply(item);

        assertThat(promotionPrice, is(equalTo(new BigDecimal("0.00"))));
    }
}