package com.taojiaen.menu;

import org.broadleafcommerce.menu.dto.MenuItemDTO;

public class ExtMenuItemDTO extends MenuItemDTO{
	private String menuType;
	private String categoryId;

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
}
