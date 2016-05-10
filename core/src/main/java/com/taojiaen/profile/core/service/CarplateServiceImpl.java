package com.taojiaen.profile.core.service;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerAttribute;
import org.broadleafcommerce.profile.core.domain.CustomerAttributeImpl;

public class CarplateServiceImpl implements CarplateSerivce{
	
	protected static final String CAR_PLATE="CAR_PLAT";

	@Override
	public String getCarplate(final Customer customer) {
		CustomerAttribute attr  = customer.getCustomerAttributes().get(CAR_PLATE);
		if (attr != null) {
			return attr.getValue();
		}
		return "";
	}

	@Override
	public String putCarplate(final Customer customer, final String carplate) {
		CustomerAttribute attr = new CustomerAttributeImpl();
		attr.setName(CAR_PLATE);
		attr.setValue(carplate);
		customer.getCustomerAttributes().put(CAR_PLATE, attr);
		return carplate;
	}


}
