package com.taojiaen.sample.slor;

import org.broadleafcommerce.core.search.service.SearchService;

import java.io.IOException;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.core.catalog.domain.Product;

public interface ExtSolrSearchService extends SearchService {
    public void addProductIndex(Product product) throws ServiceException,
            IOException;
}
