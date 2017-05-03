package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;

import java.util.List;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Cashier {

    private PromotionRepository promoRepository;
    private ReceiptBuilder receiptBuilder;
    private ReceiptPrinter receiptPrinter;

    public Cashier(PromotionRepository promoRepository, ReceiptBuilder receiptBuilder, ReceiptPrinter receiptPrinter) {
        this.promoRepository = promoRepository;
        this.receiptBuilder = receiptBuilder;
        this.receiptPrinter = receiptPrinter;
    }

    public String process(Basket basket) {
        List<Promotion> promotions = promoRepository.getPromotions();
        Receipt receipt = receiptBuilder.build(basket, promotions);
        String receiptPrintout = receiptPrinter.print(receipt);
        return receiptPrintout;
    }

}
