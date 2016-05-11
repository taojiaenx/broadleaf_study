package com.taojiaen.security;

import javax.annotation.Resource;

import org.apache.commons.validator.GenericValidator;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.CustomerService;
import org.broadleafcommerce.profile.web.controller.validator.RegisterCustomerValidator;
import org.broadleafcommerce.profile.web.core.form.RegisterCustomerForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.taojiaen.controller.account.MyRegisterForm;
import org.springframework.util.StringUtils;

public class MyRegisterCustomerValidator extends RegisterCustomerValidator {

	// protected final String validatePhoneExpression =
	// "^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$";
	protected final String validatePhoneExpression = "^(0|86|17951)?[0-9]{11}$";
	protected final String validateCarplateExpression = "[0-9A-Za-z]{5,8}";

	@Resource(name = "blCustomerService")
	private CustomerService customerService;

	@Override
	 public void validate(Object obj, Errors errors, boolean useEmailForUsername){
		 MyRegisterForm form = (MyRegisterForm) obj;

        Customer customerFromDb = customerService.readCustomerByUsername(form.getCustomer().getUsername());

        if (customerFromDb != null) {
            if (useEmailForUsername) {
                errors.rejectValue("customer.emailAddress", "emailAddress.used", null, null);
            } else {
                errors.rejectValue("customer.username", "username.phone.used", null, null);
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "passwordConfirm.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer.username", "username.phone.required");
        
        errors.pushNestedPath("customer");
    //    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.required");
    //    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.required");
      //  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "emailAddress.required");
        errors.popNestedPath();


        if (!errors.hasErrors()) {

            if (!form.getPassword().matches(getValidatePasswordExpression())) {
                errors.rejectValue("password", "password.invalid", null, null);
            }

            if (!form.getPassword().equals(form.getPasswordConfirm())) {
                errors.rejectValue("password", "passwordConfirm.invalid", null, null);
            }

            if (!form.getCustomer().getUsername().matches(getValidatePhoneExpression())) {
                errors.rejectValue("customer.username", "username.phone.invalid", null, null);
            } 
            
            if (StringUtils.hasText(form.getCarplateNumber())
            		 && !form.getCarplateNumber().matches(getValidateCarplateExpression())) {
            	errors.rejectValue("carplateNumber", "carplate.invalid", null, null);
            }
        }
	}

	private String getValidatePhoneExpression() {
		return validatePhoneExpression;
	}

	public String getValidateCarplateExpression() {
		return validateCarplateExpression;
	}

}
