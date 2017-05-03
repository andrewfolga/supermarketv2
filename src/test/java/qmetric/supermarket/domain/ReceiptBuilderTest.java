package qmetric.supermarket.domain;

import org.assertj.core.api.JUnitSoftAssertions;
import org.assertj.core.util.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.ThreeForTwoPromotion;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static qmetric.supermarket.domain.ItemType.BEANS;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReceiptBuilderTest {

    public static final ArrayList<Promotion> NO_PROMOTIONS = Lists.newArrayList();
    public static final ArrayList<Promotion> PROMOTIONS = Lists.newArrayList(new ThreeForTwoPromotion(BEANS));
    @Mock
    private Basket basket;
    @Mock
    private Receipt receipt;

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    private ReceiptBuilder receiptBuilder = new ReceiptBuilder();

    @Test
    public void shouldBuildBasicReceipt() throws Exception {
        when(basket.calculateReceipt(Lists.newArrayList())).thenReturn(receipt);

        Receipt result = receiptBuilder.build(basket, NO_PROMOTIONS);

        verify(basket).calculateReceipt(NO_PROMOTIONS);
        verifyNoMoreInteractions(basket);
        softly.assertThat(result).as("Receipt").isEqualTo(receipt);
    }

    @Test
    public void shouldBuildReceiptWithPromotion() throws Exception {
        when(basket.calculateReceipt(PROMOTIONS)).thenReturn(receipt);

        Receipt result = receiptBuilder.build(basket, PROMOTIONS);

        verify(basket).calculateReceipt(PROMOTIONS);
        verifyNoMoreInteractions(basket);
        softly.assertThat(result).as("Receipt").isEqualTo(receipt);
    }

}