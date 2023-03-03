package de.cuioss.uimodel.nameprovider.data;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;

@VerifyConstructor(of = "key", required = "key")
@VerifyConstructor(of = { "key", "value" }, required = "key")
class EntryDataTest extends ValueObjectTest<EntryData<?, ?>> {

}
