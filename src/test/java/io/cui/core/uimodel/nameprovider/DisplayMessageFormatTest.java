package io.cui.core.uimodel.nameprovider;

import io.cui.test.valueobjects.ValueObjectTest;
import io.cui.test.valueobjects.api.contracts.VerifyConstructor;
import io.cui.test.valueobjects.api.property.PropertyReflectionConfig;

@PropertyReflectionConfig(required = { "messageKey", "arguments" })
@VerifyConstructor(of = { "messageKey", "arguments" }, required = { "messageKey", "arguments" })
class DisplayMessageFormatTest extends ValueObjectTest<DisplayMessageFormat> {

}
