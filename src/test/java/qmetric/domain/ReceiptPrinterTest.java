package qmetric.domain;

import org.junit.Assert;
import org.junit.Test;
import qmetric.supermarket.domain.Receipt;
import qmetric.supermarket.domain.ReceiptItem;
import qmetric.supermarket.domain.ReceiptPrinter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSortedMap;
import static java.util.stream.Stream.of;
import static org.hamcrest.CoreMatchers.is;
import static qmetric.supermarket.util.Util.entriesToMap;
import static qmetric.supermarket.util.Util.entry;

/**
 * Created by andrzejfolga on 02/05/2017.
 */
public class ReceiptPrinterTest {

    private ReceiptPrinter receiptPrinter = new ReceiptPrinter();

    private static final String BASIC_RECEIPT =
            "Beans                0.50\n" +
            "Beans                0.50\n" +
            "Beans                0.50\n" +
            "Oranges\n" +
            "0.200 kg @ £1.99/kg  0.40\n" +
            "                    -----\n" +
            "Sub-total            1.90\n" +
            "                         \n" +
            "Savings                  \n" +
            "Beans 3 for 2       -0.50\n" +
            "                    -----\n" +
            "Total savings       -0.50\n" +
            "-------------------------\n" +
            "Total to Pay         1.40";

    @Test
    public void shouldBuildBasicReceipt() throws Exception {
        List<ReceiptItem> itemsPrices = Arrays.asList(
                new ReceiptItem("Beans", new BigDecimal("0.50")),
                new ReceiptItem("Beans", new BigDecimal("0.50")),
                new ReceiptItem("Beans", new BigDecimal("0.50")),
                new ReceiptItem("Oranges\n0.200 kg @ £1.99/kg ", new BigDecimal("0.40")));
        List<ReceiptItem> savings = Arrays.asList(new ReceiptItem("Beans 3 for 2", new BigDecimal("-0.50")));
        Receipt receipt = new Receipt(itemsPrices, savings);

        String receiptPrintout = receiptPrinter.print(receipt);

        Assert.assertThat(receiptPrintout, is(BASIC_RECEIPT));
    }

}