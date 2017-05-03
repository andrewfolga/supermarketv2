package qmetric.supermarket.domain.promotion;

import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.PromotionFunction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by andrzejfolga on 03/05/2017.
 */
public class PromotionFunctionBuilder {

    public List<PromotionFunction> build(List<Promotion> promotions) {
        return promotions.stream().map(promo -> new PromotionFunction(promo)).collect(Collectors.toList());
    }
}
