package qmetric.supermarket.domain;

import java.math.BigDecimal;
import java.util.*;
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

    public Receipt(List<ReceiptItem> receiptItems, List<ReceiptItem> savings) {
        this.receiptItems = receiptItems;
        this.savings = savings;
        this.subTotal = receiptItems.stream().map(ReceiptItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalSavings = savings.stream().map(ReceiptItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalToPay = subTotal.add(totalSavings);
    }

    public boolean hasReceiptItems(Predicate<List> predicate) {
        return predicate.test(receiptItems);
    }

    public boolean hasSavingItems(Predicate<List> predicate) {
        return predicate.test(savings);
    }

    public boolean hasSubTotal(Predicate<BigDecimal> predicate) {
        return predicate.test(subTotal);
    }

    public boolean hasTotalSavings(Predicate<BigDecimal> predicate) {
        return predicate.test(totalSavings);
    }

    public boolean hasTotalToPay(Predicate predicate) {
        return predicate.test(totalToPay);
    }

    public List<String> apply(BiFunction<String, String, String> printingFunction) {
        List<String> receiptLines = new ArrayList<>();
        receiptLines.addAll(receiptItems.stream().map(e -> printingFunction.apply(e.getDescription(), e.getPrice().toPlainString())).collect(Collectors.toList()));
        receiptLines.add(printingFunction.apply("", "-----"));
        receiptLines.add(printingFunction.apply("Sub-total", subTotal.toPlainString()));
        receiptLines.add(printingFunction.apply("", ""));
        receiptLines.add(printingFunction.apply("Savings", ""));
        receiptLines.addAll(savings.stream().map(e -> printingFunction.apply(e.getDescription(), e.getPrice().toPlainString())).collect(Collectors.toList()));
        receiptLines.add(printingFunction.apply("", "-----"));
        receiptLines.add(printingFunction.apply("Total savings", totalSavings.toPlainString()));
        receiptLines.add(printingFunction.apply("--------------------", "-----"));
        receiptLines.add(printingFunction.apply("Total to Pay", totalToPay.toPlainString()));
        return receiptLines;
    }


}