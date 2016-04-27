package com.taojiaen.menu;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.CategoryXref;
import org.broadleafcommerce.menu.domain.MenuItem;
import org.broadleafcommerce.menu.dto.MenuItemDTO;
import org.broadleafcommerce.menu.service.MenuServiceImpl;
import org.broadleafcommerce.menu.type.MenuItemType;

/**
 * 可以获取menuType的menuService
 * @author taojiaen
 *
 */
public class ExtMenuServiceimpl extends MenuServiceImpl{

	@Override
	    protected MenuItemDTO convertMenuItemToDTO(MenuItem menuItem) {

	        Category category = null;
	        if (MenuItemType.CATEGORY.equals(menuItem.getMenuItemType())) {
	            category = catalogService.findCategoryByURI(menuItem.getActionUrl());
	        }

	        if (MenuItemType.SUBMENU.equals(menuItem.getMenuItemType()) &&
	                menuItem.getLinkedMenu() != null) {
	        	ExtMenuItemDTO dto = new ExtMenuItemDTO();
	            dto.setUrl(menuItem.getDerivedUrl());
	            dto.setLabel(menuItem.getDerivedLabel());

	            List<MenuItemDTO> submenu = new ArrayList<MenuItemDTO>();
	            List<MenuItem> items = menuItem.getLinkedMenu().getMenuItems();
	            if (CollectionUtils.isNotEmpty(items)) {
	                for (MenuItem item : items) {
	                    submenu.add(convertMenuItemToDTO(item));
	                }
	            }

	            dto.setSubmenu(submenu);
	            dto.setMenuType(menuItem.getMenuItemType().getType());
	            return dto;
	        } else if (category != null) {
	            return convertCategoryToMenuItemDTO(category);
	        } else {
	        	ExtMenuItemDTO dto = new ExtMenuItemDTO();
	            dto.setUrl(menuItem.getDerivedUrl());
	            dto.setLabel(menuItem.getDerivedLabel());
	            if (menuItem.getImage() != null) {
	                dto.setImageUrl(menuItem.getImage().getUrl());
	                dto.setAltText(menuItem.getAltText());
	            }
	            dto.setMenuType(menuItem.getMenuItemType().getType());
	            return dto;
	        }

	    }
	@Override
	protected MenuItemDTO convertCategoryToMenuItemDTO(Category category) {
		ExtMenuItemDTO dto = new ExtMenuItemDTO();
        dto.setLabel(category.getName());
        dto.setUrl(category.getUrl());

        List<CategoryXref> categoryXrefs = category.getChildCategoryXrefs();

        if (CollectionUtils.isNotEmpty(categoryXrefs)) {
            List<MenuItemDTO> submenu = new ArrayList<MenuItemDTO>();
            for (CategoryXref xref : categoryXrefs) {
                submenu.add(convertCategoryToMenuItemDTO(xref.getSubCategory()));
            }

            dto.setSubmenu(submenu);
        }
        dto.setMenuType(MenuItemType.CATEGORY.getType());
        return dto;
    }
}
