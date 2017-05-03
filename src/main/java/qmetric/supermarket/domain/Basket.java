package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.AbstractPromotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Basket {

    private static final String KEY = "%s-%s";
    private final List<Item> items = new ArrayList<>();

    public void add(final Item item) {
        items.add(item);
    }

    public Receipt calculateReceipt(final List<AbstractPromotion> promotions) {
        final List<ReceiptItem> receiptItems = new ArrayList<>();
        final List<ReceiptItem> savings = new ArrayList<>();

        receiptItems.addAll(
                items.stream().
                        map(item -> new ReceiptItem(
                                item.getDescription(),
                                item.getTotalPrice())
                        ).collect(Collectors.toList()));

        savings.addAll(
                promotions.stream().
                        filter(promotion -> itemsContainType(promotion.getItemType())).
                        map(promotion -> new ReceiptItem(
                                promotion.getDescription(),
                                calculateSavings(promotion)
                        )).collect(Collectors.toList()));

        return new Receipt(receiptItems, savings);
    }

    private boolean itemsContainType(ItemType itemType) {
        return items.stream().map(Item::getItemType).filter(type -> type.equals(itemType)).findFirst().isPresent();
    }

    private BigDecimal calculateSavings(final AbstractPromotion promotion) {

        final Map<ItemType, Item> combinedItems = combineItems();

        final BigDecimal savings = combinedItems.values().stream().
                filter(item -> item.getItemType().equals(promotion.getItemType())).
                map(item -> promotion.apply(item).subtract(item.getTotalPrice())).
                reduce(BigDecimal.ZERO, BigDecimal::add);

        return savings;
    }

    private Map<ItemType, Item> combineItems() {
        return items.stream().collect(Collectors.toMap(
                    Item::getItemType,
                    Function.identity(),
                    (Item item1, Item item2) ->
                            new Item(
                                    item1.getItemType(),
                                    item1.getPriceDefinition(),
                                    item1.getQuantity().add(item2.getQuantity()))));
    }

}
