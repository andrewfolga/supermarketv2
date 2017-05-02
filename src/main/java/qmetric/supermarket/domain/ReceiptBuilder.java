package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;

import java.util.List;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ReceiptBuilder {

    public Receipt build(Basket basket, List<Promotion> promotions) {
        Receipt receipt = basket.calculateReceipt(promotions);
        return receipt;
    }

}
