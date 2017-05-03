package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;
import qmetric.supermarket.domain.promotion.PromotionFunction;

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

    private final List<Item> items = new ArrayList<>();

    public void add(final Item item) {
        items.add(item);
    }

    public Receipt calculateReceipt(final List<PromotionFunction> promotionFunctions) {
        final List<ReceiptItem> receiptItems = new ArrayList<>();
        final List<ReceiptItem> savings = new ArrayList<>();

        receiptItems.addAll(
                items.stream().
                        map(item -> new ReceiptItem(
                                item.getDescription(),
                                item.getTotalPrice())
                        ).collect(Collectors.toList()));

        savings.addAll(
                promotionFunctions.stream().
                        filter(funtion -> itemsContainType(funtion.getItemType())).
                        map(promotion -> new ReceiptItem(
                                promotion.getDescription(),
                                calculateSavings(promotion)
                        )).collect(Collectors.toList()));

        return new Receipt(receiptItems, savings);
    }

    private boolean itemsContainType(ItemType itemType) {
        return items.stream().map(Item::getItemType).filter(type -> type.equals(itemType)).findFirst().isPresent();
    }

    private BigDecimal calculateSavings(final PromotionFunction function) {

        final Map<ItemType, Item> combinedItems = combineItems();

        final BigDecimal savings = combinedItems.values().stream().
                filter(item -> item.getItemType().equals(function.getItemType())).
                map(item -> function.apply(item).subtract(item.getTotalPrice())).
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
