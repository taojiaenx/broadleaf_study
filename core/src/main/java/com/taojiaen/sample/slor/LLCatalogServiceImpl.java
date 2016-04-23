package com.taojiaen.sample.slor;

import java.io.IOException;

import javax.annotation.Resource;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.service.CatalogServiceImpl;
import org.springframework.transaction.annotation.Transactional;

public class LLCatalogServiceImpl extends CatalogServiceImpl{
	@Resource(name = "blSearchService")
	private ExtSolrSearchService extSolrSearchService;
	@Override
	@Transactional("blTransactionManager")
	public Product saveProduct(Product product) {
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
}
