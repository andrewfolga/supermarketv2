package qmetric.supermarket.domain;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by andrzejfolga on 02/05/2017.
 */
public class Receipt {

    private final Map<String, BigDecimal> itemsPrices;
    private final Map<String, BigDecimal> savings;
    private final BigDecimal subTotal;
    private final BigDecimal totalSavings;
    private final BigDecimal totalToPay;

    public Receipt(Map<String, BigDecimal> itemsPrices, Map<String, BigDecimal> savings) {
        this.itemsPrices = itemsPrices;
        this.savings = savings;
        this.subTotal = itemsPrices.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalSavings = savings.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalToPay = subTotal.add(totalSavings);
    }

}