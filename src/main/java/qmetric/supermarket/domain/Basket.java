package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by andrzejfolga on 01/05/2017.
 */
public class Basket {

    private final Map<ItemType, Item> items = new HashMap<ItemType, Item>();

    public void add(Item item) {
        items.merge(item.getItemType(), item, (firstItem, secondItem) ->
                new Item(
                        firstItem.getItemType(),
                        firstItem.getPriceDefinition(),
                        firstItem.getQuantity().add(secondItem.getQuantity())));
    }

    public Receipt calculateReceipt(List<Promotion> promotions) {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        List<ReceiptItem> savings = new ArrayList<>();

        receiptItems.addAll(
                items.values().stream().
                        filter(item -> item.getPriceDefinition().getUnit().equals(Unit.KG)).
                        map(item -> new ReceiptItem(
                                item.getDescription(),
                                item.getTotalPrice())
                        ).collect(Collectors.toList()));

        receiptItems.addAll(
                items.values().stream().
                        filter(item -> item.getPriceDefinition().getUnit().equals(Unit.ITEM)).
                        flatMap(item -> IntStream.rangeClosed(1, item.getQuantity().intValue()).mapToObj(counter -> new ReceiptItem(
                                item.getDescription(),
                                item.getAmountPerUnit()))
                        ).collect(Collectors.toList()));

        savings.addAll(
                promotions.stream().
                        filter(promotion -> items.containsKey(promotion.getItemType())).
                        map(promotion -> new ReceiptItem(
                                promotion.getDescription(),
                                getSavings(promotion)
                        )).collect(Collectors.toList()));

        return new Receipt(receiptItems, savings);
    }

    private BigDecimal getSavings(Promotion promotion) {
        return promotion.apply(getItemForType(promotion.getItemType())).
                subtract(getItemForType(promotion.getItemType()).getTotalPrice());
    }

    private Item getItemForType(ItemType itemType) {
        return items.get(itemType);
    }

}
