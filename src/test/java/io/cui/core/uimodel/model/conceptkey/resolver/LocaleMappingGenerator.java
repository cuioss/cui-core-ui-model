package io.cui.core.uimodel.model.conceptkey.resolver;

import static io.cui.test.generator.Generators.integers;
import static io.cui.test.generator.Generators.locales;
import static io.cui.test.generator.Generators.nonEmptyStrings;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.cui.test.generator.TypedGenerator;

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
