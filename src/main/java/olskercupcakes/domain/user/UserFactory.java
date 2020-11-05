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
            validationErrorException.addProblem("password", "Password can not be less than 3 characters.");
        if(!password.equals(passwordConfirm))
            validationErrorException.addProblem("password", "Passwords does not match.");
        EmailValidator emailValidator = EmailValidator.getInstance();
        if(!emailValidator.isValid(email))
            validationErrorException.addProblem("email", "E-mail is not an email.");

        validationErrorException.validate();
    }

    public User validateAndCommit() throws ValidationErrorException {
        validate();
        return commit();
    }

    protected abstract User commit();
}
