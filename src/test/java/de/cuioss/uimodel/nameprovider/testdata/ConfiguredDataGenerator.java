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

import de.cuioss.test.generator.TypedGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@SuppressWarnings({"rawtypes"})
public class ConfiguredDataGenerator implements TypedGenerator<Stream> {

    private final List<ConfiguredData> valid = Arrays.asList(
            new ConfiguredData(Locale.GERMAN.toLanguageTag(), "[de] text"),
            new ConfiguredData(Locale.GERMANY.toLanguageTag(), "[de_DE] text"),
            new ConfiguredData(Locale.ENGLISH.toLanguageTag(), "[en] text"),
            new ConfiguredData(Locale.UK.toLanguageTag(), "[en_GB] text"));

    @Override
    public Stream<ConfiguredData> next() {
        return valid.stream();
    }

    @Override
    public Class<Stream> getType() {
        return Stream.class;
    }
}
