package com.taojiaen.security;
import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.CustomerServiceImpl;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.taojiaen.util.CommonUtils;

public class MDCustomerServiceImpl extends CustomerServiceImpl{
	 @Resource(name = "mdPasswordEncoder")
	private MessageDigestPasswordEncoder encoder;
    @Override
    public Customer registerCustomer(Customer customer, String password, String passwordConfirm) {
       password =  encoder.encodePassword(password, customer.getUsername());
       passwordConfirm = encoder.encodePassword(passwordConfirm, customer.getUsername());
       return super.registerCustomer(customer, password, passwordConfirm);
    }
}
