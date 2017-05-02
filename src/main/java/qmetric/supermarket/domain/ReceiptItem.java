package qmetric.supermarket.domain;

import java.math.BigDecimal;

public class ReceiptItem {
    private final String description;
    private final BigDecimal price;

    public ReceiptItem(String description, BigDecimal price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }
}