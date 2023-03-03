package de.cuioss.uimodel.field.impl;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.generator.PropertyGenerator;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;
import de.cuioss.uimodel.field.DynamicField;

@PropertyGenerator(BaseDynamicFieldGenerator.class)
@PropertyConfig(name = "delegate", propertyClass = DynamicField.class)
@VerifyConstructor(of = { "delegate", "identifier", "labelKey", "advisoryKey" },
        writeOnly = "delegate", required = { "delegate", "identifier" })
class BaseLabeledDynamicFieldTest
        extends ValueObjectTest<BaseLabeledDynamicField<String>> {

}
