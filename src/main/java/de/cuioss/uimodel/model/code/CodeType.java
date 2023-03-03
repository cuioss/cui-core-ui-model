package de.cuioss.uimodel.model.code;

import java.io.Serializable;
import java.util.Locale;

/**
 * Abstracts any implementation of eHF Code, CodeDto, CodedValueDto, AnnotatedCode etc.
 * <p>
 * The idea is use (resolve) any of this object without care about how the magic resolving
 * is working.
 * This approach provide a lose coupling from UI to BE. Additional advantage is the simple creation
 * of mocks.
 * </p>
 * <p>
 * <em>PRECONDITION</em> : appropriate use is only possible if you have knowledge about DAO pattern
 * (data access object)
 * </p>
 * This interface doesn't make assumptions about the resolving strategies or caching feel free to
 * use the power of this.
 *
 * @author Eugen Fischer
 */
public interface CodeType extends Serializable {

    /**
     * Provide translated value by using implementation defined resolving / fall-back logic.
     *
     * @param locale {@linkplain Locale} must not be null.
     * @return resolved value if possible, {@code null} otherwise.
     */
    String getResolved(Locale locale);

    /**
     * @return unique id must be always defined
     */
    String getIdentifier();
}
