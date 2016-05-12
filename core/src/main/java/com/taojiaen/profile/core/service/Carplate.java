package com.taojiaen.profile.core.service;

public class Carplate {
	private String carplateProvince="";
	private String carplateCity="";
	private String carplateNumber="";
	
	public Carplate(){}
	public Carplate(String carplateProvince, String carplateCity, String carplateNumber){
		this.carplateProvince = carplateProvince.toUpperCase();
		this.carplateCity = carplateCity.toUpperCase();
		this.carplateNumber = carplateNumber.toUpperCase();
	}
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
		this.carplateNumber = carplateNumber.toUpperCase();
	}
	
}
