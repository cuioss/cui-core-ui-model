package io.cui.core.uimodel.nameprovider;

import io.cui.test.valueobjects.ValueObjectTest;
import io.cui.test.valueobjects.api.contracts.VerifyConstructor;
import io.cui.test.valueobjects.api.property.PropertyReflectionConfig;

@PropertyReflectionConfig(required = "msgKey")
// FIXME efischer: The structure seems to be problematic
// @PropertyBuilderConfig(name = "arguments", builderMethodName = "addAll")
// @VerifyBuilder(of = { "msgKey", "arguments" })
@VerifyConstructor(of = { "msgKey", "arguments" }, required = { "msgKey", "arguments" })
class DisplayMessageFormatTest extends ValueObjectTest<DisplayMessageFormat> {

}
