package de.cuioss.uimodel.nameprovider.testdata;

import java.util.Locale;
import java.util.Map.Entry;
import java.util.function.Function;

import de.cuioss.uimodel.nameprovider.data.EntryData;

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
