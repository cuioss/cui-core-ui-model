package de.cuioss.uimodel.model.conceptkey.impl;

import java.util.HashMap;
import java.util.Map;

import de.cuioss.test.generator.TypedGenerator;

@SuppressWarnings({ "rawtypes", "javadoc" })
public class AugmentationMapGenerator implements TypedGenerator<Map> {

    public static final String KEY1 = "key1";

    public static final String KEY2 = "key2";

    public static final String INVALID_KEY = "invalidkey";

    public static final String VALUE1 = Integer.toString(1);

    public static final String VALUE2 = "value2";

    @Override
    public Map<String, String> next() {
        final Map<String, String> augmentationMap = new HashMap<>();
        augmentationMap.put(KEY1, VALUE1);
        augmentationMap.put(KEY2, VALUE2);
        return augmentationMap;
    }

    @Override
    public Class<Map> getType() {
        return Map.class;
    }

}
