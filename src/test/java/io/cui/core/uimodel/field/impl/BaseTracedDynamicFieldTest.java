package io.cui.core.uimodel.field.impl;

import io.cui.test.valueobjects.ValueObjectTest;
import io.cui.test.valueobjects.api.contracts.VerifyConstructor;

@VerifyConstructor(of = { "value", "editable" })
class BaseTracedDynamicFieldTest extends ValueObjectTest<BaseTracedDynamicField<String>> {

    // tests defined in ValueObjectTest
}
