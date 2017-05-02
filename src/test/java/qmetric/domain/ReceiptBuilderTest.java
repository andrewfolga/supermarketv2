package qmetric.domain;

import org.junit.Test;
import qmetric.supermarket.domain.*;
import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.ThreeForTwoPromotion;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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
        Item item = new Item(BEANS, new PriceDefinition(new BigDecimal("0.50"), Unit.ITEM), new BigDecimal(3));
        basket.add(item);

        Receipt receipt = receiptBuilder.build(basket, Arrays.asList());

        assertTrue(receipt.hasTotalToPay(totalToPay -> totalToPay.equals(new BigDecimal("1.50"))));
        assertTrue(receipt.hasSubTotal(subTotal -> subTotal.equals(new BigDecimal("1.50"))));
        assertTrue(receipt.hasTotalSavings(totalSaving -> totalSaving.equals(new BigDecimal("0.00"))));
        assertTrue(receipt.hasReceiptItems(receiptItems -> {
            ReceiptItem receiptItem = new ReceiptItem("Beans", new BigDecimal("0.50"));
            return receiptItems.stream().filter(e -> e.equals(receiptItem)).count() == 3;
        }));
        assertTrue(receipt.hasSavingItems(savingItems -> savingItems.isEmpty()));
    }

    @Test
    public void shouldBuildReceiptWithPromotions() throws Exception {
        ThreeForTwoPromotion threeForTwoPromotion = new ThreeForTwoPromotion(BEANS);
        List<Promotion> availablePromotions = Arrays.asList(threeForTwoPromotion);
        Basket basket = new Basket();
        Item item = new Item(BEANS, new PriceDefinition(new BigDecimal("0.50"), Unit.ITEM), new BigDecimal(3));
        basket.add(item);

        Receipt receipt = receiptBuilder.build(basket, availablePromotions);

        assertTrue(receipt.hasTotalToPay(totalToPay -> totalToPay.equals(new BigDecimal("1.00"))));
        assertTrue(receipt.hasSubTotal(subTotal -> subTotal.equals(new BigDecimal("1.50"))));
        assertTrue(receipt.hasTotalSavings(totalSaving -> totalSaving.equals(new BigDecimal("-0.50"))));
        assertTrue(receipt.hasReceiptItems(receiptItems -> {
            ReceiptItem receiptItem = new ReceiptItem("Beans", new BigDecimal("0.50"));
            return receiptItems.stream().filter(e -> e.equals(receiptItem)).count() == 3;
        }));
        assertTrue(receipt.hasSavingItems(savingItems -> {
            ReceiptItem receiptItem = new ReceiptItem("Beans 3 for 2", new BigDecimal("-0.50"));
            return savingItems.stream().filter(e -> e.equals(receiptItem)).count() == 1;
        }));
    }

}