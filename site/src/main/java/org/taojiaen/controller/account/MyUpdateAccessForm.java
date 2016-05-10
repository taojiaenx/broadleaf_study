package org.taojiaen.controller.account;

import org.broadleafcommerce.core.web.controller.account.UpdateAccountForm;

public class MyUpdateAccessForm extends UpdateAccountForm{
	private String phoneNumber;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
