package olskercupcakes.domain.order;

import olskercupcakes.domain.user.User;
import olskercupcakes.domain.validation.ValidationErrorException;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;
import java.util.UUID;

public abstract class OrderFactory {

    private UUID uuid;
    private User user;
    private Cart cart;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void validate() throws ValidationErrorException {
        ValidationErrorException validationErrorException = new ValidationErrorException();
        if(getCart().getItems().isEmpty())
            validationErrorException.addProblem("cart", "Din kurv er tom, du kan ikke bestille med en tom kurv.");
        if(getCart().getTotalPrice() > getUser().getBalance())
            validationErrorException.addProblem("balance", "Du har ikke nok penge på din konto for at betale for denne ordre. Hvis dette er en fejl, kontakt os venligst.");

        validationErrorException.validate();
    }

    public Order validateAndCommit() throws ValidationErrorException {
        validate();
        return commit();
    }

    protected abstract Order commit();
}

