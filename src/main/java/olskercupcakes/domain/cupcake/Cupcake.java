package olskercupcakes.domain.cupcake;

import java.util.Objects;

public class Cupcake {

    private final Cake cake;
    private final Topping topping;

    public Cupcake(Cake cake, Topping topping) {
        this.cake = cake;
        this.topping = topping;
    }

    public Topping getTopping() {
        return topping;
    }

    public Cake getCake() {
        return cake;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cupcake cupcake = (Cupcake) o;
        return Objects.equals(topping, cupcake.topping) &&
                Objects.equals(cake, cupcake.cake);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topping, cake);
    }

    @Override
    public String toString() {
        return "Cupcake{" +
                "cake=" + cake +
                ", topping=" + topping +
                '}';
    }

    /*


    Topping and Cake classes:


     */

    public static class Topping{
        private final String name;
        private final int price;
        private final int id;

        public Topping(int id, String name, int price) {
            this.name = name;
            this.price = price;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Topping{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    ", id=" + id +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Topping topping = (Topping) o;
            return id == topping.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    public static class Cake {
        private final String name;
        private final int price;
        private final int id;

        public Cake(int id, String name, int price) {
            this.name = name;
            this.price = price;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Cake{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    ", id=" + id +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cake cake = (Cake) o;
            return id == cake.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
