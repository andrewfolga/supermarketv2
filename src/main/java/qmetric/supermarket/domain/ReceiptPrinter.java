package qmetric.supermarket.domain;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by andrzejfolga on 02/05/2017.
 */
public class ReceiptPrinter {

    public static final String TEMPLATE = "%1$-20s%2$5s";

    public String print(Receipt receipt) {
        List<String> printableReceiptLines = receipt.apply((String desc, String price) -> String.format(TEMPLATE, desc, price));
        String printableReceipt = printableReceiptLines.stream().collect(Collectors.joining("\n"));
        return printableReceipt;
    }

}
