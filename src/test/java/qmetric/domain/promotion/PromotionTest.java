package qmetric.domain.promotion;

import org.junit.Test;
import qmetric.supermarket.domain.Item;
import qmetric.supermarket.domain.ItemType;
import qmetric.supermarket.domain.PriceDefinition;
import qmetric.supermarket.domain.Unit;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by andrzejfolga on 02/05/2017.
 */
public class PromotionTest {

    @Test(expected = NullPointerException.class)
    public void shouldNotApplyPromotionIfTriggerQuantityNotProvided() throws Exception {
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotApplyPromotionIfTargetQuantityLowerThanTriggerQuantity() throws Exception {
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotApplyPromotionIfTriggerQuantityProvidedAndNeitherTargetQuantityNorTargetPrice() throws Exception {
    }

    @Test
    public void shouldNotApply3For2PromotionForDifferentItemType() throws Exception {
    }

    @Test
    public void shouldApply3For2Promotion() throws Exception {
    }

    @Test
    public void shouldApply2ForPricePromotion() throws Exception {
    }

    @Test
    public void shouldApply4For3Promotion() throws Exception {
    }

}