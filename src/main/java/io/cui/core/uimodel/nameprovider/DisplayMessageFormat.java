package io.cui.core.uimodel.nameprovider;

import static io.cui.tools.string.MoreStrings.emptyToNull;
import static io.cui.tools.string.MoreStrings.requireNotEmpty;
import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.cui.tools.logging.CuiLogger;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Content which can be used to transfer message key and arguments.
 * Usage for {@linkplain java.text.MessageFormat}
 *
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode
public class DisplayMessageFormat implements Serializable {

    private static final long serialVersionUID = 1169472475051755852L;

    @Getter
    private final String msgKey;

    @Getter
    private final List<Serializable> arguments;

    /**
     * Create DisplayMessageFormat
     *
     * @param messageKey must not be {@code null} or empty
     * @param messageArguments must not be {@code null}
     */
    public DisplayMessageFormat(final String messageKey,
            final List<Serializable> messageArguments) {
        msgKey = requireNotEmpty(messageKey, "messageKey");
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

    /**
     * Provide creation of {@linkplain DisplayMessageProvider}
     *
     * @author Eugen Fischer
     */
    @NoArgsConstructor
    public static class Builder {

        private static final CuiLogger log = new CuiLogger(DisplayMessageFormat.Builder.class);

        private String tempMsgKey;

        private final List<Serializable> tempArguments = new ArrayList<>();

        /**
         * @param messageKey message bundle key must not be {@code null} or empty
         */
        public Builder(final String messageKey) {
            tempMsgKey = emptyToNull(messageKey);
        }

        /**
         * @param arguments is optional, any entry that is null will be skiped
         * @return {@linkplain Builder} in fluent api style
         */
        public Builder add(final Serializable... arguments) {
            if (null != arguments) {
                addAll(Arrays.asList(arguments));
            }
            return this;
        }

        /**
         * @param msgKey must not be null nor empty
         * @return {@linkplain Builder} in fluent api style
         */
        public Builder msgKey(final String msgKey) {
            tempMsgKey = emptyToNull(msgKey);
            return this;
        }

        /**
         * @param arguments is optional, any entry that is null will be skiped
         * @return {@linkplain Builder} in fluent api style
         */
        public Builder addAll(final List<Serializable> arguments) {
            if (null != arguments) {
                for (final Serializable entry : arguments) {
                    if (null != entry) {
                        tempArguments.add(entry);
                    }
                }
            }
            return this;
        }

        /**
         * @return full filled {@linkplain DisplayMessageProvider}
         */
        public DisplayMessageProvider build() {
            requireNonNull(tempMsgKey);
            if (tempArguments.isEmpty()) {
                log.warn("{}\n{}",
                        "Are you really sure there are no message format arguments needed?",
                        "May it's better idea to use LabeledKey instead?");
            }
            return new DisplayMessageProvider(
                    new DisplayMessageFormat(tempMsgKey, tempArguments));
        }
    }

    /**
     * @return {@linkplain Builder} in fluent api style
     */
    public static final Builder builder() {
        return new Builder();
    }
}
