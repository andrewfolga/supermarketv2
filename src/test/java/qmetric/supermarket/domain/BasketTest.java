package qmetric.supermarket.domain;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.PromotionType;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static qmetric.supermarket.domain.ItemType.*;

/**
 * Created by andrzejfolga on 03/05/2017.
 */
public class BasketTest {

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    private Basket basket = new Basket();
    private List<Promotion> availablePromotions;

    @Before
    public void setUp() {
        availablePromotions = Arrays.asList(
                Promotion.quantityForQuantity(new BigDecimal("3.00"), new BigDecimal("2.00"), BEANS, PromotionType.THREE_FOR_TWO),
                Promotion.quantityForPrice(new BigDecimal("2.00"), new BigDecimal("2.00"), COKE, PromotionType.TWO_FOR_PRICE));
        basket.addAll(Arrays.asList(
                new Item(BEANS, new PriceDefinition(new BigDecimal("0.50"), Unit.ITEM)),
                new Item(BEANS, new PriceDefinition(new BigDecimal("0.50"), Unit.ITEM)),
                new Item(BEANS, new PriceDefinition(new BigDecimal("0.50"), Unit.ITEM)),
                new Item(BEANS, new PriceDefinition(new BigDecimal("0.50"), Unit.ITEM)),
                new Item(COKE, new PriceDefinition(new BigDecimal("1.50"), Unit.ITEM)),
                new Item(COKE, new PriceDefinition(new BigDecimal("1.50"), Unit.ITEM)),
                new Item(COKE, new PriceDefinition(new BigDecimal("1.50"), Unit.ITEM)),
                new Item(COKE, new PriceDefinition(new BigDecimal("1.50"), Unit.ITEM)),
                new Item(COKE, new PriceDefinition(new BigDecimal("1.50"), Unit.ITEM)),
                new Item(ORANGES, new PriceDefinition(new BigDecimal("3.00"), Unit.KG), new BigDecimal("0.50")),
                new Item(JUICE, new PriceDefinition(new BigDecimal("2.00"), Unit.L), new BigDecimal("0.50"))));
    }

    @Test
    public void shouldHaveCorrectTotalToPay() {

        Receipt receipt = basket.calculateReceipt(availablePromotions);

        softly.assertThat(receipt.hasTotalToPay(totalToPay -> totalToPay.equals(new BigDecimal("9.50")))).as("Total to pay").isTrue();
    }

    @Test
    public void shouldHaveCorrectSubTotal() {

        Receipt receipt = basket.calculateReceipt(availablePromotions);

        softly.assertThat(receipt.hasSubTotal(subTotal -> subTotal.equals(new BigDecimal("12.00")))).as("Sub-total").isTrue();
    }

    @Test
    public void shouldHaveCorrectTotalSavings() {

        Receipt receipt = basket.calculateReceipt(availablePromotions);

        softly.assertThat(receipt.hasTotalSavings(totalSaving -> totalSaving.equals(new BigDecimal("-2.50")))).as("Total savings").isTrue();
    }

    @Test
    public void shouldHaveCorrectReceiptItems() {

        Receipt receipt = basket.calculateReceipt(availablePromotions);

        receipt.hasReceiptItems(receiptItems -> {
            ReceiptItem receiptItemBeans = new ReceiptItem("Beans", new BigDecimal("0.50"));
            softly.assertThat(receiptItems.stream().filter(receiptItem -> receiptItem.equals(receiptItemBeans)).count() == 4).as("Number of beans receipt items").isTrue();
            ReceiptItem receiptItemCoke = new ReceiptItem("Coke", new BigDecimal("1.50"));
            softly.assertThat(receiptItems.stream().filter(receiptItem -> receiptItem.equals(receiptItemCoke)).count() == 5).as("Number of coke receipt items").isTrue();
            ReceiptItem receiptItemOranges = new ReceiptItem("Oranges\n0.500 kg @ £ 3.00/kg", new BigDecimal("1.50"));
            softly.assertThat(receiptItems.stream().filter(receiptItem -> receiptItem.equals(receiptItemOranges)).count() == 1).as("Number of oranges receipt items").isTrue();
            return true;
        });
    }

    @Test
    public void shouldHaveCorrectSavingsItems() {

        Receipt receipt = basket.calculateReceipt(availablePromotions);

        receipt.hasSavingItems(savingItems -> {
            ReceiptItem savingBeans = new ReceiptItem("Beans 3 for 2", new BigDecimal("-0.50"));
            softly.assertThat(savingItems.stream().filter(receiptItem -> receiptItem.equals(savingBeans)).count() == 1).as("Number of beans savings items").isTrue();
            ReceiptItem savingCoke = new ReceiptItem("Coke 2 for £2.00", new BigDecimal("-2.00"));
            softly.assertThat(savingItems.stream().filter(receiptItem -> receiptItem.equals(savingCoke)).count() == 1).as("Number of coke savings items").isTrue();
            return true;
        });
    }

}