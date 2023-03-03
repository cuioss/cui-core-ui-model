package de.cuioss.uimodel.model.conceptkey.impl;

import static de.cuioss.tools.collect.CollectionLiterals.immutableMap;
import static de.cuioss.tools.collect.CollectionLiterals.immutableSet;
import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import de.cuioss.uimodel.model.code.CodeType;
import de.cuioss.uimodel.model.conceptkey.ConceptCategory;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * <p>
 * Default implementation of {@link ConceptKeyType}. The actual labelResolving will be done by a
 * given {@link I18nDisplayNameProvider}, see
 * {@link ConceptKeyTypeImplBuilder#labelResolver(I18nDisplayNameProvider)}
 * </p>
 * <h3>Ordering</h3>
 * <ul>
 * <li>If {@link ConceptCategory} are not equal they will be compared</li>
 * <li>If {@link ConceptCategory} are equal the {@link #getIdentifier()} will be compared</li>
 * <li>The result of {@link I18nDisplayNameProvider} will not be regarded here, because it is
 * {@link Locale}
 * specific</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode(of = { "identifier" }, callSuper = true)
@ToString(callSuper = true)
public class ConceptKeyTypeImpl extends BaseConceptKeyType {

    private static final long serialVersionUID = 5225412069791405625L;

    @Getter
    private final String identifier;

    @Getter
    private final I18nDisplayNameProvider labelResolver;

    @Override
    public String getResolved(final Locale locale) {
        return labelResolver.lookupTextWithFallbackFirstFittingLanguageOnly(locale);
    }

    ConceptKeyTypeImpl(final String tempIdentifier, final ConceptCategory tempCategory,
            final Map<String, String> tempAugmentationMap, final I18nDisplayNameProvider tempLabelResolver,
            final Set<String> tempAliases) {
        super(tempAliases, tempAugmentationMap, tempCategory);
        identifier = tempIdentifier;
        labelResolver = tempLabelResolver;
    }

    /**
     * @author Oliver Wolff
     */
    public static class ConceptKeyTypeImplBuilder {

        private ConceptCategory tempCategory;

        private String tempIdentifier;

        private Map<String, String> tempAugmentationMap;

        private I18nDisplayNameProvider tempLabelResolver;

        private Set<String> tempAliases;

        ConceptKeyTypeImplBuilder() {
            tempAugmentationMap = new HashMap<>();
            tempAliases = new TreeSet<>();
        }

        /**
         * @param identifier the String identifier for finding a code category. see
         *            {@link CodeType#getIdentifier()}
         * @return the {@link ConceptKeyTypeImplBuilder}
         */
        public ConceptKeyTypeImplBuilder identifier(final String identifier) {
            tempIdentifier = emptyToNull(identifier);
            return this;
        }

        /**
         * @param category identifying the category this {@link ConceptKeyType} belongs to.
         * @return the {@link ConceptKeyTypeImplBuilder}
         */
        public ConceptKeyTypeImplBuilder category(final ConceptCategory category) {
            tempCategory = category;
            return this;
        }

        /**
         * @param labelResolver the {@link I18nDisplayNameProvider} to be set..
         * @return the {@link ConceptKeyTypeImplBuilder}
         */
        public ConceptKeyTypeImplBuilder labelResolver(final I18nDisplayNameProvider labelResolver) {
            tempLabelResolver = labelResolver;
            return this;
        }

        /**
         * @param augmentationMap for the augmentationMap must not be null.
         * @return the {@link ConceptKeyTypeImplBuilder}
         */
        public ConceptKeyTypeImplBuilder augmentation(
                final Map<String, String> augmentationMap) {
            tempAugmentationMap.putAll(augmentationMap);
            return this;
        }

        /**
         * @param key for the augmentationMap must not be null.
         * @param value for the augmentationMap must not be null.
         * @return the {@link ConceptKeyTypeImplBuilder}
         */
        public ConceptKeyTypeImplBuilder augmentation(final String key, final String value) {
            tempAugmentationMap.put(key, value);
            return this;
        }

        /**
         * @param alias for the aliasSet, must not be null.
         * @return the {@link ConceptKeyTypeImplBuilder}
         */
        public ConceptKeyTypeImplBuilder alias(final String alias) {
            tempAliases.add(alias);
            return this;
        }

        /**
         * @param aliases for the augmentationMap must not be null.
         * @return the {@link ConceptKeyTypeImplBuilder}
         */
        public ConceptKeyTypeImplBuilder alias(final Set<String> aliases) {
            tempAliases.addAll(aliases);
            return this;
        }

        /**
         * @return the build {@link ConceptKeyTypeImpl}
         */
        public ConceptKeyTypeImpl build() {
            requireNonNull(tempIdentifier, "identifier");
            requireNonNull(tempLabelResolver, "labelResolver");
            tempAugmentationMap = immutableMap(tempAugmentationMap);
            tempAliases = immutableSet(tempAliases);
            return new ConceptKeyTypeImpl(tempIdentifier, tempCategory,
                    tempAugmentationMap,
                    tempLabelResolver, tempAliases);
        }
    }

    /**
     * @return the {@link ConceptKeyTypeImplBuilder}
     */
    public static ConceptKeyTypeImplBuilder builder() {
        return new ConceptKeyTypeImplBuilder();
    }

    @Override
    public int compareTo(final ConceptKeyType other) {
        if (null != getCategory() && null != other.getCategory() && !getCategory().equals(other.getCategory())) {
            return getCategory().getName().compareTo(other.getCategory().getName());
        }
        return getIdentifier().compareTo(other.getIdentifier());
    }
}
