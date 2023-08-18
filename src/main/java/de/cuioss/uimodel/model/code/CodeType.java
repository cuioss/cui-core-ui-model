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
package de.cuioss.uimodel.model.code;

import java.io.Serializable;
import java.util.Locale;

/**
 * Abstracts any implementation of Code, CodeDto, CodedValueDto, AnnotatedCode
 * etc.
 * <p>
 * The idea is use (resolve) any of this object without care about how the magic
 * resolving is working. This approach provide a lose coupling from UI to BE.
 * Additional advantage is the simple creation of mocks.
 * </p>
 * <p>
 * <em>PRECONDITION</em> : appropriate use is only possible if you have
 * knowledge about DAO pattern (data access object)
 * </p>
 * This interface doesn't make assumptions about the resolving strategies or
 * caching feel free to use the power of this.
 *
 * @author Eugen Fischer
 */
public interface CodeType extends Serializable {

    /**
     * Provide translated value by using implementation defined resolving /
     * fall-back logic.
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
