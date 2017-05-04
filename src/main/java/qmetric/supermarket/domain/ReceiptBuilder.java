package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;

import java.util.List;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ReceiptBuilder {

    public Receipt build(final Basket basket, final List<Promotion> promotions) {
        return basket.calculateReceipt(promotions);
    }

}
