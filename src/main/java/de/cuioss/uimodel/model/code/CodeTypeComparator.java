package de.cuioss.uimodel.model.code;

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;

import lombok.RequiredArgsConstructor;

/**
 * Helper class comparing to {@link CodeType} regarding their label. It needs a
 * Locale, in order to call. {@link CodeType#getResolved(Locale)}. In case you
 * know if the concrete {@link CodeType} ignore the {@link Locale} attribute,
 * <code>null</code> is permitted.
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor
public class CodeTypeComparator implements Comparator<CodeType>, Serializable {

    private static final long serialVersionUID = 7747878519156301042L;

    private final Locale locale;

    @Override
    public int compare(final CodeType type1, final CodeType type2) {
        final var type1Name = nullToEmpty(type1.getResolved(locale));
        final var type2Name = nullToEmpty(type2.getResolved(locale));

        return type1Name.compareTo(type2Name);
    }

}
