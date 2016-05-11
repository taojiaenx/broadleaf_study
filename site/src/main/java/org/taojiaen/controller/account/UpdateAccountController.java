/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.taojiaen.controller.account;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.core.web.controller.account.BroadleafUpdateAccountController;
import org.broadleafcommerce.core.web.controller.account.UpdateAccountForm;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.web.core.CustomerState;
import org.broadleafcommerce.profile.web.core.form.RegisterCustomerForm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.taojiaen.profile.core.service.Carplate;
import com.taojiaen.profile.core.service.CarplateSerivce;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/account")
public class UpdateAccountController extends BroadleafUpdateAccountController {
	@Resource(name="blUserDetailsService")
    private UserDetailsService userDetailsService;
	@Resource(name = "tjeCarplateService")
	protected CarplateSerivce carplateService;
	

    @RequestMapping(method = RequestMethod.GET)
    public String viewUpdateAccount(HttpServletRequest request, Model model, @ModelAttribute("updateAccountForm") MyUpdateAccessForm form) {
    	Customer customer = CustomerState.getCustomer();
        form.setEmailAddress(customer.getEmailAddress());
        form.setFirstName(customer.getFirstName());
        form.setLastName(customer.getLastName());
        form.setPhoneNumber(customer.getUsername());
        Carplate carplate = carplateService.getCarplate(customer);
        if (carplate != null) {
        	form.setCarplateProvince(carplate.getCarplateProvince());
        	form.setCarplateCity(carplate.getCarplateCity());
        	form.setCarplateNumber(carplate.getCarplateNumber());
        }
        
        return getUpdateAccountView();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processUpdateAccount(HttpServletRequest request, Model model, @ModelAttribute("updateAccountForm") MyUpdateAccessForm form, BindingResult result, RedirectAttributes redirectAttributes) throws ServiceException {
       updateAccountValidator.validate(form, result);

        
        if (result.hasErrors()) {
            return getUpdateAccountView();
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
    		throw new AuthenticationCredentialsNotFoundException("Authentication was null, not authenticated, or not logged in.");
    	}
        
        Customer customer = CustomerState.getCustomer();
        customer.setEmailAddress(form.getEmailAddress());
        customer.setFirstName(form.getFirstName());
        customer.setLastName(form.getLastName());
        if (!StringUtils.hasLength((form.getCarplateNumber()))) {
        	form.setCarplateNumber("");
        }
        if (StringUtils.hasText(form.getCarplateProvince())
        		&& StringUtils.hasText(form.getCarplateCity())) {
        carplateService.putCarplate(customer, new Carplate(form.getCarplateProvince(), form.getCarplateCity(), form.getCarplateNumber()));
        }
        
       // if (useEmailForLogin) {
            customer.setUsername(form.getPhoneNumber());
        //}
        
        customer = customerService.saveCustomer(customer);
        
        if (useEmailForLogin) {
        	UserDetails principal = userDetailsService.loadUserByUsername(customer.getUsername());
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), auth.getAuthorities());
            
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        
        redirectAttributes.addFlashAttribute("successMessage", getAccountUpdatedMessage());
        return getAccountRedirectView();
    }

}
