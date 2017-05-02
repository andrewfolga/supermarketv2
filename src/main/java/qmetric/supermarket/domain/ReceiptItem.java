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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceiptItem that = (ReceiptItem) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(price, that.price);
    }

}