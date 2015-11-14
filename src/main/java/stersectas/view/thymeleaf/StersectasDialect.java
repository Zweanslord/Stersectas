package stersectas.view.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import lombok.val;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

/**
 * A thymeleaf dialect with all the custom extensions for this application.
 *
 * @see http://www.thymeleaf.org/doc/tutorials/2.1/extendingthymeleaf.html
 */
public class StersectasDialect extends AbstractDialect {

	private static final String DIALECT_PREFIX = "ster";

	public StersectasDialect() {
		super();
	}

	@Override
	public String getPrefix() {
		return DIALECT_PREFIX;
	}

	@Override
	public Set<IProcessor> getProcessors() {
		final val processors = new HashSet<IProcessor>();
		processors.add(new HtmlEscapedWithLineSeparatorsProcessor());
		return processors;
	}

}