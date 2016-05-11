package org.taojiaen.processor;

import java.util.ArrayList;
import java.util.List;

import org.broadleafcommerce.common.web.dialect.AbstractModelVariableModifierProcessor;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

public class AllPlateDataProcessor extends AbstractModelVariableModifierProcessor {
	private static final String ALL_PROVINCE = "allProvince";
	private static final String ALL_CITY = "allcity";

	public AllPlateDataProcessor() {
		super("all_province");
		// TODO Auto-generated constructor stub
	}

	private static final String[] ALL_PROVINCES = new String[] { "浙", "京", "津", "沪", "渝", "冀", "豫", "云", "辽", "黑", "湘",
			"皖", "鲁", "新", "苏", "赣", "鄂", "桂", "甘", "晋", "琼", "陕", "陕", "闽", "贵", "粤", "青", "藏", "川", "宁", "琼" };
	private static String[] ALL_CITIES = new String[] { "F", "A", "B", "C", "D", "E", "G", "H", "I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	@Override
	protected void modifyModelAttributes(Arguments arguments, Element element) {

		if (ALL_PROVINCE.equals(element.getAttributeValue("type"))) {

			addToModel(arguments, ALL_PROVINCE, ALL_PROVINCES);
		} else if (ALL_CITY.equals(element.getAttributeValue("type"))) {
			addToModel(arguments, ALL_CITY, ALL_CITIES);
		}
	}
}
