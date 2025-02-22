/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.uimodel.nameprovider;

import de.cuioss.tools.logging.CuiLogger;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;
import static java.util.Objects.requireNonNull;

/**
 * Content which can be used to transfer message key and arguments. Usage for
 * {@linkplain java.text.MessageFormat}
 *
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode
public class DisplayMessageFormat implements Serializable {

    @Serial
    private static final long serialVersionUID = 1169472475051755852L;

    @Getter
    private final String msgKey;

    @Getter
    private final List<Serializable> arguments;

    /**
     * Create DisplayMessageFormat
     *
     * @param messageKey       must not be {@code null} or empty
     * @param messageArguments must not be {@code null}
     */
    public DisplayMessageFormat(final String messageKey, final List<Serializable> messageArguments) {
        msgKey = requireNotEmpty(messageKey, "messageKey");
        arguments = requireNonNull(messageArguments);
    }

    /**
     * Create DisplayMessageFormat
     *
     * @param messageKey       must not be {@code null} or empty
     * @param messageArguments may be null or empty
     */
    public DisplayMessageFormat(final String messageKey, final Serializable... messageArguments) {
        this(messageKey, immutableList(messageArguments));
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
                log.warn("{}\n{}", "Are you really sure there are no message format arguments needed?",
                        "May it's better idea to use LabeledKey instead?");
            }
            return new DisplayMessageProvider(new DisplayMessageFormat(tempMsgKey, tempArguments));
        }
    }

    /**
     * @return {@linkplain Builder} in fluent api style
     */
    public static Builder builder() {
        return new Builder();
    }
}
