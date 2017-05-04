package qmetric.supermarket.ports.primary;

import qmetric.supermarket.domain.Promotion;

import java.util.List;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public interface PromotionRepositoryPort {

    List<Promotion> getPromotions();

}
