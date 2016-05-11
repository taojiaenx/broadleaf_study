package com.taojiaen.profile.core.service;

import org.broadleafcommerce.profile.core.domain.Customer;

/**
 * 专门用于车牌号管理的类
 * 车牌号存储在一个三元字串数组中
 * [0]归属地
 * [1]归属城市
 * [2]八位数字
 * @author taojiaen
 *
 */
public interface CarplateSerivce {
	public Carplate getCarplate(Customer customer);
	public Carplate putCarplate(Customer customer, Carplate carplate);
}
