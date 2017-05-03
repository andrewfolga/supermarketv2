package qmetric.domain;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import qmetric.supermarket.domain.*;
import qmetric.supermarket.domain.promotion.Promotion;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CashierTest {

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Mock
    Basket basket;
    @Mock
    Receipt receipt;
    @Mock
    List<Promotion> promotions;
    @Mock
    private ReceiptPrinter receiptPrinter;
    @Mock
    private ReceiptBuilder receiptBuilder;
    @Mock
    private PromotionRepository promotionRepository;
    @InjectMocks
    private Cashier cashier;

    @Test
    public void shouldProduceReceipt() throws Exception {
        String receiptPrintout = "receipt";
        when(promotionRepository.getPromotions()).thenReturn(promotions);
        when(receiptBuilder.build(basket, promotions)).thenReturn(receipt);
        when(receiptPrinter.print(receipt)).thenReturn(receiptPrintout);

        String result = cashier.process(basket);

        verify(promotionRepository).getPromotions();
        verify(receiptBuilder).build(basket, promotions);
        verify(receiptPrinter).print(receipt);
        softly.assertThat(result).as("Result printout").isEqualTo(receiptPrintout);
    }
}
