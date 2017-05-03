package qmetric.supermarket.domain;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class PriceDefinition {

    private final BigDecimal amountPerUnit;
    private final Unit unit;

    public PriceDefinition(BigDecimal amountPerUnit, Unit unit) {
        Validate.validState(amountPerUnit.compareTo(BigDecimal.ZERO)>=0, "Amount per unit has to be positive");
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
