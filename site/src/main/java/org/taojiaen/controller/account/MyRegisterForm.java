package org.taojiaen.controller.account;

import org.broadleafcommerce.profile.web.core.form.RegisterCustomerForm;
import org.springframework.web.bind.annotation.ModelAttribute;

public class MyRegisterForm extends RegisterCustomerForm{
	private  String carplateProvince;
	private  String carplateCity;
	private  String carplateNumber;
	public String getCarplateProvince() {
		return carplateProvince;
	}
	public void setCarplateProvince(String carplateProvice) {
		this.carplateProvince = carplateProvice;
	}
	public String getCarplateCity() {
		return carplateCity;
	}
	public void setCarplateCity(String carplateCity) {
		this.carplateCity = carplateCity;
	}
	public String getCarplateNumber() {
		return carplateNumber;
	}
	public void setCarplateNumber(String carplateNumber) {
		this.carplateNumber = carplateNumber;
	}
}
