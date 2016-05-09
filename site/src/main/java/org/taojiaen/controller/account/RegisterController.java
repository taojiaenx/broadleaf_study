/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.taojiaen.controller.account;

import org.apache.commons.lang.StringUtils;
import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.core.order.domain.NullOrderImpl;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;
import org.broadleafcommerce.core.web.controller.account.BroadleafRegisterController;
import org.broadleafcommerce.core.web.order.CartState;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.web.core.form.RegisterCustomerForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The controller responsible for registering a customer
 */
@Controller
@RequestMapping("/register")
public class RegisterController extends BroadleafRegisterController {

	@RequestMapping(method = RequestMethod.GET)
	public String register(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute("registrationForm") RegisterCustomerForm registerCustomerForm) {
		return super.register(registerCustomerForm, request, response, model);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processRegister(HttpServletRequest request, HttpServletResponse response, Model model,
            @ModelAttribute("registrationForm") RegisterCustomerForm registerCustomerForm, BindingResult errors)
			throws ServiceException, PricingException {
		String unencodedPassword = registerCustomerForm.getPassword();
		if (useEmailForLogin) {
			Customer customer = registerCustomerForm.getCustomer();
			customer.setUsername(customer.getEmailAddress());
		}
		


		registerCustomerValidator.validate(registerCustomerForm, errors, useEmailForLogin);
		if (!errors.hasErrors()) {
			Customer newCustomer = customerService.registerCustomer(registerCustomerForm.getCustomer(),
					registerCustomerForm.getPassword(), registerCustomerForm.getPasswordConfirm());
			assert (newCustomer != null);

			// The next line needs to use the customer from the input form and
			// not the customer returned after registration
			// so that we still have the unencoded password for use by the
			// authentication mechanism.
			// loginService.loginCustomer(registerCustomerForm.getCustomer());
			// #### Store hashed password in a variable
			String codedPassword = registerCustomerForm.getCustomer().getPassword();

			// #### SET UNENCODED PASSWORD AS PLAIN ONE FROM ABOVE
			newCustomer.setUnencodedPassword(unencodedPassword);

			// #### REPLACE WITH "newCustomer" for loginService.
			loginService.loginCustomer(newCustomer);

			// AFTER LOGIN RE-SET THE HASHED PASSWORD - Because loginService
			// resets it to unencodedPassword.
			newCustomer.setUnencodedPassword(codedPassword);
			newCustomer.setPassword(codedPassword);
			customerService.saveCustomer(newCustomer);

			// Need to ensure that the Cart on CartState is owned by the newly
			// registered customer.
			Order cart = CartState.getCart();
			if (cart != null && !(cart instanceof NullOrderImpl) && cart.getEmailAddress() == null) {
				cart.setEmailAddress(newCustomer.getEmailAddress());
				orderService.save(cart, false);
			}

			String redirectUrl = registerCustomerForm.getRedirectUrl();
			if (StringUtils.isNotBlank(redirectUrl) && redirectUrl.contains(":")) {
				redirectUrl = null;
			}
			return StringUtils.isBlank(redirectUrl) ? getRegisterSuccessView() : "redirect:" + redirectUrl;
		} else {
			return getRegisterView();
		}
	}

	@ModelAttribute("registrationForm")
	public RegisterCustomerForm initCustomerRegistrationForm() {
		return super.initCustomerRegistrationForm();
	}
}
