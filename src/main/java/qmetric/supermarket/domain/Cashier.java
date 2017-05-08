package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.ports.primary.PromotionRepositoryPort;

import java.util.List;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Cashier {

    private final PromotionRepositoryPort promoRepository;
    private final ReceiptBuilder receiptBuilder;
    private final ReceiptPrinter receiptPrinter;

    public Cashier(final PromotionRepositoryPort promoRepository, final ReceiptBuilder receiptBuilder, final ReceiptPrinter receiptPrinter) {
        this.promoRepository = promoRepository;
        this.receiptBuilder = receiptBuilder;
        this.receiptPrinter = receiptPrinter;
    }

    public String process(final Basket basket) {
        final List<Promotion> promotions = promoRepository.getPromotions();
        final Receipt receipt = receiptBuilder.build(basket, promotions);
        return receiptPrinter.print(receipt);
    }

}
