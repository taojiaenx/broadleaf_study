package com.taojiaen.sample.vendor.aliPaymentGateway.web.processor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.broadleafcommerce.common.payment.dto.PaymentRequestDTO;
import org.broadleafcommerce.common.payment.dto.PaymentResponseDTO;
import org.broadleafcommerce.common.payment.service.PaymentGatewayHostedService;
import org.broadleafcommerce.common.vendor.service.exception.PaymentException;
import org.springframework.stereotype.Component;
import org.taojiaen.sample.vendor.nullPaymentGateway.service.payment.NullPaymentGatewayConstants;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor;
import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor.ModificationType;
import org.thymeleaf.standard.expression.StandardExpressionProcessor;

/**
 * <p>A Thymeleaf processor that will generate a Mock Hosted Link given a passed in PaymentRequestDTO.</p>
 *
 * <pre><code>
 * <form blc:ali_payment_hosted_action="${paymentRequestDTO}" complete_checkout="${false}" method="POST">
 *   <input type="image" blc:src="@{/img/alipay_logo.png}" align="left" style="margin-right:7px;" alt="Submit Form" />
 * </form>
 * </code></pre>
 *
 * In order to use this sample processor, you will need to component scan
 * the package "org.taojiaen.sample".
 *
 * This should NOT be used in production, and is meant solely for demonstration
 * purposes only.
 *
 * @author taojiaen
 */
@Component("blAlipaymentPaymentGatewayHostedActionProcessor")
public class AlipayPaymentGatewayHostedActionProcessor extends AbstractAttributeModifierAttrProcessor{
	public AlipayPaymentGatewayHostedActionProcessor(){
		super("ali_payment_hosted_action"); 
	}
    @Resource(name = "blNullPaymentGatewayHostedService")
    private PaymentGatewayHostedService paymentGatewayHostedService;


    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override
    protected Map<String, String> getModifiedAttributeValues(Arguments arguments, Element element, String attributeName) {
        Map<String, String> attrs = new HashMap<String, String>();

        PaymentRequestDTO requestDTO = (PaymentRequestDTO) StandardExpressionProcessor.processExpression(arguments, element.getAttributeValue(attributeName));
        String url = "";
        

        if (requestDTO != null) {
            if ( element.getAttributeValue("complete_checkout") != null) {
                Boolean completeCheckout = (Boolean) StandardExpressionProcessor.processExpression(arguments,
                        element.getAttributeValue("complete_checkout"));
                element.removeAttribute("complete_checkout");
                requestDTO.completeCheckoutOnCallback(completeCheckout);
            }

            try {
                PaymentResponseDTO responseDTO = paymentGatewayHostedService.requestHostedEndpoint(requestDTO);
                url = responseDTO.getResponseMap().get(NullPaymentGatewayConstants.HOSTED_REDIRECT_URL).toString();
            } catch (PaymentException e) {
                throw new RuntimeException("Unable to Create Null Payment Gateway Hosted Link", e);
            }
        }
        attrs.put("action", url);
        return attrs;
    }

    @Override
    protected ModificationType getModificationType(Arguments arguments, Element element, String attributeName, String newAttributeName) {
        return ModificationType.SUBSTITUTION;
    }

    @Override
    protected boolean removeAttributeIfEmpty(Arguments arguments, Element element, String attributeName, String newAttributeName) {
        return true;
    }

    @Override
    protected boolean recomputeProcessorsAfterExecution(Arguments arguments, Element element, String attributeName) {
        return false;
    }

}
