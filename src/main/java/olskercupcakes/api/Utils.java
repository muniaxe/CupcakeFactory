package olskercupcakes.api;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Utils {
    public String formattedPrice(int priceInCents) {
        double price = (double) priceInCents / 100;
        Locale locale = new Locale("da", "DK");
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat.format(price);
    }

    public String formattedDateTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        String formattedTime = time.format(formatter);
        return formattedTime;
    }
}
