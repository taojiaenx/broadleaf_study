package com.taojiaen.product.processor;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.web.BroadleafRequestContext;
import org.broadleafcommerce.core.web.processor.PriceTextDisplayProcessor;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;

public class ExtPriceTextProcessor extends PriceTextDisplayProcessor{
	public ExtPriceTextProcessor() {
		super();
	}
	@Override
    protected String getText(Arguments arguments, Element element, String attributeName) {
        
        Money price = null;

        Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
                .parseExpression(arguments.getConfiguration(), arguments, element.getAttributeValue(attributeName));
        Object result = expression.execute(arguments.getConfiguration(), arguments);
        if (result instanceof Money) {
            price = (Money) result;
        } else if (result instanceof Number) {
            price = new Money(((Number)result).doubleValue());
        }


        if (price == null) {
            return "Not Available";
        }

        BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
        return price.getAmount().toString() + " 元";
    }
}
