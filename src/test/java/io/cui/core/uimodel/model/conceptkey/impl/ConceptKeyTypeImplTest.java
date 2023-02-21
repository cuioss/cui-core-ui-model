package io.cui.core.uimodel.model.conceptkey.impl;

import static io.cui.core.uimodel.model.conceptkey.impl.AugmentationMapGenerator.INVALID_KEY;
import static io.cui.core.uimodel.model.conceptkey.impl.AugmentationMapGenerator.KEY1;
import static io.cui.core.uimodel.model.conceptkey.impl.AugmentationMapGenerator.KEY2;
import static io.cui.core.uimodel.model.conceptkey.impl.AugmentationMapGenerator.VALUE1;
import static io.cui.core.uimodel.model.conceptkey.impl.AugmentationMapGenerator.VALUE2;
import static io.cui.test.valueobjects.property.util.PropertyAccessStrategy.BUILDER_COLLECTION_AND_SINGLE_ELEMENT;
import static io.cui.tools.collect.CollectionLiterals.mutableSet;
import static io.cui.tools.property.PropertyReadWrite.WRITE_ONLY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import io.cui.core.uimodel.model.conceptkey.ConceptCategory;
import io.cui.core.uimodel.model.conceptkey.impl.ConceptCategoryGenerator.TestCodeCategory;
import io.cui.core.uimodel.nameprovider.I18nDisplayNameProvider;
import io.cui.test.generator.Generators;
import io.cui.test.generator.TypedGenerator;
import io.cui.test.valueobjects.ValueObjectTest;
import io.cui.test.valueobjects.api.contracts.VerifyBuilder;
import io.cui.test.valueobjects.api.object.ObjectTestConfig;
import io.cui.test.valueobjects.api.property.PropertyBuilderConfig;
import io.cui.test.valueobjects.api.property.PropertyConfig;
import io.cui.test.valueobjects.api.property.PropertyReflectionConfig;
import io.cui.test.valueobjects.property.util.CollectionType;

@PropertyConfig(name = "identifier", propertyClass = String.class)
@PropertyConfig(name = "category", propertyClass = ConceptCategory.class,
        generator = ConceptCategoryGenerator.class)
@PropertyConfig(name = "labelResolver", propertyClass = I18nDisplayNameProvider.class)
@PropertyConfig(name = "augmentation",
        propertyClass = Map.class,
        generator = AugmentationMapGenerator.class,
        propertyReadWrite = WRITE_ONLY)
@PropertyConfig(name = "aliases", propertyClass = String.class, collectionType = CollectionType.SET)
@PropertyBuilderConfig(name = "aliases",
        builderMethodName = "alias",
        builderSingleAddMethodName = "alias",
        propertyAccessStrategy = BUILDER_COLLECTION_AND_SINGLE_ELEMENT)
@VerifyBuilder(of = { "identifier", "category", "labelResolver", "augmentation", "aliases" },
        required = { "identifier", "labelResolver" })
@ObjectTestConfig(equalsAndHashCodeBasicOnly = true)
@PropertyReflectionConfig(skip = true)
class ConceptKeyTypeImplTest extends ValueObjectTest<ConceptKeyTypeImpl> {

    private static final String RESOLVED_VALUE = "testValue";

    private static final String IDENTIFIER = "stringIdentifier";

    private static final I18nDisplayNameProvider RESOLVER =
        new I18nDisplayNameProvider(RESOLVED_VALUE);

    private static final I18nDisplayNameProvider RESOLVER2 =
        new I18nDisplayNameProvider(VALUE2);

    private final TypedGenerator<ConceptCategory> categories = new ConceptCategoryGenerator();
    private final AugmentationMapGenerator mapGenerator = new AugmentationMapGenerator();

    @Test
    void shouldBuildWithCorrectParameter() {
        final var category = categories.next();
        final var type = ConceptKeyTypeImpl.builder().category(category)
                .identifier(IDENTIFIER).labelResolver(RESOLVER).build();
        assertEquals(RESOLVED_VALUE, type.getResolved(Generators.locales().next()));
        assertEquals(category, type.getCategory());
        assertEquals(IDENTIFIER, type.getIdentifier());
    }

    @Test
    void shouldHandleEmptyMap() {
        final var type =
            ConceptKeyTypeImpl.builder().category(categories.next())
                    .identifier(IDENTIFIER).labelResolver(RESOLVER).build();

        assertFalse(type.containsKey(KEY1));
        assertNull(type.get(KEY1, null));
        assertNull(type.get(KEY1));
        // Now with defaulting
        assertEquals(VALUE2, type.get(KEY1, VALUE2));

        assertTrue(type.entrySet().isEmpty());
    }

    @Test
    void shouldHandleAugmentationMap() {
        final var type =
            ConceptKeyTypeImpl.builder().category(categories.next())
                    .augmentation(mapGenerator.next()).identifier(IDENTIFIER)
                    .labelResolver(RESOLVER)
                    .build();

        for (final Entry<String, String> entry : mapGenerator.next().entrySet()) {
            assertTrue(type.containsKey(entry.getKey()));
            assertEquals(entry.getValue(), type.get(entry.getKey(), null));
        }

        assertEquals(mapGenerator.next().entrySet(), type.entrySet());
        // Now with defaulting
        assertEquals(VALUE2, type.get(INVALID_KEY, VALUE2));
    }

    @Test
    void shouldHandleAugmentationEntries() {
        final var type =
            ConceptKeyTypeImpl.builder().category(categories.next())
                    .augmentation(KEY1, VALUE1).augmentation(KEY2, VALUE2).identifier(IDENTIFIER)
                    .labelResolver(RESOLVER).build();

        for (final Entry<String, String> entry : mapGenerator.next().entrySet()) {
            assertTrue(type.containsKey(entry.getKey()));
            assertEquals(entry.getValue(), type.get(entry.getKey(), null));
        }

        assertEquals(mapGenerator.next().entrySet(), type.entrySet());
        // Now with defaulting
        assertEquals(VALUE2, type.get(INVALID_KEY, VALUE2));
    }

    @Test
    void shouldHandleAliases() {
        var type =
            ConceptKeyTypeImpl.builder().category(categories.next()).identifier(IDENTIFIER)
                    .labelResolver(RESOLVER).build();
        assertTrue(type.getAliases().isEmpty());

        type =
            ConceptKeyTypeImpl.builder().category(categories.next()).identifier(IDENTIFIER)
                    .alias(KEY1)
                    .labelResolver(RESOLVER).build();
        assertFalse(type.getAliases().isEmpty());
        assertEquals(KEY1, type.getAliases().iterator().next());

        type =
            ConceptKeyTypeImpl.builder().category(categories.next()).identifier(IDENTIFIER)
                    .alias(mutableSet(KEY1, KEY2))
                    .labelResolver(RESOLVER).build();
        assertFalse(type.getAliases().isEmpty());
        assertEquals(2, type.getAliases().size());
    }

    @Test
    void shouldCompareCorrectly() {
        final var category = categories.next();

        // Same Object
        var type1 = ConceptKeyTypeImpl.builder().category(category)
                .identifier(IDENTIFIER).labelResolver(RESOLVER).augmentation(Collections.emptyMap())
                .build();
        var type2 = ConceptKeyTypeImpl.builder().category(category)
                .identifier(IDENTIFIER).labelResolver(RESOLVER2).build();
        assertEquals(0, type1.compareTo(type2));

        // Different identifier
        type2 = ConceptKeyTypeImpl.builder().category(category)
                .identifier(IDENTIFIER + IDENTIFIER).labelResolver(RESOLVER)
                .augmentation(Collections.emptyMap()).build();
        assertTrue(type1.compareTo(type2) < 0);
        assertNotEquals(type1, type2);

        // Different category
        type1 = ConceptKeyTypeImpl.builder().category(TestCodeCategory.CAT1)
                .identifier(IDENTIFIER).labelResolver(RESOLVER).build();
        type2 = ConceptKeyTypeImpl.builder().category(TestCodeCategory.CAT2).identifier(IDENTIFIER)
                .labelResolver(RESOLVER).build();
        assertTrue(type1.compareTo(type2) < 0);
        assertNotEquals(type1, type2);
    }

    @Test
    void shouldNotFailOnMissingCategory() {
        assertDoesNotThrow(() -> ConceptKeyTypeImpl.builder().identifier(IDENTIFIER).labelResolver(RESOLVER).build());
    }

    @Test
    void shouldFailOnMissingResolver() {
        var builder = ConceptKeyTypeImpl.builder().category(categories.next()).identifier(IDENTIFIER);
        assertThrows(NullPointerException.class,
                () -> builder.build());
    }

    @Test
    void shouldFailOnMissingIdentifier() {
        var builder = ConceptKeyTypeImpl.builder().category(categories.next()).labelResolver(RESOLVER);
        assertThrows(NullPointerException.class,
                () -> builder.build());
    }

    @Test
    void shouldFailOnEmptyIdentifier() {
        var builder = ConceptKeyTypeImpl.builder().identifier("").category(categories.next())
                .labelResolver(RESOLVER);
        assertThrows(NullPointerException.class,
                () -> builder.build());
    }
}
