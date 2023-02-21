package io.cui.core.uimodel.field.impl;

import io.cui.core.uimodel.field.DynamicField;
import io.cui.core.uimodel.field.DynamicFieldType;
import io.cui.test.generator.Generators;
import io.cui.test.generator.TypedGenerator;

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
