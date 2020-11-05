package olskercupcakes.domain.user;

import olskercupcakes.domain.validation.ValidationErrorException;
import org.apache.commons.validator.routines.EmailValidator;

public abstract class UserFactory {
    private String email;
    private String password;
    private String passwordConfirm;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void validate() throws ValidationErrorException {
        ValidationErrorException validationErrorException = new ValidationErrorException();
        if(password == null || password.length() < 3)
            validationErrorException.addProblem("password", "Adgangskoden kan ikke være mindre end 3 tegn.");
        if(!password.equals(passwordConfirm))
            validationErrorException.addProblem("password", "Adgangskoderne matcher ikke.");
        EmailValidator emailValidator = EmailValidator.getInstance();
        if(email == null || !emailValidator.isValid(email))
            validationErrorException.addProblem("email", "Denne e-mail er ikke gyldig.");

        validationErrorException.validate();
    }

    public User validateAndCommit() throws ValidationErrorException {
        validate();
        return commit();
    }

    protected abstract User commit();
}
