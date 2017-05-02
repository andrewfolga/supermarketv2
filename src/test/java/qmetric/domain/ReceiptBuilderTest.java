package qmetric.domain;

import org.junit.Test;
import qmetric.supermarket.domain.*;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static qmetric.supermarket.domain.ItemType.BEANS;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ReceiptBuilderTest {

    private ReceiptBuilder receiptBuilder = new ReceiptBuilder();

    @Test
    public void shouldBuildBasicReceipt() throws Exception {
        Basket basket = new Basket();
        Item item = new Item(BEANS, new PriceDefinition(new BigDecimal("0.5"), Unit.ITEM), new BigDecimal(3));
        basket.add(item);

        Receipt receipt = receiptBuilder.build(basket, Arrays.asList());

        assertTrue(receipt.hasTotalToPay(totalToPay -> totalToPay.equals(new BigDecimal("1.5"))));
        assertTrue(receipt.hasSubTotal(subTotal -> subTotal.equals(new BigDecimal("1.5"))));
        assertTrue(receipt.hasTotalSavings(totalSaving -> totalSaving.equals(BigDecimal.ZERO)));
        assertTrue(receipt.hasReceiptItems(receiptItems -> {
            ReceiptItem receiptItem = new ReceiptItem(item.getDescription(), new BigDecimal("0.5"));
            return receiptItems.stream().filter(e -> e.equals(receiptItem)).count() == 3;
        }));
        assertTrue(receipt.hasSavingItems(savingItems -> savingItems.isEmpty()));
    }

    @Test
    public void shouldBuildReceiptWithPromotions() throws Exception {

    }

}