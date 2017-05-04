package qmetric.supermarket.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by andrzejfolga on 02/05/2017.
 */
public class Receipt {

    private final List<ReceiptItem> receiptItems;
    private final List<ReceiptItem> savings;
    private final BigDecimal subTotal;
    private final BigDecimal totalSavings;
    private final BigDecimal totalToPay;

    public Receipt(final List<ReceiptItem> receiptItems, final List<ReceiptItem> savings) {
        this.receiptItems = receiptItems;
        this.savings = savings;
        this.subTotal = receiptItems.stream().map(ReceiptItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        this.totalSavings = savings.stream().map(ReceiptItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        this.totalToPay = subTotal.add(totalSavings).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public boolean hasReceiptItems(final Predicate<List> predicate) {
        return predicate.test(receiptItems);
    }

    public boolean hasSavingItems(final Predicate<List> predicate) {
        return predicate.test(savings);
    }

    public boolean hasSubTotal(final Predicate<BigDecimal> predicate) {
        return predicate.test(subTotal);
    }

    public boolean hasTotalSavings(final Predicate<BigDecimal> predicate) {
        return predicate.test(totalSavings);
    }

    public boolean hasTotalToPay(final Predicate predicate) {
        return predicate.test(totalToPay);
    }

    public List<String> apply(final BiFunction<String, String, String> printingFunction) {
        final List<String> receiptLines = new ArrayList<>();
        receiptLines.addAll(receiptItems.stream().
                map(receiptItem ->
                        printingFunction.apply(
                                receiptItem.getDescription(),
                                receiptItem.getPrice().toPlainString())).
                collect(Collectors.toList()));
        receiptLines.add(printingFunction.apply("", "-----"));
        receiptLines.add(printingFunction.apply("Sub-total", subTotal.toPlainString()));
        receiptLines.add(printingFunction.apply("", ""));
        receiptLines.add(printingFunction.apply("Savings", ""));
        receiptLines.addAll(savings.stream().
                map(receiptItem ->
                        printingFunction.apply(
                                receiptItem.getDescription(),
                                receiptItem.getPrice().toPlainString())).
                collect(Collectors.toList()));
        receiptLines.add(printingFunction.apply("", "-----"));
        receiptLines.add(printingFunction.apply("Total savings", totalSavings.toPlainString()));
        receiptLines.add(printingFunction.apply("--------------------", "-----"));
        receiptLines.add(printingFunction.apply("Total to Pay", totalToPay.toPlainString()));
        return receiptLines;
    }


}