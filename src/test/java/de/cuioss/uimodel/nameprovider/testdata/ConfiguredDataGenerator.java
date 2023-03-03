package de.cuioss.uimodel.nameprovider.testdata;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import de.cuioss.test.generator.TypedGenerator;

@SuppressWarnings({ "javadoc", "rawtypes" })
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
