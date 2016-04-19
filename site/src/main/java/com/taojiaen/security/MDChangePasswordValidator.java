package com.taojiaen.security;

import javax.annotation.Resource;

import org.broadleafcommerce.common.security.util.PasswordChange;
import org.broadleafcommerce.core.web.controller.account.validator.ChangePasswordValidator;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class MDChangePasswordValidator extends ChangePasswordValidator{
	@Resource(name = "mdPasswordEncoder")
	private MessageDigestPasswordEncoder encoder;
	
	@Override
	public void validate(PasswordChange passwordChange, Errors errors) {

        String currentPassword = encoder.encodePassword(passwordChange.getCurrentPassword(),
        		CustomerState.getCustomer().getUsername());
        String password = passwordChange.getNewPassword();
        String passwordConfirm = passwordChange.getNewPasswordConfirm();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPassword", "currentPassword.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "newPassword.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPasswordConfirm", "newPasswordConfirm.required");

        if (!errors.hasErrors()) {
            //validate current password
            if (!customerService.isPasswordValid(currentPassword, CustomerState.getCustomer().getPassword(), CustomerState.getCustomer())) {
                errors.rejectValue("currentPassword", "currentPassword.invalid");
            }
            //password and confirm password fields must be equal
            if (!passwordConfirm.equals(password)) {
                errors.rejectValue("newPasswordConfirm", "newPasswordConfirm.invalid");
            }
            //restrict password characteristics
            if (!password.matches(getValidPasswordRegex())) {
                errors.rejectValue("newPassword", "newPassword.invalid");
            }
        }

    }
}
