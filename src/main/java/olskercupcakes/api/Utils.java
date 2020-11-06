package olskercupcakes.api;

import org.apache.commons.text.WordUtils;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Utils {
    Locale locale = new Locale("da", "DK");
    public String formattedPrice(int priceInCents) {
        double price = (double) priceInCents / 100;
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat.format(price);
    }

    public String formattedDate(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy").localizedBy(locale);
        return WordUtils.capitalize(time.format(formatter));
    }
    public String formattedDateTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.ss - d MMMM, yyyy").localizedBy(locale);
        return WordUtils.capitalize(time.format(formatter));
    }
}
