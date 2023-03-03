package de.cuioss.uimodel.nameprovider;

import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;
import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

/**
 * Content which can be used to transfer message key and arguments.
 * Usage for {@linkplain java.text.MessageFormat}
 *
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode
@Builder
public class DisplayMessageFormat implements Serializable {

    private static final long serialVersionUID = 1169472475051755852L;

    @Getter
    private final String messageKey;

    @Getter
    @Singular
    private final List<Serializable> arguments;

    /**
     * Create DisplayMessageFormat
     *
     * @param messageKey must not be {@code null} or empty
     * @param messageArguments must not be {@code null}
     */
    public DisplayMessageFormat(final String messageKey,
            final List<Serializable> messageArguments) {
        this.messageKey = requireNotEmpty(messageKey, "messageKey");
        arguments = requireNonNull(messageArguments);
    }

    /**
     * Create DisplayMessageFormat
     *
     * @param messageKey must not be {@code null} or empty
     * @param messageArguments must not be {@code null}
     */
    public DisplayMessageFormat(final String messageKey, final Serializable... messageArguments) {
        this(messageKey, Arrays.asList(requireNonNull(messageArguments, "messageArguments")));
    }

}
