package qmetric.domain;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import qmetric.supermarket.domain.*;
import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.ThreeForTwoPromotion;
import qmetric.supermarket.domain.promotion.TwoForPricePromotion;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static qmetric.supermarket.domain.ItemType.BEANS;
import static qmetric.supermarket.domain.ItemType.COKE;
import static qmetric.supermarket.domain.ItemType.ORANGES;

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
    public void shouldBuildReceiptWithBasicPromotions() throws Exception {
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

    @Test
    public void shouldBuildReceiptWithComplexPromotions() throws Exception {
        ThreeForTwoPromotion threeForTwoPromotion = new ThreeForTwoPromotion(BEANS);
        TwoForPricePromotion twoForPricePromotion = new TwoForPricePromotion(COKE, new BigDecimal("2.00"));
        List<Promotion> availablePromotions = Arrays.asList(threeForTwoPromotion, twoForPricePromotion);
        Basket basket = new Basket();
        Item beans = new Item(BEANS, new PriceDefinition(new BigDecimal("0.50"), Unit.ITEM), new BigDecimal(4));
        Item coke = new Item(COKE, new PriceDefinition(new BigDecimal("1.50"), Unit.ITEM), new BigDecimal(5));
        Item oranges = new Item(ORANGES, new PriceDefinition(new BigDecimal("3.00"), Unit.KG), new BigDecimal(0.5));
        basket.add(beans);
        basket.add(coke);
        basket.add(oranges);

        Receipt receipt = receiptBuilder.build(basket, availablePromotions);

        softly.assertThat(receipt.hasTotalToPay(totalToPay -> totalToPay.equals(new BigDecimal("8.50")))).as("Total to pay").isTrue();
        softly.assertThat(receipt.hasSubTotal(subTotal -> subTotal.equals(new BigDecimal("11.00")))).as("Sub-total").isTrue();
        softly.assertThat(receipt.hasTotalSavings(totalSaving -> totalSaving.equals(new BigDecimal("-2.50")))).as("Total savings").isTrue();
        receipt.hasReceiptItems(receiptItems -> {
            ReceiptItem receiptItemBeans = new ReceiptItem("Beans", new BigDecimal("0.50"));
            softly.assertThat(receiptItems.stream().filter(e -> e.equals(receiptItemBeans)).count()==4).as("Number of beans receipt items").isTrue();
            ReceiptItem receiptItemCoke = new ReceiptItem("Coke", new BigDecimal("1.50"));
            softly.assertThat(receiptItems.stream().filter(e -> e.equals(receiptItemCoke)).count()==5).as("Number of coke receipt items").isTrue();
            ReceiptItem receiptItemOranges = new ReceiptItem("Oranges\n0.500 kg @ £ 3.00/kg", new BigDecimal("1.50"));
            softly.assertThat(receiptItems.stream().filter(e -> e.equals(receiptItemOranges)).count()==1).as("Number of oranges receipt items").isTrue();
            return true;
        });
        receipt.hasSavingItems(savingItems -> {
            ReceiptItem savingBeans = new ReceiptItem("Beans 3 for 2", new BigDecimal("-0.50"));
            softly.assertThat(savingItems.stream().filter(e -> e.equals(savingBeans)).count()==1).as("Number of beans savings items").isTrue();
            ReceiptItem savingCoke = new ReceiptItem("Coke 2 for £2.00", new BigDecimal("-2.00"));
            softly.assertThat(savingItems.stream().filter(e -> e.equals(savingCoke)).count()==1).as("Number of coke savings items").isTrue();
            return true;
        });
    }

}