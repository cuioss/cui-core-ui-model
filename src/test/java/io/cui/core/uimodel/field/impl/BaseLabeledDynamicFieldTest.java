package io.cui.core.uimodel.field.impl;

import io.cui.core.uimodel.field.DynamicField;
import io.cui.test.valueobjects.ValueObjectTest;
import io.cui.test.valueobjects.api.contracts.VerifyConstructor;
import io.cui.test.valueobjects.api.generator.PropertyGenerator;
import io.cui.test.valueobjects.api.property.PropertyConfig;

@PropertyGenerator(BaseDynamicFieldGenerator.class)
@PropertyConfig(name = "delegate", propertyClass = DynamicField.class)
@VerifyConstructor(of = { "delegate", "identifier", "labelKey", "advisoryKey" },
        writeOnly = "delegate", required = { "delegate", "identifier" })
class BaseLabeledDynamicFieldTest
        extends ValueObjectTest<BaseLabeledDynamicField<String>> {

}
