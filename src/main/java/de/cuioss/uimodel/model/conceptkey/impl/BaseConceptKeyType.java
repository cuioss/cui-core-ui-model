package de.cuioss.uimodel.model.conceptkey.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import de.cuioss.uimodel.model.conceptkey.ConceptCategory;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Base abstract implementation of {@link ConceptKeyType}, implementing aliases, augmentationMap and
 * category handling.
 *
 * @author Matthias Walliczek
 */
@EqualsAndHashCode(of = { "category" })
@ToString
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseConceptKeyType implements ConceptKeyType {

    private static final long serialVersionUID = 3314726756126201321L;

    @Getter
    private final Set<String> aliases;

    private final Map<String, String> augmentationMap;

    @Getter
    private final ConceptCategory category;

    protected BaseConceptKeyType(final ConceptCategory category) {
        this(new TreeSet<>(), new HashMap<>(), category);
    }

    @Override
    public String get(final String key, final String defaultValue) {
        if (!this.augmentationMap.containsKey(key)) {
            return defaultValue;
        }
        return this.augmentationMap.get(key);
    }

    @Override
    public String get(final String key) {
        return get(key, null);
    }

    protected void set(final String key, final String value) {
        this.augmentationMap.put(key, value);
    }

    @Override
    public boolean containsKey(final String key) {
        return this.augmentationMap.containsKey(key);
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return this.augmentationMap.entrySet();
    }

}
