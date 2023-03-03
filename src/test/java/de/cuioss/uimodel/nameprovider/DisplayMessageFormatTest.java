package de.cuioss.uimodel.nameprovider;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@PropertyReflectionConfig(required = { "messageKey", "arguments" })
@VerifyConstructor(of = { "messageKey", "arguments" }, required = { "messageKey", "arguments" })
class DisplayMessageFormatTest extends ValueObjectTest<DisplayMessageFormat> {

}
