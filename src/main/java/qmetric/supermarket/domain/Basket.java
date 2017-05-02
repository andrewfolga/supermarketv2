package qmetric.supermarket.domain;

import qmetric.supermarket.domain.promotion.Promotion;

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
        items.merge(item.getItemType(), item, (a, b) -> new Item(a.getItemType(), a.getPriceDefinition(), a.getQuantity().add(b.getQuantity())));
    }

    public Receipt calculateReceipt(List<Promotion> promotions) {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        List<ReceiptItem> savings = new ArrayList<>();

        receiptItems.addAll(
                items.values().stream().
                        filter(i -> i.getPriceDefinition().getUnit().equals(Unit.KG)).
                        map(i -> new ReceiptItem(
                                i.getDescription(),
                                i.getTotalPrice())
                        ).collect(Collectors.toList()));

        receiptItems.addAll(
                items.values().stream().
                    filter(i -> i.getPriceDefinition().getUnit().equals(Unit.ITEM)).
                    flatMap(i -> IntStream.range(0, i.getQuantity().intValue()).mapToObj(c ->new ReceiptItem(
                            i.getDescription(),
                            i.getAmountPerUnit()))
                    ).collect(Collectors.toList()));

        savings.addAll(
            promotions.stream().
                filter(p -> items.containsKey(p.getItemType())).
                map(p -> new ReceiptItem(
                        p.getDescription(),
                        p.apply(getItemForType(p.getItemType())).subtract(getItemForType(p.getItemType()).getTotalPrice())
                )).collect(Collectors.toList()));

        return new Receipt(receiptItems, savings);
    }

    private Item getItemForType(ItemType itemType) {
        return items.get(itemType);
    }

}
