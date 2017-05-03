package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.PromotionFunction;
import qmetric.supermarket.domain.promotion.PromotionFunctionBuilder;

import java.util.List;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class ReceiptBuilder {

    private PromotionFunctionBuilder promotionFunctionBuilder;

    public ReceiptBuilder(PromotionFunctionBuilder promotionFunctionBuilder) {
        this.promotionFunctionBuilder = promotionFunctionBuilder;
    }

    public Receipt build(final Basket basket, final List<Promotion> promotions) {
        List<PromotionFunction> promotionFunctions = promotionFunctionBuilder.build(promotions);
        return basket.calculateReceipt(promotionFunctions);
    }

}
