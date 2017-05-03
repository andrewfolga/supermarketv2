package qmetric.supermarket.domain;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object receiptItem) {
        if (this == receiptItem) return true;
        if (receiptItem == null || getClass() != receiptItem.getClass()) return false;
        ReceiptItem that = (ReceiptItem) receiptItem;
        return Objects.equals(description, that.description) &&
                Objects.equals(price, that.price);
    }

}