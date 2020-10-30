package olskercupcakes.api;

import java.text.NumberFormat;
import java.util.Locale;

public class Utils {
    public String formattedPrice(int priceInCents) {
        double price = (double) priceInCents / 100;
        Locale locale = new Locale("da", "DK");
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat.format(price);
    }
}
