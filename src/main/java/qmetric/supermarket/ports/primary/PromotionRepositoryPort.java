package qmetric.supermarket.ports.primary;

import qmetric.supermarket.domain.promotion.AbstractPromotion;

import java.util.List;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public interface PromotionRepositoryPort {

    List<AbstractPromotion> getPromotions();

}
