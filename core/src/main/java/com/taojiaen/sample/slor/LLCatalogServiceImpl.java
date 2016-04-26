package com.taojiaen.sample.slor;

import java.io.IOException;

import javax.annotation.Resource;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.service.CatalogServiceImpl;
import org.springframework.transaction.annotation.Transactional;

public class LLCatalogServiceImpl extends CatalogServiceImpl{
	@Resource(name = "blSearchService")
	private ExtSolrSearchService extSolrSearchService;
	@Override
	@Transactional("blTransactionManager")
	public Product saveProduct(Product product) {
		System.out.println("存了存了！！！！！！");;
		
	    Product dbProduct = super.saveProduct(product);
	    try {
	        extSolrSearchService.addProductIndex(dbProduct);
	    } catch (ServiceException e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    } catch (IOException e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    }
	    return dbProduct;
	}
	
	@Override
    public Product findProductById(Long productId) {
		System.out.println("找到了找到了！！！！！！");
        return super.findProductById(productId);
    }
	
	 @Override
	    @Transactional("blTransactionManager")
	  public void removeCategory(Category category){
	       super.removeCategory(category);
	 }
}
