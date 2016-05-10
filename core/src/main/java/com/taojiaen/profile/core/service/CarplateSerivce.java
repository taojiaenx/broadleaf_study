package com.taojiaen.profile.core.service;

import org.broadleafcommerce.profile.core.domain.Customer;

public interface CarplateSerivce {
	public String getCarplate(Customer customer);
	public String putCarplate(Customer customer, String carplate);
}
