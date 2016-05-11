package com.taojiaen.profile.core.service;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerAttribute;
import org.broadleafcommerce.profile.core.domain.CustomerAttributeImpl;

public class CarplateServiceImpl implements CarplateSerivce{
	
	protected static final String CAR_PLATE_PROVICE="CAR_PLATE_PROVICE";
	protected static final String CAR_PLATE_CITY = "CAR_PLATE_CITY";
	protected static final String CAR_PLATE_NUMBER = "CAR_PLATE_NUMBER";

	@Override
	public Carplate getCarplate(final Customer customer) {
		final Carplate result = new Carplate();
		CustomerAttribute attrProvice  = customer.getCustomerAttributes().get(CAR_PLATE_PROVICE);
		if (attrProvice != null) {
			result.setCarplateProvince(attrProvice.getValue());
		}
		CustomerAttribute attrCity = customer.getCustomerAttributes().get(CAR_PLATE_CITY);
		if (attrCity != null) {
			result.setCarplateCity(attrCity.getValue());
		}
		CustomerAttribute attrNumber = customer.getCustomerAttributes().get(CAR_PLATE_NUMBER);
		if (attrNumber != null) {
			result.setCarplateNumber(attrNumber.getValue());
		}
		return result;
	}

	@Override
	public Carplate putCarplate(final Customer customer, final Carplate carplate) {
		CustomerAttribute attrProvice  = new CustomerAttributeImpl();
		attrProvice.setName(CAR_PLATE_PROVICE);
		attrProvice.setValue(carplate.getCarplateProvince());
		attrProvice.setCustomer(customer);
		customer.getCustomerAttributes().put(CAR_PLATE_PROVICE, attrProvice);
		CustomerAttribute attrCity  = new CustomerAttributeImpl();
		attrCity.setName(CAR_PLATE_CITY);
		attrCity.setValue(carplate.getCarplateCity());
		attrCity.setCustomer(customer);
		customer.getCustomerAttributes().put(CAR_PLATE_CITY, attrCity);
		CustomerAttribute attrNumber  = new CustomerAttributeImpl();
		attrNumber.setName(CAR_PLATE_NUMBER);
		attrNumber.setValue(carplate.getCarplateNumber());
		attrNumber.setCustomer(customer);
		customer.getCustomerAttributes().put(CAR_PLATE_NUMBER, attrNumber);
		return carplate;
	}


}
