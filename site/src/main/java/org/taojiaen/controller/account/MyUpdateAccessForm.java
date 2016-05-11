package org.taojiaen.controller.account;

import org.broadleafcommerce.core.web.controller.account.UpdateAccountForm;

public class MyUpdateAccessForm extends UpdateAccountForm{
	private String phoneNumber;
	private  String carplateProvince;
	private  String carplateCity;
	private  String carplateNumber;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCarplateProvince() {
		return carplateProvince;
	}

	public void setCarplateProvince(String carplateProvince) {
		if (carplateProvince == null) {
			carplateProvince="";
		}
		this.carplateProvince = carplateProvince;
	}

	public String getCarplateCity() {
		return carplateCity;
	}

	public void setCarplateCity(String carplateCity) {
		if (carplateCity == null) {
			carplateCity = "";
		}
		this.carplateCity = carplateCity;
	}

	public String getCarplateNumber() {
		return carplateNumber;
	}

	public void setCarplateNumber(String carplateNumber) {
		this.carplateNumber = carplateNumber;
	}
	
}
