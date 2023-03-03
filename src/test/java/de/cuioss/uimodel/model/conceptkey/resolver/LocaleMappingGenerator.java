package de.cuioss.uimodel.model.conceptkey.resolver;

import static de.cuioss.test.generator.Generators.integers;
import static de.cuioss.test.generator.Generators.locales;
import static de.cuioss.test.generator.Generators.nonEmptyStrings;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.cuioss.test.generator.TypedGenerator;

@SuppressWarnings({ "javadoc", "rawtypes" })
public class LocaleMappingGenerator implements TypedGenerator<Map> {

    private final TypedGenerator<String> labels = nonEmptyStrings();

    private final TypedGenerator<Integer> counter = integers(0, 12);

    @Override
    public Map<Locale, String> next() {
        final Map<Locale, String> map = new HashMap<>();
        final int size = counter.next();
        for (var i = 0; i < size; i++) {
            map.put(locales().next(), labels.next());
        }
        return map;
    }

    @Override
    public Class<Map> getType() {
        return Map.class;
    }

}
