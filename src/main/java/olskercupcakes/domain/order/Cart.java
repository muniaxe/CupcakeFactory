package olskercupcakes.domain.order;

import olskercupcakes.domain.cupcake.Cupcake;

import java.util.*;

public class Cart {
    private final List<Item> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        //If same item already exists, just update quantity.
        Item foundItem;
        if((foundItem = findItem(item)) != null) {
            foundItem.setQuantity(item.getQuantity() + foundItem.getQuantity());
        }
        else
            items.add(item);
    }

    public Item findItem(Item item) {
        if (items.contains(item)) {
            for (Item itemTmp : items) {
                if (itemTmp.equals(item))
                    return itemTmp;
            }
        }

        return null;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void removeItem(int index){
        items.remove(index);
    }

    public int getTotalPrice() {
        return calculatePrice();
    }

    private int calculatePrice() {
        int price = 0;
        for(Item item : items) {
            price += item.getTotalPrice();
        }
        return price;
    }

    @Override
    public String toString() {
        StringBuilder itemsString = new StringBuilder("Items={\n");
        for(Item item : items) {
            itemsString.append(item).append("\n");
        }
        itemsString.append("}");

        return itemsString.toString();
    }

    public static class Item {
        private final Cupcake cupcake;
        private int quantity;

        public Item(Cupcake cupcake, int quantity) {
            this.cupcake = cupcake;
            this.quantity = quantity;
        }

        public Cupcake getCupcake() {
            return cupcake;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getTotalPrice() {
            return (cupcake.getCake().getPrice() + cupcake.getTopping().getPrice()) * quantity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return cupcake.equals(item.cupcake);
        }

        @Override
        public int hashCode() {
            return Objects.hash(cupcake);
        }

        @Override
        public String toString() {
            return "Item{" +
                    "cupcake=" + cupcake +
                    ", quantity=" + quantity +
                    '}';
        }
    }
}
