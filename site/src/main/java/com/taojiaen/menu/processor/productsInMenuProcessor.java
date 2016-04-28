package com.taojiaen.menu.processor;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.common.web.dialect.AbstractModelVariableModifierProcessor;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.service.CatalogService;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

/**
 * 从指定定category中获取products
 * @author taojiaen
 *
 */
public class productsInMenuProcessor extends AbstractModelVariableModifierProcessor{
	private static final String RESULT_VAR = "menuProducts";
	@Resource(name = "blCatalogService")
    protected CatalogService catalogService;

	public productsInMenuProcessor() {
		super("products_in_menu");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void modifyModelAttributes(Arguments arguments, Element element) {
		final Category category = catalogService.findCategoryById(Long.parseLong(element.getAttributeValue("CategoryId")));
		List<Product> products = catalogService.findActiveProductsByCategory(category, 6, 0);
		addToModel(arguments, RESULT_VAR, products);
		
	}

}
