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

import static java.util.Objects.requireNonNull;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * DisplayMessageProvider provide {@linkplain DisplayMessageFormat} to be able
 * to transport key and arguments which can be used for
 * {@linkplain java.text.MessageFormat}
 *
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode
public class DisplayMessageProvider implements IDisplayNameProvider<DisplayMessageFormat> {

    private static final long serialVersionUID = -3453598477657055961L;

    private final DisplayMessageFormat content;

    /**
     * Create DisplayMessageProvider
     *
     * @param displayMessageFormat {@linkplain DisplayMessageFormat} must not be
     *                             {@code null}
     */
    public DisplayMessageProvider(final DisplayMessageFormat displayMessageFormat) {
        content = requireNonNull(displayMessageFormat, "displayMessageFormat");
    }

    @Override
    public DisplayMessageFormat getContent() {
        return content;
    }

    /**
     * @param bundle {@linkplain ResourceBundle} must not be {@code null}
     * @return formated text
     * @throws MissingResourceException if no object for the given key can be found
     * @throws IllegalArgumentException if bundle is null, or if the pattern is
     *                                  invalid, or if an argument in the arguments
     *                                  array is not of the type expected by the
     *                                  format element(s) that use it.
     */
    public String getMessageFormated(final ResourceBundle bundle) {

        requireNonNull(bundle, "bundle");
        return MessageFormat.format(bundle.getString(content.getMsgKey()),
                content.getArguments().toArray(new Object[content.getArguments().size()]));
    }

    /**
     * Provide creation of full initialized DisplayMessageProvider
     *
     * @author Eugen Fischer
     */
    public static class Builder {

        /**
         * @param messageKey must not be {@code null} or empty
         * @return DisplayMessageFormat builder
         */
        public de.cuioss.uimodel.nameprovider.DisplayMessageFormat.Builder messageKey(final String messageKey) {
            return new DisplayMessageFormat.Builder(messageKey);
        }
    }
}
