package de.cuioss.uimodel.nameprovider;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;

@VerifyConstructor(of = "content", required = "content")
class DisplayNameTest extends ValueObjectTest<DisplayName> {

}
