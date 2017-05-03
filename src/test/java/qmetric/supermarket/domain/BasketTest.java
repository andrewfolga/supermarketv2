package qmetric.supermarket.domain;

import org.assertj.core.api.JUnitSoftAssertions;
import org.assertj.core.util.Lists;
import org.junit.Rule;
import org.junit.Test;
import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.ThreeForTwoPromotion;
import qmetric.supermarket.domain.promotion.TwoForPricePromotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static qmetric.supermarket.domain.ItemType.*;

/**
 * Created by andrzejfolga on 03/05/2017.
 */
public class BasketTest {

    public static final ArrayList<Promotion> NO_PROMOTIONS = Lists.newArrayList();

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    private Basket basket = new Basket();

    @Test
    public void shouldBuildBasicReceipt() throws Exception {
        Basket basket = buildBasket(
                new Item(BEANS, new PriceDefinition(new BigDecimal("0.50"), Unit.ITEM), new BigDecimal(3)));

        Receipt receipt = basket.calculateReceipt(NO_PROMOTIONS);

        softly.assertThat(receipt.hasTotalToPay(totalToPay -> totalToPay.equals(new BigDecimal("1.50")))).as("Total to pay").isTrue();
        softly.assertThat(receipt.hasSubTotal(subTotal -> subTotal.equals(new BigDecimal("1.50")))).as("Sub-total").isTrue();
        softly.assertThat(receipt.hasTotalSavings(totalSaving -> totalSaving.equals(new BigDecimal("0.00")))).as("Total savings").isTrue();
        softly.assertThat(receipt.hasReceiptItems(receiptItems -> {
            ReceiptItem receiptItemBeans = new ReceiptItem("Beans", new BigDecimal("0.50"));
            return receiptItems.stream().filter(receiptItem -> receiptItem.equals(receiptItemBeans)).count() == 3;
        })).as("Receipt items").isTrue();
        softly.assertThat(receipt.hasSavingItems(savingItems -> savingItems.isEmpty())).as("Savings items").isTrue();
    }

    @Test
    public void shouldBuildReceiptWithSimplePromotion() throws Exception {
        List<Promotion> availablePromotions = buildPromotions(new ThreeForTwoPromotion(BEANS));
        Basket basket = buildBasket(
                new Item(BEANS, new PriceDefinition(new BigDecimal("0.50"), Unit.ITEM), new BigDecimal(3)));

        Receipt receipt = basket.calculateReceipt(availablePromotions);

        softly.assertThat(receipt.hasTotalToPay(totalToPay -> totalToPay.equals(new BigDecimal("1.00")))).as("Total to pay").isTrue();
        softly.assertThat(receipt.hasSubTotal(subTotal -> subTotal.equals(new BigDecimal("1.50")))).as("Sub-total").isTrue();
        softly.assertThat(receipt.hasTotalSavings(totalSaving -> totalSaving.equals(new BigDecimal("-0.50")))).as("Total savings").isTrue();
        softly.assertThat(receipt.hasReceiptItems(receiptItems -> {
            ReceiptItem receiptItemBeans = new ReceiptItem("Beans", new BigDecimal("0.50"));
            return receiptItems.stream().filter(receiptItem -> receiptItem.equals(receiptItemBeans)).count() == 3;
        })).as("Receipt items").isTrue();
        softly.assertThat(receipt.hasSavingItems(savingItems -> {
            ReceiptItem receiptItemBeans = new ReceiptItem("Beans 3 for 2", new BigDecimal("-0.50"));
            return savingItems.stream().filter(receiptItem -> receiptItem.equals(receiptItemBeans)).count() == 1;
        })).as("Savings items").isTrue();
    }

    @Test
    public void shouldBuildReceiptWithComplexPromotions() throws Exception {
        List<Promotion> availablePromotions = buildPromotions(new ThreeForTwoPromotion(BEANS), new TwoForPricePromotion(COKE, new BigDecimal("2.00")));
        Basket basket = buildBasket(
                new Item(BEANS, new PriceDefinition(new BigDecimal("0.50"), Unit.ITEM), new BigDecimal(4)),
                new Item(COKE, new PriceDefinition(new BigDecimal("1.50"), Unit.ITEM), new BigDecimal(5)),
                new Item(ORANGES, new PriceDefinition(new BigDecimal("3.00"), Unit.KG), new BigDecimal(0.5)));

        Receipt receipt = basket.calculateReceipt(availablePromotions);

        softly.assertThat(receipt.hasTotalToPay(totalToPay -> totalToPay.equals(new BigDecimal("8.50")))).as("Total to pay").isTrue();
        softly.assertThat(receipt.hasSubTotal(subTotal -> subTotal.equals(new BigDecimal("11.00")))).as("Sub-total").isTrue();
        softly.assertThat(receipt.hasTotalSavings(totalSaving -> totalSaving.equals(new BigDecimal("-2.50")))).as("Total savings").isTrue();
        receipt.hasReceiptItems(receiptItems -> {
            ReceiptItem receiptItemBeans = new ReceiptItem("Beans", new BigDecimal("0.50"));
            softly.assertThat(receiptItems.stream().filter(receiptItem -> receiptItem.equals(receiptItemBeans)).count() == 4).as("Number of beans receipt items").isTrue();
            ReceiptItem receiptItemCoke = new ReceiptItem("Coke", new BigDecimal("1.50"));
            softly.assertThat(receiptItems.stream().filter(receiptItem -> receiptItem.equals(receiptItemCoke)).count() == 5).as("Number of coke receipt items").isTrue();
            ReceiptItem receiptItemOranges = new ReceiptItem("Oranges\n0.500 kg @ £ 3.00/kg", new BigDecimal("1.50"));
            softly.assertThat(receiptItems.stream().filter(receiptItem -> receiptItem.equals(receiptItemOranges)).count() == 1).as("Number of oranges receipt items").isTrue();
            return true;
        });
        receipt.hasSavingItems(savingItems -> {
            ReceiptItem savingBeans = new ReceiptItem("Beans 3 for 2", new BigDecimal("-0.50"));
            softly.assertThat(savingItems.stream().filter(receiptItem -> receiptItem.equals(savingBeans)).count() == 1).as("Number of beans savings items").isTrue();
            ReceiptItem savingCoke = new ReceiptItem("Coke 2 for £2.00", new BigDecimal("-2.00"));
            softly.assertThat(savingItems.stream().filter(receiptItem -> receiptItem.equals(savingCoke)).count() == 1).as("Number of coke savings items").isTrue();
            return true;
        });
    }

    private List<Promotion> buildPromotions(Promotion... promotions) {
        List<Promotion> promotionList = new ArrayList<>();
        for (Promotion promotion : promotions) {
            promotionList.add(promotion);
        }
        return promotionList;
    }

    private Basket buildBasket(Item... items) {
        Basket basket = new Basket();
        for (Item item : items) {
            basket.add(item);
        }
        return basket;
    }
}