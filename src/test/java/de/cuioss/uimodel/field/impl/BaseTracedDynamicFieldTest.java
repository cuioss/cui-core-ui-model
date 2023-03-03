package de.cuioss.uimodel.field.impl;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;

@VerifyConstructor(of = { "value", "editable" })
class BaseTracedDynamicFieldTest extends ValueObjectTest<BaseTracedDynamicField<String>> {

    // tests defined in ValueObjectTest
}
