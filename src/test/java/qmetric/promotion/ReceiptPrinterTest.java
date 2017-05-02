package qmetric.promotion;

import org.junit.Assert;
import org.junit.Test;
import qmetric.supermarket.domain.Receipt;
import qmetric.supermarket.domain.ReceiptPrinter;

import java.math.BigDecimal;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;
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
            "Beans                 0.50\n" +
            "Beans                 0.50\n" +
            "Beans                 0.50\n" +
            "Oranges\n" +
            "0.200 kg @ £1.99/kg   0.40\n" +
            "Sub-total             1.90\n" +
            "Savings\n" +
            "Beans 3 for 2        -0.50\n" +
            "Total savings        -0.50\n" +
            "--------------------------\n" +
            "Total to Pay          1.40\n";

    @Test
    public void shouldBuildBasicReceipt() throws Exception {
        Map<String, BigDecimal> itemsPrices = unmodifiableMap(of(entry("Beans", new BigDecimal("0.50")),entry("Oranges\n0.200 kg @ £1.99/kg", new BigDecimal("0.40"))).collect(entriesToMap()));
        Map<String, BigDecimal> savings = unmodifiableMap(of(entry("Beans 3 for 2", new BigDecimal("-0.50"))).collect(entriesToMap()));
        Receipt receipt = new Receipt(itemsPrices, savings);

        String receiptPrintout = receiptPrinter.print(receipt);

        Assert.assertThat(receiptPrintout, is(BASIC_RECEIPT));
    }

}