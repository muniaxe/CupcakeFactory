package olskercupcakes.domain.user;

import olskercupcakes.domain.validation.ValidationErrorException;

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

    public void validate() throws ValidationErrorException {
        ValidationErrorException validationErrorException = new ValidationErrorException();
        //passwords match...
        //email is an email...

        validationErrorException.validate();
    }

    public int validateAndCommit() throws ValidationErrorException {
        validate();
        return commit();
    }

    protected abstract int commit();
}
