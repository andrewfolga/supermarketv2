package qmetric.supermarket.domain;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import qmetric.supermarket.ports.primary.PromotionRepositoryPort;

import java.util.List;

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
    private Basket basket;
    @Mock
    private Receipt receipt;
    @Mock
    private List<Promotion> promotions;
    @Mock
    private ReceiptPrinter receiptPrinter;
    @Mock
    private ReceiptBuilder receiptBuilder;
    @Mock
    private PromotionRepositoryPort promotionRepositoryPort;
    @InjectMocks
    private Cashier cashier;

    @Test
    public void shouldProduceReceipt() {
        String receiptPrintout = "receipt";
        when(promotionRepositoryPort.getPromotions()).thenReturn(promotions);
        when(receiptBuilder.build(basket, promotions)).thenReturn(receipt);
        when(receiptPrinter.print(receipt)).thenReturn(receiptPrintout);

        String result = cashier.process(basket);

        verify(promotionRepositoryPort).getPromotions();
        verify(receiptBuilder).build(basket, promotions);
        verify(receiptPrinter).print(receipt);
        softly.assertThat(result).as("Result printout").isEqualTo(receiptPrintout);
    }
}
