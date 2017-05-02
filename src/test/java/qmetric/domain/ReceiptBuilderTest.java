package qmetric.domain;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
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

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    private ReceiptBuilder receiptBuilder = new ReceiptBuilder();

    @Test
    public void shouldBuildBasicReceipt() throws Exception {
        Basket basket = new Basket();
        Item item = new Item(BEANS, new PriceDefinition(new BigDecimal("0.50"), Unit.ITEM), new BigDecimal(3));
        basket.add(item);

        Receipt receipt = receiptBuilder.build(basket, Arrays.asList());

        softly.assertThat(receipt.hasTotalToPay(totalToPay -> totalToPay.equals(new BigDecimal("1.50")))).as("Total to pay").isTrue();
        softly.assertThat(receipt.hasSubTotal(subTotal -> subTotal.equals(new BigDecimal("1.50")))).as("Sub-total").isTrue();
        softly.assertThat(receipt.hasTotalSavings(totalSaving -> totalSaving.equals(new BigDecimal("0.00")))).as("Total savings").isTrue();
        softly.assertThat(receipt.hasReceiptItems(receiptItems -> {
            ReceiptItem receiptItem = new ReceiptItem("Beans", new BigDecimal("0.50"));
            return receiptItems.stream().filter(e -> e.equals(receiptItem)).count() == 3;
        })).as("Receipt items").isTrue();
        softly.assertThat(receipt.hasSavingItems(savingItems -> savingItems.isEmpty())).as("Savings items").isTrue();
    }

    @Test
    public void shouldBuildReceiptWithPromotions() throws Exception {
        ThreeForTwoPromotion threeForTwoPromotion = new ThreeForTwoPromotion(BEANS);
        List<Promotion> availablePromotions = Arrays.asList(threeForTwoPromotion);
        Basket basket = new Basket();
        Item item = new Item(BEANS, new PriceDefinition(new BigDecimal("0.50"), Unit.ITEM), new BigDecimal(3));
        basket.add(item);

        Receipt receipt = receiptBuilder.build(basket, availablePromotions);

        softly.assertThat(receipt.hasTotalToPay(totalToPay -> totalToPay.equals(new BigDecimal("1.00")))).as("Total to pay").isTrue();
        softly.assertThat(receipt.hasSubTotal(subTotal -> subTotal.equals(new BigDecimal("1.50")))).as("Sub-total").isTrue();
        softly.assertThat(receipt.hasTotalSavings(totalSaving -> totalSaving.equals(new BigDecimal("-0.50")))).as("Total savings").isTrue();
        softly.assertThat(receipt.hasReceiptItems(receiptItems -> {
            ReceiptItem receiptItem = new ReceiptItem("Beans", new BigDecimal("0.50"));
            return receiptItems.stream().filter(e -> e.equals(receiptItem)).count() == 3;
        })).as("Receipt items").isTrue();
        softly.assertThat(receipt.hasSavingItems(savingItems -> {
            ReceiptItem receiptItem = new ReceiptItem("Beans 3 for 2", new BigDecimal("-0.50"));
            return savingItems.stream().filter(e -> e.equals(receiptItem)).count() == 1;
        })).as("Savings items").isTrue();
    }

}