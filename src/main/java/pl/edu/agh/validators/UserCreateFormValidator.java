package pl.edu.agh.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.edu.agh.forms.UserCreateForm;
import pl.edu.agh.services.UserService;

@Component
public class UserCreateFormValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserCreateFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCreateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateForm form = (UserCreateForm) target;
        validatePasswords(errors, form);
        validateLogin(errors, form);
    }

    private void validatePasswords(Errors errors, UserCreateForm form) {
        if (!form.getPassword().equals(form.getPassword())) {
            errors.reject("password.no_match", "Passwords do not match");
        }
    }

    private void validateLogin(Errors errors, UserCreateForm form) {
        if (userService.findByLogin(form.getLogin()).isPresent()) {
            errors.reject("login.exists", "User with this login already exists");
        }
    }
}
