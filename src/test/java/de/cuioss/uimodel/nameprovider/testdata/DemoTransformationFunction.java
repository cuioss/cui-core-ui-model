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
package de.cuioss.uimodel.nameprovider.testdata;

import de.cuioss.uimodel.nameprovider.data.EntryData;

import java.util.Locale;
import java.util.Map.Entry;
import java.util.function.Function;

/**
 * Example for data tranformation
 *
 * @author Eugen Fischer
 */
public class DemoTransformationFunction implements Function<ConfiguredData, Entry<Locale, String>> {

    @Override
    public Entry<Locale, String> apply(final ConfiguredData input) {

        return new EntryData<>(Locale.forLanguageTag(input.getLanguage()), input.getText());
    }

}
