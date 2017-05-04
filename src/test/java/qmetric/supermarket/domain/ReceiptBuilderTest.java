package qmetric.supermarket.domain;

import org.assertj.core.api.JUnitSoftAssertions;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static qmetric.supermarket.domain.ItemType.BEANS;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReceiptBuilderTest {

    public static final List<Promotion> NO_PROMOTIONS = Lists.newArrayList();
    public static final List<Promotion> PROMOTIONS = Lists.newArrayList(Promotion.quantityForQuantity(new BigDecimal("3.00"), new BigDecimal("2.00"), BEANS, PromotionType.THREE_FOR_TWO));
    @Mock
    private Basket basket;
    @Mock
    private Receipt receipt;

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @InjectMocks
    private ReceiptBuilder receiptBuilder;

    @Before
    public void setUp() throws Exception {
        receiptBuilder = new ReceiptBuilder();
    }

    @Test
    public void shouldBuildBasicReceipt() {
        when(basket.calculateReceipt(NO_PROMOTIONS)).thenReturn(receipt);

        Receipt result = receiptBuilder.build(basket, NO_PROMOTIONS);

        verify(basket).calculateReceipt(NO_PROMOTIONS);
        verifyNoMoreInteractions(basket);
        softly.assertThat(result).as("Receipt").isEqualTo(receipt);
    }

    @Test
    public void shouldBuildReceiptWithPromotion() {
        when(basket.calculateReceipt(PROMOTIONS)).thenReturn(receipt);

        Receipt result = receiptBuilder.build(basket, PROMOTIONS);

        verify(basket).calculateReceipt(PROMOTIONS);
        verifyNoMoreInteractions(basket);
        softly.assertThat(result).as("Receipt").isEqualTo(receipt);
    }

}