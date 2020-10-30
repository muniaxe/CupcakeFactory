package olskercupcakes.domain.cupcake;

public class Cupcake {

    private final Topping topping;
    private final Cake cake;
    private final int id;

    public Cupcake(int id, Topping topping, Cake cake) {
        this.topping = topping;
        this.cake = cake;
        this.id = id;
    }

    public static class Topping{
        private final String name;
        private final int price;
        private final int id;

        public Topping(int id, String name, int price) {
            this.name = name;
            this.price = price;
            this.id = id;
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
    }
}
