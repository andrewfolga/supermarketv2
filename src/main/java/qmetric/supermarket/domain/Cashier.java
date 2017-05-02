package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;

import java.util.List;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Cashier {

    private PromotionRepository promotionRepository;
    private ReceiptBuilder receiptBuilder;
    private ReceiptPrinter receiptPrinter;

    public Cashier(PromotionRepository promotionRepository, ReceiptBuilder receiptBuilder, ReceiptPrinter receiptPrinter) {
        this.promotionRepository = promotionRepository;
        this.receiptBuilder = receiptBuilder;
        this.receiptPrinter = receiptPrinter;
    }

    public String process(Basket basket) {
        List<Promotion> promotions = promotionRepository.getPromotions();
        Receipt receipt = receiptBuilder.build(basket, promotions);
        String receiptPrintout = receiptPrinter.print(receipt);
        return receiptPrintout;
    }

}
