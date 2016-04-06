package org.taojiaen.processor;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.common.web.dialect.AbstractModelVariableModifierProcessor;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.service.CatalogService;
import org.springframework.stereotype.Component;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

/**
 * 这是一个processor用于显示所有商品分类
 * @author taojiaen
 *
 */
@Component("myAllcategoriesProcessor")
public class AllcategoriesProcessor extends AbstractModelVariableModifierProcessor{
	private static final String RESULT_VAR = "categorites";
	private static final long  CUSTOM_CATEGORY_ID = 2001;
	private static final String CUSTOM_HOME_NAME = "所有卡片";
	
	
	 @Resource(name = "blCatalogService")
	    protected CatalogService catalogService;

	public AllcategoriesProcessor() {
		super("all_categories");
	}
	@Override
    public int getPrecedence() {
        return 10000;
    }

	@Override
	protected void modifyModelAttributes(Arguments arguments, Element element) {
		List<Category> categories = catalogService.findAllCategories();
		for(Category category : categories) {
			if (category.getId()  == CUSTOM_CATEGORY_ID) {
			   category.setName(CUSTOM_HOME_NAME);
			}
		}
		addToModel(arguments, RESULT_VAR, categories);
	}

}
