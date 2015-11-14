package stersectas.view.thymeleaf;

import lombok.val;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractUnescapedTextChildModifierAttrProcessor;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.unbescape.html.HtmlEscape;

/**
 * Enables the use of lstext="${aVariable}" where aVariable will be html escaped, but still shows new lines.
 * These new lines would typically come from a variable originating from a textarea input.
 *
 * Solution adapted from: http://stackoverflow.com/questions/30394419/thymeleaf-spring-how-to-keep-line-break
 */
public class HtmlEscapedWithLineSeparatorsProcessor extends AbstractUnescapedTextChildModifierAttrProcessor {

	// only executes this processor for the attribute 'lstext'
	private static final String ATTRIBUTE_NAME = "lstext";

	private static final String SYSTEM_LINE_SEPERATOR = System.getProperty("line.separator");
	private static final String HTML_LINE_SEPERATOR = "<br />";

	public HtmlEscapedWithLineSeparatorsProcessor() {
		super(ATTRIBUTE_NAME);
	}

	@Override
	protected String getText(final Arguments arguments, final Element element, final String attributeName) {
		final val attributeValue = element.getAttributeValue(attributeName);
		final val configuration = arguments.getConfiguration();
		final val expressionParser = StandardExpressions.getExpressionParser(configuration);
		final val expression = expressionParser.parseExpression(configuration, arguments, attributeValue);
		final val value = (String) expression.execute(configuration, arguments);

		return HtmlEscape.escapeHtml4Xml(value).replace(SYSTEM_LINE_SEPERATOR, HTML_LINE_SEPERATOR);
	}

	@Override
	public int getPrecedence() {
		// A value of 10000 is higher than any attribute in the SpringStandard dialect. So this attribute will execute
		// after all other attributes from that dialect, if in the same tag.
		return 11400;
	}

}