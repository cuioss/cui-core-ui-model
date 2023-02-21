package io.cui.core.uimodel.model.code;

import static java.util.Objects.requireNonNull;

import java.util.Locale;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Simple implementation of {@link CodeType} that ignores the {@link Locale}
 * attribute on {@link #getResolved(Locale)} and always returns a previously
 * configured string.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class CodeTypeImpl implements CodeType {

    private static final long serialVersionUID = 6827791409255699288L;

    private final String resolved;

    @Getter
    private final String identifier;

    /**
     * @param resolved must not be null
     * @param identifier must not be null
     */
    public CodeTypeImpl(final String resolved, final String identifier) {
        super();
        this.resolved = requireNonNull(resolved, "resolved must not be null");
        this.identifier = requireNonNull(identifier, "identifier must not be null");
    }

    /**
     * @param resolved
     *            to be used as resolved and identifier.
     */
    public CodeTypeImpl(final String resolved) {
        this(resolved, resolved);
    }

    @Override
    public String getResolved(final Locale locale) {
        return this.resolved;
    }

}
