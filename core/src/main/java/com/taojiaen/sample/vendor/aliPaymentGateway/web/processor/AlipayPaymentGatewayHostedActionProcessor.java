package com.taojiaen.sample.vendor.aliPaymentGateway.web.processor;

import org.springframework.stereotype.Component;

/**
 * <p>A Thymeleaf processor that will generate a Mock Hosted Link given a passed in PaymentRequestDTO.</p>
 *
 * <pre><code>
 * <form blc:null_payment_hosted_action="${paymentRequestDTO}" complete_checkout="${false}" method="POST">
 *   <input type="image" src="https://www.paypal.com/en_US/i/btn/btn_xpressCheckout.gif" align="left" style="margin-right:7px;" alt="Submit Form" />
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
@Component("blalipaymentPaymentGatewayHostedActionProcessor")
public class AlipayPaymentGatewayHostedActionProcessor {

}
