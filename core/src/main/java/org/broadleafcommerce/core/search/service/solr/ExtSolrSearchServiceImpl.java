package org.broadleafcommerce.core.search.service.solr;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.util.StopWatch;
import org.broadleafcommerce.common.util.TransactionUtils;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.search.domain.Field;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.xml.sax.SAXException;

import com.taojiaen.sample.slor.ExtSolrSearchService;
 
public class ExtSolrSearchServiceImpl extends SolrSearchServiceImpl implements
        ExtSolrSearchService {
    private static final Log LOG = LogFactory
            .getLog(ExtSolrSearchServiceImpl.class);
    @Resource(name = "blSolrIndexService")
    protected SolrIndexServiceImpl solrIndexServiceImpl;
    public ExtSolrSearchServiceImpl(SolrServer solrServer,
            SolrServer reindexServer) {
        super(solrServer, reindexServer);
    }
    public ExtSolrSearchServiceImpl(SolrServer solrServer) {
        super(solrServer);
    }
    public ExtSolrSearchServiceImpl(String solrServer, String reindexServer)
            throws IOException, ParserConfigurationException, SAXException {
        super(solrServer, reindexServer);
    }
    public ExtSolrSearchServiceImpl(String solrServer) throws IOException,
            ParserConfigurationException, SAXException {
        super(solrServer);
    }
    public void addProductIndex(Product product) throws ServiceException,
            IOException {
        TransactionStatus status = TransactionUtils.createTransaction(
                "saveProduct", TransactionDefinition.PROPAGATION_REQUIRED,
                solrIndexServiceImpl.transactionManager, true);
        StopWatch s = new StopWatch();
        try {
            List<Field> fields = fieldDao.readAllProductFields();
            List<Locale> locales = solrIndexServiceImpl.getAllLocales();
            SolrInputDocument document = solrIndexServiceImpl.buildDocument(
                    product, fields, locales);
            if (LOG.isTraceEnabled()) {
                LOG.trace(document);
            }
            SolrContext.getServer().add(document);
            SolrContext.getServer().commit();
            TransactionUtils.finalizeTransaction(status,
                    solrIndexServiceImpl.transactionManager, false);
        } catch (SolrServerException e) {
            TransactionUtils.finalizeTransaction(status,
                    solrIndexServiceImpl.transactionManager, true);
            throw new ServiceException("Could not rebuild index", e);
        } catch (IOException e) {
            TransactionUtils.finalizeTransaction(status,
                    solrIndexServiceImpl.transactionManager, true);
            throw new ServiceException("Could not rebuild index", e);
        } catch (RuntimeException e) {
            TransactionUtils.finalizeTransaction(status,
                    solrIndexServiceImpl.transactionManager, true);
            throw e;
        }
        LOG.info(String.format("Finished adding index in %s", s.toLapString()));
    }
    protected List<Product> getProducts(QueryResponse response) {
        final List<Long> productIds = new ArrayList<Long>();
        SolrDocumentList docs = response.getResults();
        for (SolrDocument doc : docs) {
            productIds
                    .add((Long) doc.getFieldValue(shs.getProductIdFieldName()));
        }
        /**
         * TODO 请添加缓存相关代码
         */
        List<Product> products = productDao.readProductsByIds(productIds);
        // We have to sort the products list by the order of the productIds list
        // to maintain sortability in the UI
        if (products != null) {
            Collections.sort(products, new Comparator<Product>() {
                public int compare(Product o1, Product o2) {
                    return new Integer(productIds.indexOf(o1.getId()))
                            .compareTo(productIds.indexOf(o2.getId()));
                }
            });
        }
        return products;
    }
}