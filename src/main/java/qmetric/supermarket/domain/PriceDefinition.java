package qmetric.supermarket.domain;

import java.math.BigDecimal;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class PriceDefinition {

    private final BigDecimal amountPerUnit;
    private final Unit unit;

    public PriceDefinition(BigDecimal amountPerUnit, Unit unit) {
        this.amountPerUnit = amountPerUnit;
        this.unit = unit;
    }

    public BigDecimal getAmountPerUnit() {
        return amountPerUnit;
    }

    public Unit getUnit() {
        return unit;
    }
}
