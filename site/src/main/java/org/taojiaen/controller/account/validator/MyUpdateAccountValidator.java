package org.taojiaen.controller.account.validator;

import org.apache.commons.validator.GenericValidator;
import org.broadleafcommerce.core.web.controller.account.UpdateAccountForm;
import org.broadleafcommerce.core.web.controller.account.validator.UpdateAccountValidator;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.taojiaen.controller.account.MyUpdateAccessForm;

public class MyUpdateAccountValidator extends UpdateAccountValidator {
	protected final  String validatePhoneExpression = "^(0|86|17951)?[0-9]{11}$";

	@Override
	public void validate(UpdateAccountForm form, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "username.phone.required");

		if (!errors.hasErrors()) {

			// is this a valid email address?
			if (form.getEmailAddress() == null && StringUtils.hasText(form.getEmailAddress().toString())) {
				if (!GenericValidator.isEmail(form.getEmailAddress())) {
					errors.rejectValue("emailAddress", "emailAddress.invalid");
				}

				// check email address to see if it is already in use by another
				// customer
				Customer customerMatchingNewEmail = customerService.readCustomerByEmail(form.getEmailAddress());

				if (customerMatchingNewEmail != null
						&& !CustomerState.getCustomer().getId().equals(customerMatchingNewEmail.getId())) {
					// customer found with new email entered, and it is not the
					// current customer
					errors.rejectValue("emailAddress", "emailAddress.used");
				}
			}

			if (form instanceof MyUpdateAccessForm) {
				if (!((MyUpdateAccessForm) form).getPhoneNumber().matches(getValidatePhoneExpression())) {
	                errors.rejectValue("phoneNumber", "username.phone.invalid", null, null);
	            } 
				
				
				Customer customerMatchingNewPhoneNumber = customerService
						.readCustomerByUsername(((MyUpdateAccessForm) form).getPhoneNumber());
				if (customerMatchingNewPhoneNumber != null
						&& !CustomerState.getCustomer().getId().equals(customerMatchingNewPhoneNumber.getId())) {
					// customer found with new email entered, and it is not the
					// current customer
					errors.rejectValue("phoneNumber", "username.phone.used");
				}
			}

		}

	}
	private String getValidatePhoneExpression() {
		return validatePhoneExpression;
	}
}
