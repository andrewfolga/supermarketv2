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
import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.PromotionFunction;
import qmetric.supermarket.domain.promotion.PromotionFunctionBuilder;
import qmetric.supermarket.domain.promotion.PromotionType;

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
    public static final List<PromotionFunction> NO_PROMOTION_FUNCTIONS = Lists.newArrayList();
    public static final List<Promotion> PROMOTIONS = Lists.newArrayList(Promotion.quantityForQuantity(new BigDecimal("3.00"), new BigDecimal("2.00"), BEANS, PromotionType.THREE_FOR_TWO));
    public static final List<PromotionFunction> PROMOTION_FUNCTIONS = Lists.newArrayList(new PromotionFunction(Promotion.quantityForQuantity(new BigDecimal("3.00"), new BigDecimal("2.00"), BEANS, PromotionType.THREE_FOR_TWO)));
    @Mock
    private Basket basket;
    @Mock
    private PromotionFunctionBuilder promotionFunctionBuilder;
    @Mock
    private Receipt receipt;

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @InjectMocks
    private ReceiptBuilder receiptBuilder;

    @Before
    public void setUp() throws Exception {
        receiptBuilder = new ReceiptBuilder(promotionFunctionBuilder);
    }

    @Test
    public void shouldBuildBasicReceipt() {
        when(promotionFunctionBuilder.build(NO_PROMOTIONS)).thenReturn(NO_PROMOTION_FUNCTIONS);
        when(basket.calculateReceipt(NO_PROMOTION_FUNCTIONS)).thenReturn(receipt);

        Receipt result = receiptBuilder.build(basket, NO_PROMOTIONS);

        verify(promotionFunctionBuilder).build(NO_PROMOTIONS);
        verify(basket).calculateReceipt(NO_PROMOTION_FUNCTIONS);
        verifyNoMoreInteractions(basket, promotionFunctionBuilder);
        softly.assertThat(result).as("Receipt").isEqualTo(receipt);
    }

    @Test
    public void shouldBuildReceiptWithPromotion() {
        when(promotionFunctionBuilder.build(PROMOTIONS)).thenReturn(PROMOTION_FUNCTIONS);
        when(basket.calculateReceipt(PROMOTION_FUNCTIONS)).thenReturn(receipt);

        Receipt result = receiptBuilder.build(basket, PROMOTIONS);

        verify(promotionFunctionBuilder).build(PROMOTIONS);
        verify(basket).calculateReceipt(PROMOTION_FUNCTIONS);
        verifyNoMoreInteractions(basket);
        softly.assertThat(result).as("Receipt").isEqualTo(receipt);
    }

}