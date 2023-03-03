package de.cuioss.uimodel.field.impl;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.uimodel.field.DynamicField;
import de.cuioss.uimodel.field.DynamicFieldType;

@SuppressWarnings({ "rawtypes", "javadoc" })
public class BaseDynamicFieldGenerator implements TypedGenerator<DynamicField> {

    private final TypedGenerator<String> generator = Generators.letterStrings();

    @Override
    public DynamicField next() {
        return new BaseLabeledDynamicField<>(
                DynamicFieldType.STRING.createDynamicField(generator.next(), true),
                generator.next(), generator.next(), generator.next());
    }

    @Override
    public Class<DynamicField> getType() {
        return DynamicField.class;
    }

}
